pipeline {
    agent any
    environment {
        COMMIT = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
        BUILD_DATE = sh(script: 'date -u +"%Y-%m-%dT%H:%M:%SZ"', returnStdout: true).trim()
        BRANCH_NAME = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
        IMAGE_NAME = "poswark/kaniko-demo"
        IMAGE_TAG = "1.0.5"
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
                        --log-format json --label commit=${COMMIT} \
                        --build-arg NODE_VERSION=${params.NODE_VERSION} \
                        --build-arg BUILD_DATE=${env.BUILD_DATE} \
                        --build-arg BRANCH_NAME=${env.BRANCH_NAME} \
                        
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