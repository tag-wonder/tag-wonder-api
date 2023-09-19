plugins {
    checkstyle
    id("org.ec4j.editorconfig") version "0.0.3"
    kotlin("jvm") version "1.8.20"
}

project(":api") {
    dependencies {
        implementation(project(":contracts"))
        implementation(project(":domain-model"))
        implementation(project(":data-access"))
        implementation(project(":gateway"))

        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("io.jsonwebtoken:jjwt-api:0.11.5")
        implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
        implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        implementation("io.springfox:springfox-swagger-ui:3.0.0")
        implementation("io.springfox:springfox-boot-starter:3.0.0")

        implementation("org.jetbrains.kotlin:kotlin-reflect")

        runtimeOnly("com.mysql:mysql-connector-j")

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
        implementation(
            group = "com.fasterxml.jackson.core",
            name = "jackson-annotations",
            version = "2.12.4"
        )
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:2.13.3")
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
        enabled = true
    }
}
