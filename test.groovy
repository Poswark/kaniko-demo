pipeline {
    agent { label 'kaniko' }

    stages {
        stage('Build and Push to Registry') {
            steps {
                script {
                    podTemplate(yaml: kaniko()) {
                        node(POD_LABEL) {
                            container('kaniko') {
                                sh '''
                                executor \
                                  --destination=docker.io/somerepo/kaniko-test:$(date -u +%Y-%m-%dT%H%M%S) \
                                  --context=git://somegitrepo.com/example/sample-go-http-app.git#refs/heads/master
                                '''
                            }
                        }
                    }
                }
            }
        }
    }
}
