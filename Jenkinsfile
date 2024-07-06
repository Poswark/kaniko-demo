pipeline {
    agent any
    environment {
        COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
        BUILD_DATE = sh(script: 'date -u +"%Y-%m-%dT%H:%M:%SZ"', returnStdout: true).trim()
        IMAGE_NAME = "poswark/kaniko-demo"
        IMAGE_TAG = "1.0.4"
    }
    parameters {
        choice(name: 'NODE_VERSION', choices: ['14', '22.4.0', '21.4.0'], description: 'Node.js version to use')
    }
    stages {
        stage('Build and Push with Kaniko') {
            steps {
                script {
                    sh """
                    docker run --rm \
                        -v `pwd`:/workspace  \
                        poswark/executor-debug:1.0.0 \
                        --context "/workspace" \
                        --dockerfile "/workspace/Dockerfile" \
                        --destination ${IMAGE_NAME}:${IMAGE_TAG} \
                        --verbosity info \
                        --kaniko-dir /tmp \
                        --log-format json --label key=value \
                        --build-arg NODE_VERSION=${params.NODE_VERSION} \
                        --build-arg COMMIT_HASH=${env.COMMIT_HASH} \
                        --build-arg BUILD_DATE=${env.BUILD_DATE} \
                        
                    """
                }
            }
        }
    }
    post {
        success {
            echo 'Build and push completed successfully.'
        }
        failure {
            echo 'Build or push failed.'
        }
        always {
            echo 'This will always run, regardless of the build result.'
        }
    }
}