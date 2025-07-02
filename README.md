# 📡 WiFiSync Android App — Jetpack Compose + Clean Architecture

A modern Android app that fetches and syncs user data only on **Wi-Fi**, every hour (with test support for 5 mins), using **WorkManager**, **Room**, and **Jetpack Compose**. Built with a clean, layered architecture that follows best practices and is optimized for performance, reliability, and clarity. 🚀

---

## 🔍 Overview

This project is a complete demonstration of how to design a **resilient, modern Android app** that handles both online and offline data states with elegance. It acts as a **single source of truth** for data, following robust design patterns with **clean architecture**.

### ✨ Key Highlights

* 🧠 **Single Source of Truth:** Combines local Room DB and remote API to ensure consistent and reliable data access.
* 🌐 **Fetch While Online with Retry:** Automatically fetches fresh data from the server when connected to Wi-Fi, with a built-in **retry mechanism** for API failures.
* 📶 **Offline-First Support:** Seamlessly reads from the local database when offline or during network/API issues.
* ⏳ **Stale Data Detection:** Checks data freshness and fetches updated data only when needed—saving bandwidth and ensuring responsiveness.
* 🔁 **Background Syncing:** Uses **WorkManager** to fetch and store data periodically in the background within defined time limits (1 hour or test 5 mins).
* 🛡️ **Fail-Safe Mechanism:** If the API fails during sync, retries automatically with **exponential backoff**. On success, the latest data is persisted locally.
* ⚙️ **Complete Lifecycle:** A powerful flow that **fetches**, **saves**, **updates**, and **retrieves** user data—on-demand or in the background—reliably and intelligently.


---


## 🧾 Folder Structure (Clean Architecture)
```bash
.
├── data                            # Handles all data sources (local DB, remote APIs, workers)
│   ├── infrastructure              # Workers, broadcast receivers, background components
│   ├── local                       
│   ├── remote                      # Network layer (Retrofit API and models)
│   └── repository                  # Repository implementation
├── di                              # Dependency Injection configuration
├── domain                          # Business logic layer (interfaces and use cases)
│   ├── repository                  # Repository abstractions
│   └── usecase                     
├── presentation                    # UI layer (Jetpack Compose-based)
│   ├── theme                       
│   ├── ui                          # Composable screens and navigation
│   │   └── screen                  
│   ├── state                       # UI state/result wrappers
│   ├── viewmodel                   # ViewModel for UI logic
├── utils                           
└── MyApplication.kt                

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
| Concurrency	         | [Coroutines](https://developer.android.com/kotlin/coroutines)                         |
| Testing              | [Mockito](https://github.com/mockito/mockito-kotlin)                                  |


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
