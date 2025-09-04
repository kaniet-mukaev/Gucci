pipeline {
    agent any

    triggers {
        // 02:00 Пн–Пт
        cron('0 2 * * 1-5')
    }

    tools {
        gradle 'Gradle' // имя из Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'kaniet', url: 'https://github.com/kaniet-mukaev/Gucci.git'
            }
        }

        stage('Run Smoke Tests') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh './gradlew clean smokeTest --continue'
                }
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

        stage('Send Telegram Notification') {
            steps {
                // шлёт и картинку, и статистику в TG (использует notifications/config.json)
                sh '''
                    java -DconfigFile=notifications/config.json \
                         -jar ../allure-notifications-4.8.0.jar
                '''
            }
        }

        stage('Send Slack Notification (Webhook)') {
            steps {
                // Храним Slack webhook в Jenkins Credentials:
                // Manage Jenkins → Credentials → Global → Add → Secret text → ID: slack-webhook
                withCredentials([string(credentialsId: 'slack-webhook', variable: 'SLACK_WEBHOOK')]) {
                    sh '''#!/usr/bin/env bash
                      set -euo pipefail

                      REPORT_URL="${BUILD_URL}"  # можно заменить на страницу отчёта-артефакта при желании
                      IMAGE_URL="${BUILD_URL}artifact/build/reports/allure-report/allureReport/widgets/summary.png"

                      # Собираем JSON с РЕАЛЬНЫМИ ссылками (переменные shell расширяются внутри heredoc без кавычек)
                      PAYLOAD=$(cat <<JSON
{
  "attachments": [
    {
      "fallback": "Allure Report",
      "color": "#36a64f",
      "title": "Allure Report",
      "title_link": "${REPORT_URL}",
      "text": "Smoke Tests завершены. Ссылка ниже 👇",
      "image_url": "${IMAGE_URL}"
    }
  ]
}
JSON
)

                      # Отправляем (значение SLACK_WEBHOOK замаскировано в консолях Jenkins)
                      curl -sSf -H 'Content-type: application/json' \
                           --data "$PAYLOAD" \
                           "$SLACK_WEBHOOK" >/dev/null || {
                        echo "Slack webhook send failed, trying text fallback..."
                        curl -sS -H 'Content-type: application/json' \
                             --data "{\"text\":\"📊 Allure Report: ${REPORT_URL}\"}" \
                             "$SLACK_WEBHOOK" >/dev/null || true
                      }
                    '''
                }
            }
        }
    }

    post {
        always {
            echo "📦 Архивируем артефакты и Allure отчёты"
            junit 'build/test-results/smokeTest/*.xml'
            archiveArtifacts artifacts: 'build/allure-results/**', fingerprint: true
            archiveArtifacts artifacts: 'build/reports/allure-report/**', fingerprint: true
        }
    }
}
