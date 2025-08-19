plugins {
    id("java")
}

group = "com.gucci"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.codeborne:selenide:7.9.4")
    implementation("org.projectlombok:lombok:1.18.38")
    implementation("io.github.bonigarcia:webdrivermanager:6.2.0")
    implementation("io.qameta.allure:allure-selenide:2.29.1")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    implementation("org.aeonbits.owner:owner:1.0.12")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}