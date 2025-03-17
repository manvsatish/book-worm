# Bookworm  
Manvika Satish, Priya Inampudi

## Table of Contents
- [Project Description](#project-description)
- [Design & Prototype](#design--prototype)
- [Features](#features)
  - [Core Android Features](#core-android-features)
  - [Jetpack Compose & UI Features](#jetpack-compose--ui-features)
  - [Journaling & Review Features](#journaling--review-features)
  - [Third-Party Libraries](#third-party-libraries)
- [Dependencies](#dependencies)
- [Project Structure & Code Overview](#project-structure--code-overview)
- [Additional Notes](#additional-notes)

## Project Description
Bookworm is an Android journaling application designed especially for book lovers. It enables you to build a personal library where you can add new books, track your reading progress, and write personal reviews and reflections. Whether you're logging insights about a favorite novel or tracking your journey through a challenging book, Bookworm helps you capture every chapter of your reading experience.

## Design & Prototype
Explore the design and UI prototype for Bookworm on Figma:  
[Bookworm Wireframes](https://www.figma.com/design/mpqSZBKMarQbp1OcvlfpTw/Bookworm-Wireframes?node-id=6-17&t=qgge5otFgmEJNMk1-1)

## Features

### Core Android Features
- **Navigation Component:** Enables smooth transitions between screens such as home, book details, add/edit book, and settings.
- **Local Storage:** Persists book data (including personal journal entries) locally using a JSON-based storage solution.
- **Lifecycle Integration:** Utilizes Android lifecycle components for robust data handling and UI state management.

### Jetpack Compose & UI Features
- **Modern UI with Compose:** Uses Compose Scaffold, LazyVerticalGrid, and Material3 components to create a responsive, visually appealing interface.
- **State Management:** Implements dynamic updates with `remember` and `mutableStateOf` for a fluid journaling experience.
- **Custom Theming:** Supports both dark and light modes via MaterialTheme for personalized UI appearance.
- **Real-Time Updates:** Reflects changes immediately—from journaling inputs to reading progress adjustments.

### Journaling & Review Features
- **Add & Edit Journal Entries:** Easily create or update entries for each book, including title, author, cover image, rating, and personal review.
- **Track Reading Progress:** Monitor your reading journey with progress indicators that update as you log pages read.
- **Reflect & Review:** Record your thoughts, insights, and detailed reflections to deepen your engagement with each book.
- **Comprehensive Book Details:** Access rich content such as author biographies, user reviews, genres, and more.

### Third-Party Libraries
- **Coil:** Efficient, asynchronous image loading for displaying book covers.
- **Gson:** Handles JSON serialization and deserialization to save and load your library data.
- **Additional Dependencies:** Utilizes AndroidX components, DataStore for preferences, and Firebase Crashlytics for monitoring app stability.

## Dependencies
- **Minimum SDK:** 24
- **Compile & Target SDK:** 35
- **Language & UI:** Kotlin with Jetpack Compose and Material3  
- Requires an internet connection for loading remote images.

## Project Structure & Code Overview
- **build.gradle.kts:** Configures plugins, dependencies, and build settings for Kotlin, Compose, and Android.
- **MainActivity.kt:** Entry point of the app that sets up the theme, navigation, and initializes the mutable state list for books.
- **Screens:**
  - **AddBookScreen:** Provides an interface for adding new books along with your personal journal entries.
  - **BookDetailsScreen1 & BookDetailsScreen2:** Offer detailed views of each book—including cover, title, author, ratings, reading progress, and user reviews.
  - **EditBookScreen:** Lets you update book details and refine your journal entries.
  - **SettingsScreen:** Allows customization of display modes (dark/light) and toggles for notifications.
- **Storage:** `BookStorageManager` manages JSON-based file storage to persist your library and journaling data.

## Additional Notes
- **Journaling Focus:** Bookworm goes beyond simple book tracking by emphasizing personal reflection. Every entry you make helps document your unique reading journey.
- **Enhanced User Experience:** The app’s smooth transitions, real-time updates, and intuitive navigation provide an engaging user experience.
- **Extendable Architecture:** The integration of Jetpack Compose with traditional Android components and a modular design makes the app easy to extend and customize for future features.
