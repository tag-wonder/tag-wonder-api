plugins {
    checkstyle
    id("org.ec4j.editorconfig") version "0.0.3"
    id("io.freefair.lombok") version "8.0.1"
    kotlin("jvm") version "1.8.20"
}

project(":domain-model") {
    dependencies {
        implementation(project(":contracts"))
        implementation("io.jsonwebtoken:jjwt-api:0.11.5")
        implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
        implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    }
    checkstyle {
        configFile = file("../.rules/checkstyle/checkstyle.xml")
        toolVersion = "8.40"
    }
    tasks.jar {
        enabled = true
        dependsOn("editorconfigCheck", "checkstyleMain")
    }
    tasks.bootJar {
        enabled = false
    }
}
