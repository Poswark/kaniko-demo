pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials('docker-registry-password')
        COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
        BUILD_DATE = sh(script: 'date -u +"%Y-%m-%dT%H:%M:%SZ"', returnStdout: true).trim()
    }
    stages {
        stage('Build and Push with Kaniko') {
            steps {
                script {
                    sh '''
                    docker run --rm \
                        -v `pwd`:/workspace  \
                        -e COMMIT_HASH=${COMMIT_HASH} \
                        -e BUILD_DATE=${BUILD_DATE} \
                        -e DOCKER_PASSWORD=${DOCKER_PASSWORD} \
                        poswark/executor-debug:1.0.0 \
                        --context "/workspace" \
                        --dockerfile "/workspace/Dockerfile" \
                        --destination poswark/kaniko-demo:1.0.2 \
                        --verbosity info --kaniko-dir /tmp \
                        --log-format json --label key=value
                    '''
                }
            }
        }
    }
}