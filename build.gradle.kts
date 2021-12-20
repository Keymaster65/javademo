import com.github.jk1.license.filter.LicenseBundleNormalizer

plugins {
    java
    id("com.github.jk1.dependency-license-report") version "2.0"
    id("com.github.hierynomus.license-base") version "0.16.1"
}

group = "io.github.keymaster65"
version = "2.2"

allprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // see https://github.com/hierynomus/license-gradle-plugin
    apply(plugin = "com.github.hierynomus.license")
    license {
        setIgnoreFailures(false)
        setHeader(File("$rootDir/licenceHeader.txt"))
        setSkipExistingHeaders(false)
        exclude("**/*.json")
        exclude("**/test.html")
    }

    dependencies {
        implementation("org.slf4j:slf4j-api:2.0.0-alpha5")
        implementation("ch.qos.logback:logback-classic:1.3.0-alpha10")

        testImplementation("org.assertj:assertj-assertions-generator:2.+")
        testImplementation("net.jqwik:jqwik:1.+")

        testImplementation("org.junit.jupiter:junit-jupiter:5.+")
        testImplementation("org.mockito:mockito-core:4.+")
    }

    dependencyLocking {
        lockAllConfigurations()
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            includeEngines.add("jqwik")
        }
        systemProperty("logback.configurationFile", "src/main/resources/logback.xml")
    }
}


// visit https://github.com/jk1/Gradle-License-Report for help
licenseReport {
    outputDir = "$projectDir/build/resources/main/license"
    excludeOwnGroup = true
    allowedLicensesFile = File("$projectDir/allowed-licenses.json")
    excludes = arrayOf<String>("com.fasterxml.jackson:jackson-bom") // is apache 2.0 but license tool say "null"
    filters = arrayOf<LicenseBundleNormalizer>(LicenseBundleNormalizer("""$projectDir/license-normalizer-bundle.json""", true))
}

tasks.assemble {
    dependsOn(tasks.findByName("checkLicense"))
}
