pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven3'
        sonarScanner 'sonar-scanner' // Ajoutez sonarScanner ici
    }
    stages {
        stage('Checkout From Git') {
            steps {
                git branch: 'main', url: 'https://github.com/papadiouf13/scolaire_niass.git'
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
                withSonarQubeEnv('sonar-server') {
                    sh 'sonar-scanner -Dsonar.projectName=scolaire_niass -Dsonar.java.binaries=. -Dsonar.projectKey=scolaire_niass'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                }
            }
        }
    }
}
