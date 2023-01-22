plugins {
    id("java")
    application
}

group = "com.mazylol.weathercord"
version = "0.1.0"

val jdaVersion = "5.0.0-beta.3"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion") {
        exclude(module = "opus-java")
    }
    implementation("io.github.cdimascio:dotenv-java:2.3.1")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation("com.fasterxml.jackson.core:jackson-core:2.11.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.11.1")
    implementation("com.google.code.gson:gson:2.10")
}

application {
    mainClass.set("$group.Bot")
}

distributions {
    main {
        contents {
            from(".env") {
                into("bin")
            }
        }
    }
}