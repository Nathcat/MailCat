plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":SMTP"))
}

application {
    mainClass = "net.nathcat.SMTP.server.MainKt"
}