package quality

import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

val runDetekt by tasks.registering(Detekt::class) {
    buildUponDefaultConfig = true
    parallel = true

    val outputFile = "${rootProject.buildDir}/reports/detekt/index.html"

    setSource(files(rootProject.projectDir))

    val configDir = "${rootProject.projectDir}/buildSrc/src/config/"
    config.setFrom("${configDir}detekt-config.yml")
    baseline.set(file("${configDir}/detekt-baseline.xml"))

    include("**/*.kt")
    exclude("**/*.kts", "*/build/*", "/buildSrc")

    reports {
        html.enabled = true
        html.destination = file(outputFile)
        xml.enabled = false
        txt.enabled = false
    }

    val reportFile = "Detekt Analysis Report: $outputFile \n"

    doFirst {
        println("********* Starting detekt analysis *********")
    }
    doLast {
        println(reportFile)
        println("********* detekt analysis Finished *********")
    }
}
