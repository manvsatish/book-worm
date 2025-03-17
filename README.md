# Bookworm
Manvika Satish, Priya Inampudi

## Table of Contents
- [Project Description](#project-description)
- [Figma Design](#figma-design)
- [Features](#features)
  - [Android Features](#android-features)
  - [Jetpack Compose Features](#jetpack-compose-features)
  - [Third-Party Libraries](#third-party-libraries)
- [Dependencies](#dependencies)
- [Additional Notes](#additional-notes)

## Project Description
Bookworm is an Android application that allows users to track and review their favorite books. It uses Jetpack Compose for the UI and supports adding, editing, and viewing book details including ratings, reviews, and reading progress.

## Figma Design
You can view the design for Bookworm on Figma:  
[Bookworm Wireframes](https://www.figma.com/design/mpqSZBKMarQbp1OcvlfpTw/Bookworm-Wireframes?node-id=6-17&t=qgge5otFgmEJNMk1-1)

## Features

### Android Features
- Navigation Component for seamless screen transitions.
- Local file storage for persisting book data.
- Integration with Android lifecycle components.

### Jetpack Compose Features
- Compose Scaffold, LazyVerticalGrid, and Material3 components for building a responsive UI.
- State management with `remember` and `mutableStateOf` for dynamic content updates.
- Custom theming with dark/light modes via MaterialTheme.

### Third-Party Libraries
- [Coil](https://coil-kt.github.io/coil/) for asynchronous image loading.
- [Gson](https://github.com/google/gson) for JSON serialization and deserialization.

## Dependencies
- **Minimum SDK:** 21 (Android 5.0 Lollipop)
- **Target SDK:** 33
- Requires a device with internet connectivity to load images from remote URLs.

## Additional Notes
- The project includes features that went Above and Beyond, such as smooth transitions, custom theming with a ThemeViewModel, and persistence of user-added books using a local JSON file.
- The code demonstrates how to integrate Jetpack Compose with traditional Android storage and navigation components.

