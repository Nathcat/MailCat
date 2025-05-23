plugins {
    id("buildlogic.kotlin-library-conventions")
} 

dependencies {
    implementation("org.apache.commons:commons-text")
}

sourceSets {
    main {
        kotlin {
            srcDir("src")
        }
    }
}