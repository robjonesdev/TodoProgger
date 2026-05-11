# TodoProgger

TodoProgger is a feature-rich Kotlin Multiplatform (KMP) To-Do application designed to help you track progress on your tasks with detailed entries, categorization, and scheduled reminders. It uses Compose Multiplatform to provide a seamless and consistent user experience on both Android and iOS.

## Features

- **Task Management**: Create, edit, and organize your tasks effortlessly.
- **Progress Tracking**: Add multiple timestamped progress updates to each task to keep a detailed history of your work.
- **Categorization**: Group your tasks into custom categories (e.g., General, Work, Personal) with dedicated tabs.
- **Reminders**: Schedule local notifications for tasks to ensure you never miss a deadline.
- **Dynamic Theming**: Choose from multiple color themes that adapt to system dark mode and persist across app restarts.
- **Persistent Storage**: Uses **Room** for robust task and category data management and **Jetpack DataStore** for application settings.

## Project Structure

This project follows the standard Kotlin Multiplatform structure:

*   **[:composeApp](./composeApp)**: Contains the shared business logic, data layer, and Compose UI.
    *   **[commonMain](./composeApp/src/commonMain/kotlin)**: Core logic, ViewModels, screens, and database definitions.
    *   **[androidMain](./composeApp/src/androidMain/kotlin)**: Android-specific implementations such as the database builder and reminder scheduler.
    *   **[iosMain](./composeApp/src/iosMain/kotlin)**: iOS-specific implementations.
*   **[:iosApp](./iosApp)**: The native iOS project that hosts the shared Compose application.

## Getting Started

### Prerequisites

- **Android Studio** (Koala or newer recommended)
- **Xcode** (For building and running the iOS application)
- **JDK 17 or higher**

### Build and Run

#### Android Application
You can run the app directly from Android Studio or via the command line:
- **Windows**: `.\gradlew.bat :composeApp:assembleDebug`
- **macOS/Linux**: `./gradlew :composeApp:assembleDebug`

#### iOS Application
1. Open the `iosApp` directory in Xcode.
2. Select a simulator or a connected iOS device.
3. Click **Run** (or `Cmd + R`).

Alternatively, use the **Kotlin Multiplatform Mobile (KMM)** plugin in Android Studio to launch the iOS app directly from the IDE.

## Technologies Used

- **Compose Multiplatform**: UI framework for shared code.
- **Room Database**: Local data persistence with multiplatform support.
- **DataStore (Preferences)**: Key-value storage for app settings and theme persistence.
- **Kotlinx Coroutines & Flow**: For reactive state management.
- **Navigation Compose**: Type-safe navigation within the shared code.
- **Kotlinx Datetime**: Multiplatform date and time handling.
- **Material 3**: Modern UI components and theming.

---