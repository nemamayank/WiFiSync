# 📡 WiFiSync Android App — Jetpack Compose + Clean Architecture

A modern Android app that fetches and syncs user data only on **Wi-Fi**, every hour (with test support for 5 mins), using **WorkManager**, **Room**, and **Jetpack Compose**. Built with a clean, layered architecture that follows best practices and is optimized for performance, reliability, and clarity. 🚀

---

## 🔍 Overview

This project demonstrates how to:
- 🧠 Structure your app using **Clean Architecture**
- 📡 Monitor **Wi-Fi only** conditions via `ConnectivityManager`
- 🔁 Sync background data using **WorkManager + Exponential Backoff**
- 🏛️ Separate concerns using **Repository Pattern**
- 🎨 Build UI with **Jetpack Compose**
- ✅ Handle API + local DB sync logic
- 🔄 Gracefully degrade to local data when offline or API fails

---

## 🧾 Folder Structure (Clean Architecture)
```bash

.
├── data                            # Handles all data sources (local DB, remote APIs, workers)
│   ├── infrastructure              # Workers, broadcast receivers, and background components
│   │   ├── FetchUserWorker.kt
│   │   ├── ScheduleUserWorker.kt
│   │   └── WifiStateReceiver.kt
│   ├── local                       # Room database setup and entities
│   │   ├── AppDatabase.kt
│   │   ├── User.kt
│   │   └── UserDao.kt
│   ├── remote                      # Network layer (Retrofit API and models)
│   │   ├── ApiResponse.kt
│   │   ├── ApiService.kt
│   │   ├── UserDto.kt
│   │   └── UserMapper.kt
│   └── repository                  # Repository implementation
│       └── UserRepositoryImpl.kt
├── di                              # Dependency Injection configuration
│   └── AppModule.kt
├── domain                          # Business logic layer (interfaces and use cases)
│   ├── repository                  # Repository abstractions
│   │   └── UserRepository.kt
│   └── usecase                     # Use cases representing app logic
│       └── GetUsersUseCase.kt
├── presentation                    # UI layer (Jetpack Compose-based)
│   ├── theme                       # Compose theming (colors, typography, etc.)
│   ├── ui                          # Composable screens and navigation
│   │   └── screen
│   │       ├── NavGraph.kt
│   │       ├── Screen.kt
│   │       ├── UserDetailScreen.kt
│   │       └── UserListScreen.kt
│   ├── state                       # UI state/result wrappers
│   │   └── ApiResult.kt
│   ├── viewmodel                   # ViewModel for UI state handling
│   │   └── UserViewModel.kt
│   └── MainActivity.kt
├── utils                            # Utility classes and helpers
│   ├── TimeUtils.kt
│   └── WiFiMonitor.kt
└── MyApplication.kt                # Custom Application class (initial setup)


````

---

## 🔧 Libraries & Tools Used

| Category             | Library / Tool                                                                        |
| -------------------- | ------------------------------------------------------------------------------------- |
| UI                   | [Jetpack Compose](https://developer.android.com/jetpack/compose)                      |
| DB                   | [Room](https://developer.android.com/training/data-storage/room)                      |
| Background Work      | [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) |
| Dependency Injection | [Koin](https://insert-koin.io/)                                                       |
| Networking           | [Retrofit](https://square.github.io/retrofit/)                                        |
| Image Loading        | [Coil](https://coil-kt.github.io/coil/)                                               |
| Logging              | Android `Log`                                                                         |
| Testing              | JUnit, Mockito (for `WiFiMonitorTest`)                                                |

---

## 📲 Features

* ✅ Automatic user data fetch on Wi-Fi only
* ✅ Periodic syncing every 1 hour (test: 5 min)
* ✅ Uses `BroadcastReceiver` to detect real-time Wi-Fi changes
* ✅ Local fallback if API or network fails
* ✅ Clean and testable architecture
* ✅ Circular profile pictures (via `AsyncImage`)
* ✅ Composable and responsive UI

---

## 🚀 Setup Instructions

1. Clone the repo:

```bash
git clone https://github.com/your-username/wifisync-app.git
```

2. Open in **Android Studio**

3. Run on device or emulator (⚠️ Needs active **Wi-Fi** for sync)

4. For testing WorkManager:

   * You may change periodic interval to `15 minutes` (minimum in release)
   * Or use test override to `5 minutes`

---

## 🔑 Highlights

* 📶 `WiFiMonitor` — checks for transport + internet capability
* 🔁 `UserSyncWorker` — retries on failure with exponential backoff
* 💾 `UserRepositoryImpl` — handles logic for stale data, API fallback, and local cache
* 🎯 `ViewModel` — minimal, forwards logic to domain/repo
* 🌐 `WifiStateReceiver` — listens for Wi-Fi transitions and triggers service
  
\#Android #JetpackCompose #CleanArchitecture #RoomDB #WorkManager #Koin #Retrofit #WiFiSync #AndroidDev #MVVM #ComposeUI #Kotlin
