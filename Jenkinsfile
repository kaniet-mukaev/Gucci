pipeline {
    agent any

    triggers {
        // запуск в 02:00 с понедельника по пятницу
        cron('0 2 * * 1-5')
    }

    tools {
        gradle 'Gradle'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'kaniet', url: 'https://github.com/kaniet-mukaev/Gucci.git'
            }
        }

        stage('Run Smoke Tests') {
            steps {
                sh './gradlew clean smokeTest'
            }
        }

        stage('Generate Allure Report') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh './gradlew allureReport'
                }
            }
        }

        stage('Download Allure Notifications Jar') {
            steps {
                dir('..') {
                    sh '''
                        FILE=allure-notifications-4.8.0.jar
                        if [ ! -f "$FILE" ]; then
                          wget https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
                        fi
                    '''
                }
            }
        }

        stage('Send Notifications') {
            steps {
                sh '''
                    java -DconfigFile=notifications/config.json \
                         -jar ../allure-notifications-4.8.0.jar
                '''
            }
        }
    }

    post {
        always {
            echo "📦 Архивируем артефакты и Allure отчёты"
            // правильный путь для smokeTest
            junit 'build/test-results/smokeTest/*.xml'
            archiveArtifacts artifacts: 'build/allure-results/**', fingerprint: true
            archiveArtifacts artifacts: 'build/reports/allure-report/**', fingerprint: true
        }
    }
}
