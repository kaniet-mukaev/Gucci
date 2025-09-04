pipeline {
  agent any

  triggers {
    // 02:00 Пн–Пт
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

          # Подставляем реальный BUILD_URL
          sed "s#\\${BUILD_URL}#${BUILD_URL%/}#g" notifications/config.json > notifications/config.resolved.json

          JAVA_OPTS="${JAVA_OPTS:-} \
            -Dorg.slf4j.simpleLogger.defaultLogLevel=warn \
            -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog \
            -Dorg.apache.commons.logging.simplelog.log.org.apache.http=warn \
            -Dorg.apache.commons.logging.simplelog.log.org.apache.http.wire=off"

          java $JAVA_OPTS -DconfigFile=notifications/config.resolved.json \
               -jar ../allure-notifications-4.8.0.jar
        '''
      }
    }
  }

  post {
    always {
      echo '📦 Архивируем отчёты'
      junit 'build/test-results/smokeTest/*.xml'
      archiveArtifacts artifacts: 'build/allure-results/**', fingerprint: true
      archiveArtifacts artifacts: 'allure-report/**', fingerprint: true
      archiveArtifacts artifacts: 'allure-report/widgets/*', fingerprint: true

      // Публикация Allure в Jenkins
      allure includeProperties: false, results: [[path: 'build/allure-results']]

      // Slack-уведомление
      withCredentials([string(credentialsId: 'slack-webhook', variable: 'SLACK_WEBHOOK')]) {
        sh '''#!/usr/bin/env bash
          set -euo pipefail

          REPORT_URL="${BUILD_URL}allure"
          IMAGE_URL="${BUILD_URL}artifact/allure-report/widgets/chart.png"

          if [ ! -f allure-report/widgets/summary.json ]; then
            echo "❌ summary.json не найден, Slack уведомление пропущено"
            exit 0
          fi

          STATS=$(cat allure-report/widgets/summary.json | jq -r '.statistic')
          PASSED=$(echo $STATS | jq -r '.passed')
          BROKEN=$(echo $STATS | jq -r '.broken')
          FAILED=$(echo $STATS | jq -r '.failed')
          SKIPPED=$(echo $STATS | jq -r '.skipped')
          TOTAL=$(echo $STATS | jq -r '.total')

          PAYLOAD=$(cat <<JSON
{
  "attachments": [{
    "fallback": "Allure Report",
    "color": "#36a64f",
    "title": "Allure Report",
    "title_link": "${REPORT_URL}",
    "text": "Smoke Tests завершены. Итог: ${TOTAL} тестов\\n✅ Passed: ${PASSED}\\n❌ Broken: ${BROKEN}\\n⛔ Failed: ${FAILED}\\n⚪ Skipped: ${SKIPPED}",
    "image_url": "${IMAGE_URL}"
  }]
}
JSON
)

          curl -sSf -H 'Content-type: application/json' --data "$PAYLOAD" "$SLACK_WEBHOOK" >/dev/null || {
            echo "Slack webhook failed, sending text fallback…"
            curl -sS -H 'Content-type: application/json' \
                 --data "{\\"text\\":\\"📊 Allure Report: ${REPORT_URL}\\n✅ Passed: ${PASSED}\\n❌ Broken: ${BROKEN}\\n⛔ Failed: ${FAILED}\\n⚪ Skipped: ${SKIPPED}\\"}" \
                 "$SLACK_WEBHOOK" >/dev/null || true
          }
        '''
      }
    }
  }
}
