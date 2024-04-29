pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building Stage Start'
                bat './gradlew assemble'
                echo 'Building Stage End'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing Stage Start'
                bat './gradlew test'
                echo 'Testing Stage End'
            }
        }
    }
}