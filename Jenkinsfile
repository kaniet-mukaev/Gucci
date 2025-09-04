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
                // шлёт и картинку, и статистику в TG
                sh '''
                    java -DconfigFile=notifications/config.json \
                         -jar ../allure-notifications-4.8.0.jar
                '''
            }
        }

        stage('Send Slack Notification (Webhook)') {
            steps {
                // читаем URL из файла вне репозитория; не печатаем значение в логи
                sh '''#!/usr/bin/env bash
                  set -euo pipefail

                  SLACK_WEBHOOK_URL="$(cat /etc/jenkins-slack/webhook.url)"

                  # Конструируем ссылки
                  REPORT_URL="${BUILD_URL}allure"
                  IMAGE_URL="${BUILD_URL}artifact/build/reports/allure-report/allureReport/widgets/summary.png"

                  # JSON грузим через here-doc, чтобы не экранировать кавычки
                  read -r -d "" PAYLOAD <<'JSON'
{
  "attachments": [
    {
      "fallback": "Allure Report",
      "color": "#36a64f",
      "title": "Allure Report",
      "title_link": "REPORT_URL_PLACEHOLDER",
      "text": "Smoke Tests завершены. Ссылка на отчёт ниже 👇",
      "image_url": "IMAGE_URL_PLACEHOLDER"
    }
  ]
}
JSON

                  # Подставим URLs внутрь payload
                  PAYLOAD="${PAYLOAD/REPORT_URL_PLACEHOLDER/${REPORT_URL}}"
                  PAYLOAD="${PAYLOAD/IMAGE_URL_PLACEHOLDER/${IMAGE_URL}}"

                  # Отправляем. В логах будет виден только $SLACK_WEBHOOK_URL, не реальный URL
                  curl -sSf -X POST -H 'Content-type: application/json' \
                       --data "$PAYLOAD" \
                       "$SLACK_WEBHOOK_URL" >/dev/null || {
                    echo "Slack webhook send failed (text fallback)..."
                    # Фолбек: хотя бы текст
                    curl -sS -X POST -H 'Content-type: application/json' \
                         --data "{\"text\":\"📊 Allure Report: ${REPORT_URL}\"}" \
                         "$SLACK_WEBHOOK_URL" >/dev/null || true
                  }
                '''
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
