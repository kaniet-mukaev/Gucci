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
                // Если джоба настроена "Pipeline script from SCM", этот шаг можно убрать,
                // но оставим, чтобы явно взять ветку kaniet.
                git branch: 'kaniet', url: 'https://github.com/kaniet-mukaev/Gucci.git'
            }
        }

        stage('Run Smoke Tests') {
            steps {
                // Не роняем пайплайн при фейлах тестов — даём пройти дальше
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh './gradlew clean smokeTest --continue'
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                // Даже если были фейлы — отчёт соберётся
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

        stage('Send Notifications') {
            steps {
                sh '''
                    # На всякий случай покажем где отчёт (для диагностики)
                    ls -la build/reports/allure-report || true
                    ls -la build/reports/allure-report/allureReport || true

                    java -DconfigFile=notifications/config.json \
                         -jar ../allure-notifications-4.8.0.jar
                '''
            }
        }
    }

    post {
        always {
            echo "📦 Архивируем артефакты и Allure отчёты"
            // Результаты именно smokeTest
            junit 'build/test-results/smokeTest/*.xml'
            // Сырые результаты Allure
            archiveArtifacts artifacts: 'build/allure-results/**', fingerprint: true
            // Готовый html-отчёт Gradle Allure Plugin
            archiveArtifacts artifacts: 'build/reports/allure-report/**', fingerprint: true
        }
    }
}
