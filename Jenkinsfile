pipeline {
    agent any

    triggers {
        // Периодическая сборка (например, раз в день в 2:00 ночи)
        cron('0 2 * * *')
    }

    tools {
       gradle 'Gradle 7.0'
    }

    environment {
       SLACK_TOKEN = credentials('slack-api-token') // Токен Slack из Jenkins Secrets
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'aliia', url: 'https://github.com/kaniet-mukaev/Gucci'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Write Text File') {
            steps {
                sh '''
                    echo '{
                        "base": {
                            "project": "${JOB_BASE_NAME}",
                            "environment": "env",
                            "comment": "some comment",
                            "reportLink": "${BUILD_URL}",
                            "language": "en",
                            "allureFolder": "allure-report/",
                            "enableChart": true
                        },
                        "slack": {
                            "token": "${SLACK_TOKEN}",  // Здесь токен будет подставлен как переменная окружения
                            "chat": "C09D3RA3551",
                            "replyTo": ""
                        }
                    }' > build_status.json
                '''
            }
        }

        stage('Allure Report') {
            steps {
                // Allure работает при условии, что тесты генерируют отчет
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
        always {
            // Архивация артефактов
            archiveArtifacts artifacts: 'build_status.json', onlyIfSuccessful: false

            // Скрипт 1: Проверка и скачивание файла, если его нет
            sh '''
                cd ..
                FILE=allure-notifications-4.8.0.jar
                if [ ! -f "$FILE" ]; then
                    wget https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
                fi
            '''

            // Скрипт 2: Запуск allure-notifications с конфигом
            sh '''
                java -DconfigFile=notifications/config.json -jar ../allure-notifications-4.8.0.jar
            '''
        }
    }
}
