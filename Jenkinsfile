pipeline {
  agent any

  triggers {
    cron('0 2 * * 1-5') // 02:00 Пн–Пт
  }

  tools {
    gradle 'Gradle'
  }

  // ==== ПРЕДОХРАНИТЕЛИ (чтоб не зависало) ====
  options {
    timeout(time: 30, unit: 'MINUTES')                 // максимум на весь пайплайн
    disableConcurrentBuilds()                          // запрет параллельных запусков одной джобы
    buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '5'))
    durabilityHint('PERFORMANCE_OPTIMIZED')            // меньше шансов залипания PlaceholderExecutable
    skipDefaultCheckout(true)                          // мы сами делаем checkout в stage
    timestamps()
  }

  environment {
    HEADLESS_MODE     = "true"
    DOCKER_REMOTE     = "true"
    REMOTE_URL_DOCKER = "http://localhost:4444/wd/hub"
  }

  stages {
    stage('Checkout') {
      options { timeout(time: 3, unit: 'MINUTES') }
      steps {
        git branch: 'kaniet', url: 'https://github.com/kaniet-mukaev/Gucci.git'
      }
    }

    stage('Docker Grid UP') {
      options { timeout(time: 5, unit: 'MINUTES') }
      steps {
        sh '''
          set -euo pipefail

          docker compose down -v || true
          docker compose up -d

          echo "Waiting for Selenium Grid on :4444 ..."
          for i in {1..30}; do
            if curl -sf http://localhost:4444/status >/dev/null; then
              echo "Selenium Grid is up"
              break
            fi
            sleep 2
          done

          # покажем статус в логе (но не падаем, если вдруг 200 не пришёл — решит следующий stage по таймауту)
          curl -s http://localhost:4444/status || true
        '''
      }
    }

    stage('Run Smoke Tests') {
      options { timeout(time: 20, unit: 'MINUTES') }
      steps {
        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
          sh '''
            set -euo pipefail
            ./gradlew clean smokeTest --continue \
              -Dheadless.mode=${HEADLESS_MODE} \
              -Ddocker.remote=${DOCKER_REMOTE} \
              -Dremote.url.docker=${REMOTE_URL_DOCKER}
          '''
        }
      }
    }

    stage('Generate Allure Report') {
      options { timeout(time: 5, unit: 'MINUTES') }
      steps {
        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
          sh '''
            set -euo pipefail
            ./gradlew allureReport
          '''
        }
      }
    }

    stage('Download Allure Notifications Jar') {
      options { timeout(time: 3, unit: 'MINUTES') }
      steps {
        dir('..') {
          sh '''
            set -euo pipefail
            FILE=allure-notifications-4.8.0.jar
            if [ ! -f "$FILE" ]; then
              wget -q https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
            fi
          '''
        }
      }
    }

    stage('Send Telegram Notification') {
      options { timeout(time: 2, unit: 'MINUTES') }
      steps {
        sh '''#!/usr/bin/env bash
          set -euo pipefail

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

      allure includeProperties: false, results: [[path: 'build/allure-results']]

      withCredentials([string(credentialsId: 'slack-webhook', variable: 'SLACK_WEBHOOK')]) {
        sh '''#!/usr/bin/env bash
          set -euo pipefail

          REPORT_URL="${BUILD_URL}allure"

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
          DURATION=$(jq -r '.time.duration' allure-report/widgets/summary.json)

          MESSAGE="*Allure Report*\\nSmoke Tests завершены. Итог: ${TOTAL} тестов\\n*Duration:* ${DURATION}\\n\\n✅ Passed: ${PASSED}\\n❌ Broken: ${BROKEN}\\n⛔ Failed: ${FAILED}\\n⚪ Skipped: ${SKIPPED}\\n\\n📊 Отчёт: ${REPORT_URL}"

          curl -sSf -H 'Content-type: application/json' --data "{\\"text\\": \\"${MESSAGE}\\"}" "$SLACK_WEBHOOK" >/dev/null || true
        '''
      }

      sh 'docker compose down -v || true'
      cleanWs() // очистка воркспейса после архивации и остановки гридa
    }
    aborted {
      echo 'Build aborted by timeout or manual stop.'
    }
    failure {
      echo 'Build failed.'
    }
  }
}
