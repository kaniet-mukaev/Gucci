 pipeline {
   agent any

   triggers {
     // 02:00 Пн–Пт
     cron('0 2 * * 1-5')
   }

   tools {
     gradle 'Gradle'
   }

+  environment {
+    // Позволяет не менять app.properties, а переопределить удалённый запуск на Grid
+    HEADLESS_MODE     = "true"
+    DOCKER_REMOTE     = "true"
+    REMOTE_URL_DOCKER = "http://localhost:4444/wd/hub"
+  }

   stages {
     stage('Checkout') {
       steps {
         git branch: 'kaniet', url: 'https://github.com/kaniet-mukaev/Gucci.git'
       }
     }

+    stage('Docker Grid UP') {
+      steps {
+        sh '''
+          # Поднимаем Grid из docker-compose в корне репо (или используем свой путь)
+          docker compose down -v || true
+          docker compose up -d
+
+          echo "Waiting for Selenium Grid on :4444 ..."
+          for i in {1..30}; do
+            curl -sf http://localhost:4444/status >/dev/null && break || sleep 2
+          done
+          curl -s http://localhost:4444/status || true
+        '''
+      }
+    }

     stage('Run Smoke Tests') {
       steps {
         catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
-          sh './gradlew clean smokeTest --continue'
+          sh '''
+            ./gradlew clean smokeTest --continue \
+              -Dheadless.mode=${HEADLESS_MODE} \
+              -Ddocker.remote=${DOCKER_REMOTE} \
+              -Dremote.url.docker=${REMOTE_URL_DOCKER}
+          '''
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
@@
   post {
     always {
       echo '📦 Архивируем отчёты'
       junit 'build/test-results/smokeTest/*.xml'
       archiveArtifacts artifacts: 'build/allure-results/**', fingerprint: true
       archiveArtifacts artifacts: 'allure-report/**', fingerprint: true

       // Публикация Allure в Jenkins
       allure includeProperties: false, results: [[path: 'build/allure-results']]
@@
         '''
       }
+
+      // Корректно выключаем Grid, чтобы не держать контейнеры
+      sh 'docker compose down -v || true'
     }
   }
 }
