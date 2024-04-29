pipeline {
    agent any
    options {
        // Timeout counter starts AFTER agent is allocated
        timeout(time: 5, unit: 'SECONDS')
    }
    stages {
        stage('Example') {
            steps {
                echo 'Hello World'
                bat 'java --version'
                bat 'pwd'
                bat 'dir'
            }
        }
    }
}