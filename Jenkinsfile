pipeline {
    agent any

    environment {
        IMAGE_NAME = "calculator-app"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Sahas-out/sampleWorkflow',
                    credentialsId: 'github-creds'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Push Image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "docker tag ${IMAGE_NAME}:latest $DOCKER_USER/${IMAGE_NAME}:latest"
                        sh "docker push $DOCKER_USER/${IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Pull Image from Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "docker pull $DOCKER_USER/${IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Run Container') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "docker run -d -p 5001:5001 --name calc $DOCKER_USER/${IMAGE_NAME}:latest"
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished. Cleaning up..."
        }
    }
}

