plugins {
    checkstyle
    id("org.ec4j.editorconfig") version "0.0.3"
    id("io.freefair.lombok") version "8.0.1"
    kotlin("jvm") version "1.8.20"
}

project(":gateway") {
    dependencies {
        implementation(project(":contracts"))
        implementation(project(":domain-model"))
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.core:jackson-databind")
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
