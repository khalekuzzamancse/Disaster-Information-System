
# Table of Contents

1. [Disaster Information System](#disaster-information-system)
2. [Features](#features)
3. [User Manual](#user-manual)
4. [Tools and Technologies](#tools-and-technologies)
    - 4.1 [Programming Languages](#programming-languages)
    - 4.2 [Frameworks](#frameworks)
    - 4.3 [Libraries](#libraries)
5. [SRS Documentations](#srs-documentations)
6. [License](#license)



  <summary><strong>Disaster Information System</strong></summary>

![Disaster Information System](docs/images/nia-splash.jpg "Disaster Information System")

  <a href="https://play.google.com/store/apps/details?id=com.google.samples.apps.nowinandroid">
    <img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" height="70">
  </a>

# Disaster Information System
This is the repository for the [Disaster Information System](https://developer.android.com/series/now-in-android) app. It is a **work in progress** 🚧.

**Disaster Information System** is a fully functional Android app built entirely with Kotlin and Jetpack Compose. It follows Android design and development best practices.

The app is currently in development. The `prodRelease` variant is [available on the Play Store](https://play.google.com/store/apps/details?id=com.google.samples.apps.nowinandroid).

<!-- New section -->
<!-- New section -->

<details open> <summary><strong>Features</strong></summary>

- Media (Image, Video) from Gallery
- Video Compression
- Send Media to Server for data collection and AI Analysis
- Fill the Disaster Information Form and Submit

</details>


<!-- New section -->
<!-- New section -->

<details open> <summary><strong>User Manual</strong></summary>

- Video
- Documentation

 </details>



<!-- New section -->
<!-- New section -->

<details open> <summary><strong>Tools and Technologies</strong></summary>

<details> <summary><strong>Programming Languages</strong></summary>

- **Kotlin**:
    - Used for development and Gradle build scripts.

</details>

<details> <summary><strong>Frameworks</strong></summary>

- **Android SDK**
- **Google Map SDK**
- **Jetpack Compose and Compose**
- **Kotlin Multiplatform**

</details>

<details> <summary><strong>Libraries</strong></summary>

- **WorkManager**:
    - Utilized for long-running background tasks, such as sending media in the background and media size compression.
- **Accompanist**:
    - Handles permissions management, including location access and notification permissions.
- **Navigation component**:
    - Manages navigation within Jetpack Compose on the Android platform.
- **Ktor Client** and **OKHttp**:
    - Integrated for communication with REST APIs.
- **Material 3 Design system**:
    - Enhances the UI aesthetics.
- **Android Studio** (using Gradle as the Build System)

</details> </details>

<!-- New section -->
<!-- New section -->
<details open>
  <summary><strong>SRS Documentations</strong></summary>

- [Chapter 01 - Introduction]()
- [Chapter 02 - Software Requirement Specification]()
- [Chapter 03 - System Design]()
- [Chapter 04 - Implementation Details]()
- [Chapter 05 - Testing]()
- [Chapter 06 - User Manual]()
</details>
<!-- New section -->
<!-- New section -->

**Disaster Information System** is distributed under the terms of the Apache License (Version 2.0). See the [license](LICENSE) for more information.
