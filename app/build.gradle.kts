import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.amos_tech_code.knowledgebase"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.amos_tech_code.knowledgebase"
        minSdk = 24
        targetSdk = 36
        versionCode = 6
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("debugMinified") {
            initWith(getByName("debug"))
            matchingFallbacks.add("debug")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Detekt plugins for additional rules
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.23.7")
    detektPlugins("com.twitter.compose.rules:detekt:0.0.26")
}

// Bump version
tasks.register("bumpVersion") {
    group = "versioning"
    description = "Increments versionCode and versionName (patch version) in build.gradle.kts"

    doLast {
        val buildFile = project.file("build.gradle.kts")
        var content = buildFile.readText()

        // Increment versionCode
        val versionCodeRegex = Regex("versionCode = (\\d+)")
        val currentVersionCodeMatch = versionCodeRegex.find(content)
        if (currentVersionCodeMatch != null) {
            val currentCode = currentVersionCodeMatch.groupValues[1].toInt()
            val newCode = currentCode + 1
            content = content.replace("versionCode = $currentCode", "versionCode = $newCode")
            println("Bumped versionCode: $currentCode -> $newCode")
        }

        // Increment versionName (assuming SEMVER Major.Minor.Patch)
        val versionNameRegex = Regex("versionName = \"(\\d+)\\.(\\d+)\\.(\\d+)\"")
        val currentVersionNameMatch = versionNameRegex.find(content)
        if (currentVersionNameMatch != null) {
            val major = currentVersionNameMatch.groupValues[1]
            val minor = currentVersionNameMatch.groupValues[2]
            val patch = currentVersionNameMatch.groupValues[3].toInt()
            val newPatch = patch + 1
            val newVersionName = "$major.$minor.$newPatch"
            content = content.replace("versionName = \"$major.$minor.$patch\"", "versionName = \"$newVersionName\"")
            println("Bumped versionName: $major.$minor.$patch -> $newVersionName")
        }

        buildFile.writeText(content)
    }
}

// ktlint configuration
ktlint {
    version = "1.0.1"
    android = true
    ignoreFailures = false
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.HTML)
        reporter(ReporterType.JSON)
    }
    filter {
        exclude("**/generated/**")
        exclude("**/build/**")
    }
}

// Detekt configuration
detekt {
    // Version of Detekt that will be used
    toolVersion = "1.23.7"

    // Builds the AST in parallel. Rules are always executed in parallel.
    parallel = true

    // Define the detekt configuration files
    config.setFrom(files("$projectDir/../detekt-config.yml"))

    // Adds the baseline file for suppressing known issues
    baseline = file("$projectDir/detekt-baseline.xml")

    // Turns on all the rules
    allRules = false

    // Fail build on any finding
    ignoreFailures = false

    // Android specific setup
    buildUponDefaultConfig = true
}

// Configure detekt tasks
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "11"

    // Exclude build and generated directories
    exclude("**/build/**", "**/generated/**", "**/resources/**")

    // Include all Kotlin and script files
    include("**/*.kt", "**/*.kts")

    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}
