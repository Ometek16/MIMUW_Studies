plugins {
    id("java")
    application
}

group = "pl.edu.mimuw"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("pl.edu.mimuw.Main")
}

tasks.getByName<JavaExec> ("run") {
    standardInput = System.`in`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
