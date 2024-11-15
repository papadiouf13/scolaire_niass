pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
    environment {
        SCANNER_HOME=tool 'sonar-scanner'
    }
    stages {
        stage('Checkout From Git') {
            steps {
                git branch: 'main', url: 'https://github.com/diouf173/scolaire_niass.git'
            }
        }
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Sonarqube Analysis') {
            steps {
                withSonarQubeEnv('sonar-scanner') {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=examen \
                        -Dsonar.java.binaries=. \
                        -Dsonar.projectKey=examen \
                        -Dsonar.login=$SONAR_TOKEN '''
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t diouf173/scolaire_niass:latest .'
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([string(credentialsId: 'docker-hub-credentials', variable: 'DOCKER_PASSWORD')]) {
                    sh """
                    echo $DOCKER_PASSWORD | docker login -u diouf173 --password-stdin
                    docker push diouf173/scolaire_niass:latest
                    """
                }
            }
        }
    }
}
