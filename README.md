# Knowledge Base

An Android project designed as a training ground for modern development practices, focusing on **Static Code Analysis** and **CI/CD** pipelines.

## 🚀 Key Features

### 1. Static Code Analysis
The project integrates two powerful tools to maintain high code quality and consistency:
*   **[ktlint](https://github.com/pinterest/ktlint):** An anti-bikeshedding Kotlin linter with built-in formatter.
    *   Run check: `./gradlew ktlintCheck`
    *   Auto-format: `./gradlew ktlintFormat`
*   **[Detekt](https://github.com/detekt/detekt):** A static code analysis tool for the Kotlin programming language.
    *   Run check: `./gradlew detekt`
    *   Configured with custom rule sets (formatting, libraries, and compose rules).

### 2. CI/CD with GitHub Actions
The project includes a robust `.github/workflows/android-ci.yml` pipeline that:
*   Triggers on every `push` and `pull_request` to the `main` branch.
*   **Automated Testing:** Ensures the project builds successfully.
*   **Signed Release Builds:** Automatically generates and signs a production APK when code is merged into `main`, using secure GitHub Secrets for the Keystore.
*   **Artifact Management:** Uploads build outputs (APKs) as workflow artifacts.

### 3. Build Automation
*   **Custom Versioning:** Includes a custom Gradle task to automate version bumps.
    *   Run: `./gradlew bumpVersion`
*   **Helper Scripts:** Bash scripts for managing local device deployments:
    *   `./uninstall_all_variants.sh`: Removes all project variants from the device.
    *   `./install_all_variants.sh`: Builds and installs all variants (Debug, Release, DebugMinified).
    *   `./clean_reinstall_all.sh`: Performs a full clean, build, and reinstall cycle.
*   **Secure Secret Management:** Uses `local.properties` for local development and Environment Variables for CI to keep sensitive data like API keys and signing credentials out of source control.

## 🛠 Tech Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose
*   **Build System:** Gradle (Kotlin DSL)
*   **Dependency Management:** Version Catalogs (libs.versions.toml)
*   **Backend:** Firebase (Auth, Analytics)

## 📖 Getting Started
1.  Clone the repository.
2.  Create a `local.properties` file in the root directory.
3.  Add the required secrets (refer to `app/build.gradle.kts` for mandatory fields like `DEMO_API_KEY`).
4.  Add your `google-services.json` to the `app/` directory.

```properties
# Example local.properties
sdk.dir=C\:\\Users\\user\\AppData\\Local\\Android\\Sdk
DEMO_API_KEY=your_api_key_here

# RELEASE-ONLY (Signing credentials)
RELEASE_SIGNING_STORE_FILE=C\:\\Users\\user\\Documents\\Keystore\\KnowledgeBase.jks
RELEASE_SIGNING_STORE_PASSWORD=your_password
RELEASE_SIGNING_KEY_ALIAS=key0
RELEASE_SIGNING_KEY_PASSWORD=your_password
```
