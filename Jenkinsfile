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
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                    sh '''
                    echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin
                    docker push diouf173/scolaire_niass:latest
                    '''
                }
            }
        }
    }
}
