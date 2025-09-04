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
                          wget https://github.com/qa-guru/allure-notifications/releases/download/4.8.0/allure-notifications-4.8.0.jar
                        fi
                    '''
                }
            }
        }

        stage('Send Telegram Notification') {
            steps {
                sh '''
                    java -DconfigFile=notifications/config.json \
                         -jar ../allure-notifications-4.8.0.jar
                '''
            }
        }

        stage('Send Slack Notification (Webhook)') {
            steps {
                sh '''
                  curl -X POST -H 'Content-type: application/json' \
                  --data "{
                    \\"attachments\\": [
                      {
                        \\"fallback\\": \\"Allure Report\\",
                        \\"color\\": \\"#36a64f\\",
                        \\"title\\": \\"Allure Report\\",
                        \\"title_link\\": \\"${BUILD_URL}\\",
                        \\"text\\": \\"Smoke Tests завершены. Duration: 00:04:15. Ссылка ниже 👇\\",
                        \\"image_url\\": \\"${BUILD_URL}artifact/build/reports/allure-report/allureReport/widgets/summary.png\\"
                      }
                    ]
                  }" \
                  https://hooks.slack.com/services/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
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
