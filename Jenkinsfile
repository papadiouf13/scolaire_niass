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
                git branch: 'main', url: 'https://github.com/papadiouf13/scolaire_niass.git'
            }
        }
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
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
                        sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=scolaire_niass \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.projectKey=scolaire_niass \
                        -Dsonar.login=$SONAR_TOKEN \
                        -Dsonar.java.libraries=target/**/*.jar '''
                    }
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
