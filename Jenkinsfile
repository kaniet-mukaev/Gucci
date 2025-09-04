pipeline {
    agent any

    triggers {
        cron('0 2 * * 1-5') // запуск в 02:00 с понедельника по пятницу
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'kaniet', url: 'https://github.com/kaniet-mukaev/Gucci.git'
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew clean smokeTest'
            }
            post {
                always {
                    // JUnit (чтобы Jenkins понимал количество тестов)
                    junit 'build/test-results/smokeTest/*.xml'

                    // Allure (чтобы был красивый отчёт)
                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'build/allure-results']]
                    ])
                }
            }
        }

        stage('Send Allure Report to Slack') {
            steps {
                sh '''
                    if [ ! -f allure-notifications-4.8.0.jar ]; then
                        echo "❌ JAR файл не найден в корне проекта!"
                        exit 1
                    fi

                    java -DconfigFile=notifications/config.json -jar allure-notifications-4.8.0.jar
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline успешно завершён'
        }
        failure {
            echo '❌ Pipeline упал, проверь логи'
        }
    }
}
