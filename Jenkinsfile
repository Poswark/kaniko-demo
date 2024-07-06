pipeline {
    agent any
    environment {
        COMMIT = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
        BUILD_DATE = sh(script: 'date -u +"%Y-%m-%dT%H:%M:%SZ"', returnStdout: true).trim()
        BRANCH_NAME = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
        IMAGE_NAME = "poswark/kaniko-demo"
        IMAGE_TAG = "1.0.8"
    }
    parameters {
        //choice(name: 'NODE_VERSION', choices: ['14', '22.4.0', '21.4.0'], description: 'Node.js version to use')
        choice(name: 'TECHNOLOGY', choices: ['node', 'python'], description: 'Technology to use')
        string(name: 'NODE_VERSION', defaultValue: '22.4.0', description: 'Node.js version to use (if node is selected)')
        string(name: 'PYTHON_VERSION', defaultValue: '3.9', description: 'Python version to use (if python is selected)')
    }
    stages {
        stage('Build and Push with Kaniko') {
            steps {
                script {
                    def dockerfile
                    def buildArgs
                    //def buildArgs = "--build-arg COMMIT=${env.COMMIT} --build-arg BUILD_DATE=${env.BUILD_DATE} --build-arg BRANCH_NAME=${env.BRANCH_NAME}"

                    if (params.TECHNOLOGY == 'node') {
                        dockerfile = '/workspace/Dockerfile.node'
                        buildArgs = "${params.NODE_VERSION}"
                    } else if (params.TECHNOLOGY == 'python') {
                        dockerfile = '/workspace/Dockerfile.python'
                        buildArgs = "--build-arg PYTHON_VERSION=${params.PYTHON_VERSION}"
                    }

                    sh """
                    docker run --rm \
                        -v `pwd`:/workspace  \
                        poswark/executor-debug:1.0.0 \
                        --context "/workspace" \
                        --dockerfile "${dockerfile}" \
                        --destination ${IMAGE_NAME}:${IMAGE_TAG} \
                        --verbosity debug \
                        --kaniko-dir /tmp \
                        --log-format json  \
                        --build-arg VERSION=${buildArgs} \
                        --label commit=${COMMIT} \
                        --label build_date=${env.BUILD_DATE} \
                        --label branch=${env.BRANCH_NAME}  --no-push-cache
                        
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