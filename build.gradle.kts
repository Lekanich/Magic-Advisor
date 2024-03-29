import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.date
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    // gradle-intellij-plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
    id("org.jetbrains.intellij") version "1.16.1"
    // gradle-changelog-plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
    id("org.jetbrains.changelog") version "2.2.0"
    // detekt linter - read more: https://detekt.github.io/detekt/gradle.html
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
    // ktlint linter - read more: https://github.com/JLLeitschuh/ktlint-gradle
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

println("ArtifactVersion is : $version")

// Configure project's dependencies
repositories {
    mavenCentral()
}
dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.4")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation("junit:junit:4.13.2")
}

// Configure gradle-intellij-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))
    downloadSources.set(properties("platformDownloadSources").toBoolean())
    updateSinceUntilBuild.set(true)

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

// Configure gradle-changelog-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version.set(properties("pluginVersion"))
    header.set(provider { "[${version.get()}] - ${date()}" })
    itemPrefix.set("-")
    keepUnreleasedSection.set(false)
    groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Security"))
}

// Configure detekt plugin.
// Read more: https://detekt.github.io/detekt/kotlindsl.html
detekt {
    config.setFrom("./detekt-config.yml")
    buildUponDefaultConfig = true
}

tasks {
    // Set the compatibility versions to 1.8
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    withType<Detekt> {
        jvmTarget = "17"
        reports {
            // Enable/Disable XML report (default: true)
            xml.required.set(false)
            xml.outputLocation.set(file("build/reports/detekt.xml"))
            // Enable/Disable HTML report (default: true)
            html.required.set(true)
            html.outputLocation.set(file("build/reports/detekt.html"))
            // Enable/Disable TXT report (default: true)
            txt.required.set(false)
            txt.outputLocation.set(file("build/reports/detekt.txt"))
        }
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        if (project.hasProperty("pluginUntilBuild")) {
            untilBuild.set(properties("pluginUntilBuild"))
        }

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
            File(projectDir, "README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run { markdownToHTML(this) }
        )

        // Get the latest available change notes from the changelog file
        changeNotes.set(provider {
            changelog.getAll()
                .filterKeys { it != "[Unreleased]" }
                .values.joinToString("") {
                    changelog.renderItem(it.withHeader(true), Changelog.OutputType.HTML)
                }
        })
    }

    runIde {
//        jvmArgs.add("-Didea.ProcessCanceledException=disabled")
    }

    // TODO: signPlugin

//    //https://data.services.jetbrains.com/products?fields=code,name,releases.downloads,releases.version,releases.build,releases.type&code=IIC,IIU
    runPluginVerifier {
        ideVersions.set(properties("pluginVerifierIdeVersions").split(',').map(String::trim).filter(String::isNotEmpty))
    }

    publishPlugin {
        dependsOn("patchChangelog")
        if (File("token.txt").exists()) {
            token.set(File("token.txt").readText(Charsets.UTF_8))
        }
//        token.set(System.getenv("PUBLISH_TOKEN"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first()))
    }
}
