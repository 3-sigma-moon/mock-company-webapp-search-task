pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building Stage'
                echo 'Jenkins Pipelines syntax is near to GitLab 👍'

                script {
                    gradlew assemble
                }
            }
        },
        stage('Test') {
            steps {
                echo 'Testing Stage'

                script {
                    gradlew test
                }
            }
        }
    }
}