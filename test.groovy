pipeline {
    agent none

    stages {
        stage('Build and Push to Registry') {
            agent {
                kubernetes {
                    yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:latest
    command:
    - sleep
    args:
    - infinity
                    """
                }
            }
            steps {
                container('kaniko') {
                    sh '''
                    # Clonar el repositorio
                    git clone https://github.com/Poswark/kaniko-demo.git /workspace
                    cd /workspace

                    # Ejecutar Kaniko
                    /kaniko/executor \
                      --context "/workspace" \
                      --dockerfile "/workspace/Dockerfile" \
                      --destination hello:0.0.1 \
                      --verbosity info \
                      --kaniko-dir /tmp \
                      --log-format json \
                      --tar-path image.tar --no-push
                    '''
                }
            }
        }
    }
}
