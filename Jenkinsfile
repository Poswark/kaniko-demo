pipeline {
    agent any
    environment {
        COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
        BUILD_DATE = sh(script: 'date -u +"%Y-%m-%dT%H:%M:%SZ"', returnStdout: true).trim()
        IMAGE_NAME = "poswark/kaniko-demo"
        IMAGE_TAG = "1.0.2"
    }
    parameters {
        choice(name: 'NODE_VERSION', choices: ['14', '16', '22.4.0'], description: 'Node.js version to use')
        string(name: 'DOCKERFILE_PATH', defaultValue: '/workspace/Dockerfile', description: 'Path to Dockerfile')
        string(name: 'BUILD_CONTEXT', defaultValue: '/workspace', description: 'Build context')
        string(name: 'KANIKO_IMAGE', defaultValue: 'poswark/executor-debug:1.0.0', description: 'Kaniko executor image')
    }
    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    // Any preparation steps can go here
                    echo "Using Node.js version: ${params.NODE_VERSION}"
                }
            }
        }
        stage('Build and Push with Kaniko') {
            steps {
                script {
                    sh '''
                    docker run --rm \
                        -v `pwd`:/workspace  \
                        -e COMMIT_HASH=${COMMIT_HASH} \
                        -e BUILD_DATE=${BUILD_DATE} \
                        -e DOCKER_PASSWORD=${DOCKER_PASSWORD} \
                        -e NODE_VERSION=${params.NODE_VERSION} \
                        ${params.KANIKO_IMAGE} \
                        --context "${params.BUILD_CONTEXT}" \
                        --dockerfile "${params.DOCKERFILE_PATH}" \
                        --destination ${IMAGE_NAME}:${IMAGE_TAG} \
                        --verbosity info --kaniko-dir /tmp \
                        --log-format json --label commit=${COMMIT_HASH} --label build_date=${BUILD_DATE}
                    '''
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