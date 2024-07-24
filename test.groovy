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
                                  --context=git://github.com/Poswark/kaniko-demo.git#refs/heads/trunk \
                                  --tar-path image.tar --no-push
                                '''
                            }
    
                        }
                    }
                }
            }
        }
    }
}
