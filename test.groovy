pipeline {
    agent none

    stages {
        stage('Test Kubernetes Pod') {
            agent {
                kubernetes {
                    yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: busybox
    image: busybox
    command:
    - sleep
    args:
    - 1000
                    """
                }
            }
            steps {
                container('busybox') {
                    sh '''
                    echo "Hello from BusyBox container"
                    ls -l /
                    '''
                }
            }
        }
    }
}

----
def label = "kaniko-${UUID.randomUUID().toString()}"

podTemplate(name: 'kaniko', label: label, yaml: """
apiVersion: v1
kind: Pod
metadata:
  name: kaniko
spec:
  containers:
  - name: kaniko
    image: poswark/executor-debug:1.0.0
    imagePullPolicy: IfNotPresent
    imagePullSecrets:
    - name: regcred
    command:
    - /busybox/cat
    tty: true

""") {
  node(label) {
    def IMAGE_PUSH_DESTINATION = "poswark/kaniko-demo:1.0.9"

    stage('Clone Repository') {
      //checkout scm
    }

    stage('Build with Kaniko') {
      container(name: 'kaniko', shell: '/busybox/sh') {
        sh '''
        /kaniko/executor \
          --context=git://github.com/Poswark/kaniko-demo.git#refs/heads/trunk \
          --dockerfile=Dockerfile.node \
          --destination=poswark/kaniko-demo:1.0.9 \
          --verbosity info \
          --kaniko-dir /tmp \
          --log-format json  \
          --insecure \
          --skip-tls-verify \
          --cache=false \
          --force
        '''
      }
    }
  }
}
