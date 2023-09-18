plugins {
    checkstyle
    id("org.ec4j.editorconfig") version "0.0.3"
    id("io.freefair.lombok") version "8.0.1"
    kotlin("jvm") version "1.8.20"
}

project(":data-access") {
    dependencies {
        implementation(project(":domain-model"))
        implementation(project(":contracts"))
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        implementation(
            group = "com.fasterxml.jackson.core",
            name = "jackson-core",
            version = "2.13.3"
        )
        implementation(
            group = "com.fasterxml.jackson.core",
            name = "jackson-databind",
            version = "2.13.3"
        )
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
