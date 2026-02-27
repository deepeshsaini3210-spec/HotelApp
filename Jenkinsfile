pipeline {
    agent any

    environment {
        IMAGE_NAME = "grandstay/hotel-app"
        IMAGE_TAG = "latest"
        DOCKER_REGISTRY = "docker.io"
        DOCKER_REGISTRY_USERNAME = "grandstay"
        DOCKER_REGISTRY_PASSWORD = "grandstay"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('backend') {
                    sh "docker build -t ${env.IMAGE_NAME}:${env.IMAGE_TAG} ."
                }
            }
        }

        stage('Push Image') {
            steps {
                sh "docker push ${env.IMAGE_NAME}:${env.IMAGE_TAG}"
            }
        }

        stage('Deploy') {
            when {
                expression { fileExists('backend/k8s/deployment.yaml') }
            }
            steps {
                sh 'kubectl apply -f backend/k8s/deployment.yaml'
            }
        }
    }
}