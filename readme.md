
<!--
The order of the content is important.
After Feature the User Manual is placed.
Put less important thing(for user) at the end.

The abbreviation SRS might not be immediately clear to all readers. Consider renaming this to Software Requirements Specification (SRS) for clarity

- Use capital letter among all the heading
- No dot(.) on bullet points text



-->
 <!-- -->
## Table of Contents

- [Introduction](#introduction)
    - [Overview](#overview)
    - [Motivation and Purpose](#motivation-and-purpose)
    - [Project Scope and Assumptions](#project-scope-and-assumptions)

- [Release Versions & Updates]()

- [Features](#features)

- [Tools and Technologies Used](#used-tools-and-technologies)

- [Documentation](#documentation)
    - [Software Requirements Specification (SRS)](#software-requirements-specification-srs)
    - [User Manual](#user-manual)

- [Project Ownership & Contributors](#project-ownership--contributors)


<!--
Section: Introduction 
this section should contain:
What the project does.
why the project is unique.
Why KMP and CMP is used(Technical Description)
Project Scope Clarity:
-explain how this information benefits the user or stakeholders would strengthen this section.

-->
## INTRODUCTION

<!---
Briefly describe what the problem it solve 
-->
<p align=" justify" >

**Disaster Information System** is a  Multiplatform application.It is being developed using Kotlin and Compose Multiplatform technology.
</p>


### Motivation and Purpose
<p align=" justify" >
The existing information about various disasters is not readily available at the user level, and it lacks proper maintenance and analysis. To address this challenge, the Disaster Information System aims to collect information related to recently occurred disasters. This information is presented in various formats, including text, video, and images.
</p>

### Project Scope and Assumptions
<p align=" justify" >
This project is intended for use with a high-speed internet connection and requires devices that support Google Maps, location permissions, and device media access permissions.
</p>

<!-- Sections Release Version -->

## RELEASE VERSIONS &  UPDATES

- **Android** | [APK File]() | [Google Play](https://develop) (will available soon)
- **Windows** | [EXE File]() | [Microsoft Link](https://develop) (will available soon)
- [ **Website Link** ](https://develop) (will available soon)
- [**Apple Store Link**](https://develop) (will available soon)





<!-- Feature Section Starts-->
## FEATURES

<details>
  <summary><strong>Multimedia Reporting</strong></summary>

- Users have the ability to upload photos and videos directly from their device’s gallery to report disaster events

</details>
<details>
  <summary><strong>Smart Data Shrinkage </strong></summary>

- Multimedia files with disproportionately large sizes relative to their duration will be automatically compressed before transmission to the server

</details>


<details>
  <summary><strong>Report Form</strong></summary>

- A detailed submission form that collects essential information about the disaster, including the location, a description, the start date and time, and the end date and time of the disaster

</details>


<details>
  <summary><strong>Offline Queueing</strong></summary>

- Enables users to collect and complete report details even when offline.The information will be  stored locally and automatically uploaded once an internet connection becomes available.

</details>


<details>
  <summary><strong>Historical Insights</strong></summary>

- Have the capability to explore historical disaster data through visual graphs. This feature offers valuable insights into the frequency, geographical distribution, and severity of past disasters
</details>




#   <!-- Empty heading for new horizontal line -->

<!-- New section -->
<!-- Section : Tools and Technologies-->

## USED TOOLS AND TECHNOLOGIES

<details> <summary><strong>Programming Languages</strong></summary>

- [**Kotlin**](https://kotlinlang.org/)
    - Used for development
    - For Gradle build scripts

</details>

<details> <summary><strong>Frameworks</strong></summary>

- [**Android SDK**](https://www.android.com/)
    - Used to build native Android applications
- [**Google Map SDK**](https://developers.google.com/maps/documentation/android-sdk/maps-compose)
    - Enables selection of disaster occurrence locations
- [**Jetpack Compose**](https://developer.android.com/jetpack/compose)
    - To  access to Android-specific UI components, including Google Maps, permissions handling, and media picker

- [**Compose Multiplatform**](https://www.jetbrains.com/lp/compose-multiplatform/)
    - To developer common UI  that can be shared across multiple platforms

- [**Kotlin Multiplatform**](https://kotlinlang.org/docs/multiplatform.html)
    - To sharing of business logic across different platforms


</details>

<details> <summary><strong>Libraries</strong></summary>

- [**WorkManager**](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started)
    - Utilized for long-running background tasks, such as sending media in the background and media size compression
- [**Accompanist**](https://github.com/google/accompanist)
    - Handles permissions management, including location access and notification permissions
- [**Navigation component**](https://developer.android.com/jetpack/compose/navigation)
    - Manages navigation within   the Android platform.
- [**Ktor Client** and **OKHttp**](https://ktor.io/docs/getting-started-ktor-client.html)
    - Integrated for communication to server via REST APIs
- [**Material 3 Design system**](https://m3.material.io/)
    - Utilized for predefined UI components and custom theming

</details> </details>





<!-- New section -->
<!-- Section :DOCUMENTATION -->
## DOCUMENTATION

### Software Requirements Specification (SRS)
- [Link](./docs/srs_document.md)
### User Manual
- [Video Preview](https://youtu.be/KBp_7D-v7hw)

</details>
<!-- New section -->
<!-- Ending section-->

## Project Ownership & Contributors
Owner: [**Mahedi Hasan**](https://just.edu.bd/t/mahedihasan), Dept of [CSE]()   , [JASHORE UNIVERSITY OF SCIENCE AND TECHNOLOGY](https://just.edu.bd/)

Server-Side and  Web Platform Developer : [Jatrion Mondol Joy](), Dept of [CSE](), [JASHORE UNIVERSITY OF SCIENCE AND TECHNOLOGY](https://just.edu.bd/)



##
**Disaster Information System** is distributed under the terms of the Apache License (Version 2.0). See the [license](LICENSE) for more information.
