# Panduan Kontribusi

Terima kasih sudah tertarik berkontribusi! 🎉

## Setup Development

1. Fork dan clone repo ini
2. Ikuti langkah setup di [README.md](README.md)
3. Buat branch baru dari `main`

## Konvensi Commit

Gunakan format berikut:

```
Add: menambah fitur baru
Fix: memperbaiki bug
Update: mengupdate sesuatu yang sudah ada
Remove: menghapus sesuatu
Docs: perubahan dokumentasi
Refactor: refactor kode tanpa mengubah behavior
```

## Pull Request

- Pastikan build tidak error: `./gradlew assembleDebug`
- Pastikan tests lulus: `./gradlew testDebugUnitTest`
- Deskripsikan perubahan dengan jelas di PR
