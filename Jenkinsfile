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
              wget -q https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
            fi
          '''
        }
      }
    }

    stage('Send Telegram Notification') {
      steps {
        sh '''#!/usr/bin/env bash
          set -euo pipefail

          # 1) Подставим реальный BUILD_URL внутрь notifications/config.json
          sed "s#\\${BUILD_URL}#${BUILD_URL%/}#g" notifications/config.json > notifications/config.resolved.json

          # 2) Урежем болтливые логи
          JAVA_OPTS="${JAVA_OPTS:-} \
            -Dorg.slf4j.simpleLogger.defaultLogLevel=warn \
            -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog \
            -Dorg.apache.commons.logging.simplelog.log.org.apache.http=warn \
            -Dorg.apache.commons.logging.simplelog.log.org.apache.http.wire=off"

          # 3) Запускаем отправку
          java $JAVA_OPTS -DconfigFile=notifications/config.resolved.json \
               -jar ../allure-notifications-4.8.0.jar
        '''
      }
    }
  } // 👈 закрыл stages

  post {
    always {
      echo '📦 Архивируем отчёты'
      junit 'build/test-results/smokeTest/*.xml'
      archiveArtifacts artifacts: 'build/allure-results/**', fingerprint: true
      archiveArtifacts artifacts: 'build/reports/allure-report/**', fingerprint: true
      archiveArtifacts artifacts: 'build/reports/allure-report/widgets/*', fingerprint: true
      archiveArtifacts artifacts: 'build/reports/allure-report/widgets/chart.png', fingerprint: true


      // Публикация Allure в Jenkins (даст стабильный ${BUILD_URL}allure)
      allure includeProperties: false, results: [[path: 'build/allure-results']]

      // Slack-уведомление после архивирования
      withCredentials([string(credentialsId: 'slack-webhook', variable: 'SLACK_WEBHOOK')]) {
        sh '''#!/usr/bin/env bash
          set -euo pipefail

          REPORT_URL="${BUILD_URL}allure"
          IMAGE_URL="${BUILD_URL}artifact/build/reports/allure-report/widgets/chart.png"

          # Читаем статистику из summary.json
          STATS=$(cat build/reports/allure-report/widgets/summary.json | jq -r '.statistic')
          PASSED=$(echo $STATS | jq -r '.passed')
          BROKEN=$(echo $STATS | jq -r '.broken')
          TOTAL=$(echo $STATS | jq -r '.total')

          PAYLOAD=$(cat <<JSON
{
  "attachments": [{
    "fallback": "Allure Report",
    "color": "#36a64f",
    "title": "Allure Report",
    "title_link": "${REPORT_URL}",
    "text": "Smoke Tests завершены. Итог: ${TOTAL} тестов (✅ Passed: ${PASSED}, ❌ Broken: ${BROKEN})",
    "image_url": "${IMAGE_URL}"
  }]
}
JSON
)

          curl -sSf -H 'Content-type: application/json' --data "$PAYLOAD" "$SLACK_WEBHOOK" >/dev/null || {
            echo "Slack webhook failed, sending text fallback…"
            curl -sS -H 'Content-type: application/json' \
                 --data "{\\"text\\":\\"📊 Allure Report: ${REPORT_URL}\\n✅ Passed: ${PASSED}\\n❌ Broken: ${BROKEN}\\"}" \
                 "$SLACK_WEBHOOK" >/dev/null || true
          }
        '''
      }
    }
  }
}
