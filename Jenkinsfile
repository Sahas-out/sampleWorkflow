pipeline {
    agent any

    environment {
        IMAGE_NAME = "calculator-app"
        // DOCKER_HUB_USER = "your-dockerhub-username"   // Change this
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
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_NAME}:latest ."
                }
            }
        }

        stage('Login & Push Image to Docker Hub') {
            steps {
                script {
                    // withCredentials([string(credentialsId: 'dockerhub-token', variable: 'TOKEN')]) {
                    withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "echo $DOCKER_PASS | docker login -u ${DOCKER_USER} --password-stdin"
                        sh "docker tag ${IMAGE_NAME}:latest ${DOCKER_USER}/${IMAGE_NAME}:latest"
                        sh "docker push ${DOCKER_USER}/${IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Pull Image from Docker Hub') {
            steps {
                sh "docker pull ${DOCKER_USER}/${IMAGE_NAME}:latest"
            }
        }

        stage('Run Container from Pulled Image') {
            steps {
                script {
                    sh "docker rm -f calc || true"
                    sh "docker run -d -p 8080:8080 --name calc ${DOCKER_USER}/${IMAGE_NAME}:latest"
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

