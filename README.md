# GamePub & SFS Hub

<p align="center">
  <img src="app/src/main/res/drawable/app_icon_fg_1785932605186.jpg" width="120" alt="App Icon"/>
</p>

<p align="center">
  Platform publikasi game & APK multi-bahasa, pengubah tautan HTML/HTTP/HTTPS, dan pusat berbagi blueprint Space Flight Simulator.
</p>

---

## ✨ Fitur

- 🎮 **Game Hub** — Publikasikan game & APK dengan informasi multi-bahasa
- 🔗 **Link Converter** — Ubah tautan HTML/HTTP/HTTPS
- 🚀 **SFS Blueprint** — Bagikan dan unduh blueprint Space Flight Simulator
- 📖 **Dev Guides** — Panduan pengembangan dalam aplikasi
- 🤖 **Firebase AI** — Terintegrasi dengan Gemini via Firebase AI

## 🛠️ Tech Stack

| Komponen | Teknologi |
|----------|-----------|
| Bahasa | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Database | Room |
| Network | Retrofit + OkHttp + Moshi |
| AI | Firebase AI (Gemini) |
| DI (implicit) | ViewModel + Repository Pattern |
| Build | Gradle (KTS) |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

## 🚀 Setup & Instalasi

### 1. Clone repositori

```bash
git clone https://github.com/USERNAME/Gamepub-SFS-Blueprint.git
cd Gamepub-SFS-Blueprint
```

### 2. Konfigurasi API Key

Salin file contoh environment:

```bash
cp .env.example .env
```

Edit `.env` dan isi dengan API key kamu:

```env
GEMINI_API_KEY=your_actual_gemini_api_key_here
```

> ⚠️ **PENTING:** Jangan pernah commit file `.env` ke Git. File ini sudah masuk `.gitignore`.

### 3. Firebase Setup

1. Buat project di [Firebase Console](https://console.firebase.google.com/)
2. Tambahkan Android app dengan package `com.aistudio.gamepublish.sfshub`
3. Download `google-services.json` dan letakkan di folder `app/`
4. Aktifkan **Firebase AI** (Gemini API) di console

> 💡 `google-services.json` sudah masuk `.gitignore`. Setiap developer harus mengonfigurasi sendiri.

### 4. Signing Config (untuk Release Build)

Buat keystore:

```bash
keytool -genkey -v -keystore my-upload-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias upload
```

Set environment variable atau tambahkan di `local.properties` (**jangan commit**):

```
KEYSTORE_PATH=/path/to/my-upload-key.jks
STORE_PASSWORD=your_store_password
KEY_PASSWORD=your_key_password
```

### 5. Build & Run

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

Atau buka di **Android Studio** dan jalankan langsung.

## 📁 Struktur Proyek

```
app/src/main/java/com/example/
├── MainActivity.kt
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   ├── GameDao.kt
│   │   └── GameEntity.kt
│   └── repository/
│       └── GameRepository.kt
├── ui/
│   ├── components/
│   │   ├── GameCard.kt
│   │   ├── GameDetailModal.kt
│   │   ├── PublishGameDialog.kt
│   │   ├── SfsBlueprintCard.kt
│   │   ├── SfsDetailModal.kt
│   │   ├── TopHeaderBar.kt
│   │   └── UploadBlueprintDialog.kt
│   ├── screens/
│   │   ├── DevGuidesScreen.kt
│   │   ├── GameHubScreen.kt
│   │   ├── LinkConverterScreen.kt
│   │   └── SfsBlueprintScreen.kt
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodel/
│       └── MainViewModel.kt
└── util/
    └── LinkConverter.kt
```

## 🔄 GitHub Actions (CI/CD)

Proyek ini sudah dilengkapi workflow CI otomatis. Setiap push ke branch `main` akan:

1. ✅ Build debug APK
2. ✅ Jalankan unit tests

Untuk setup GitHub Actions, tambahkan secret berikut di repository settings:

| Secret | Keterangan |
|--------|------------|
| `GEMINI_API_KEY` | API key Gemini |

## 🤝 Kontribusi

1. Fork repositori ini
2. Buat branch fitur: `git checkout -b feature/nama-fitur`
3. Commit perubahan: `git commit -m 'Add: nama fitur'`
4. Push ke branch: `git push origin feature/nama-fitur`
5. Buat Pull Request

## 📄 Lisensi

```
Copyright (c) 2026 GamePub & SFS Hub

Licensed under the MIT License
```

---

<p align="center">Made with ❤️ using Jetpack Compose & Firebase AI</p>
