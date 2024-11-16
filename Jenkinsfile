pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
    stages {
        stage('Récupérer depuis Git') {
            steps {
                git branch: 'main', url: 'https://github.com/papadiouf13/scolaire_niass.git'
            }
        }
        stage('Nettoyer') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Tester') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Emballer') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Construire l’image Docker') {
            steps {
                script {
                    sh 'docker build -t diouf173/scolaire_niass:latest .'
                }
            }
        }
        stage('Pousser l’image Docker') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-credentials', variable: 'DOCKER_HUB_TOKEN')]) {
                    sh '''
                    echo $DOCKER_HUB_TOKEN | docker login -u diouf173 --password-stdin
                    docker push diouf173/scolaire_niass:latest
                    '''
                }
            }
        }
    }
}
