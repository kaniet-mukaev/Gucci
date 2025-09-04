pipeline {
    agent any

    tools {
        gradle 'Gradle' // имя Gradle из Jenkins Global Tool Configuration
    }

    environment {
        SLACK_TOKEN = 'xoxb-1234567890-abcdef' // твой токен
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean test'
            }
        }

        stage('Generate Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'build/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "📦 Архивируем артефакты и Allure отчёты"
            junit 'build/test-results/test/*.xml'
            archiveArtifacts artifacts: 'build/allure-results/**', allowEmptyArchive: true
            archiveArtifacts artifacts: 'allure-report/**', allowEmptyArchive: true

            echo "📤 Отправляем отчёт в Slack"
            sh """
                curl -F file=@allure-report/index.html \
                     -F "initial_comment=Allure Report for build #${env.BUILD_NUMBER}" \
                     -F channels=#your-slack-channel \
                     -H "Authorization: Bearer ${SLACK_TOKEN}" \
                     https://slack.com/api/files.upload
            """
        }
        success {
            echo "✅ Pipeline успешно завершён"
        }
        failure {
            echo "❌ Pipeline упал, но Allure отчёт и Slack отправлены"
        }
    }
}
