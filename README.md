# WorldCinema — KOD1.4 Variant2

Приложение-кинотеатр для умных часов (Wear OS), повторяющее макет
[KOD1.4 Variant2](https://www.figma.com/file/wwDCPGa4AyPXdeuFnKU95M/KOD1.4-Variant2?type=design&node-id=111-393).

## Что реализовано

- **Экран загрузки** с логотипом WorldCinema.
- **Авторизация и регистрация** (данные сохраняются в `SharedPreferences`, пароль хэшируется SHA-256).
- **Главное меню** — 2×2: Подборки, Обсуждения, Избранное, Профиль, плюс быстрый поиск.
- **Подборки** — Marvel, DC, Star Wars, Комедии, Ужасы.
- **Список фильмов из OMDB API** (`https://www.omdbapi.com`) с поиском по названию.
- **Карточка фильма**: постер, год, длительность, рейтинг IMDb, жанр, описание, режиссёр, актёры.
- **Избранное** — добавление/удаление звёздочкой, отдельный экран, сохранение между запусками.
- **Обсуждения** — список чатов и переписка с отправкой сообщений.
- **Профиль** — имя, e-mail, статистика, версия приложения, выход из аккаунта.

## Picasso

Постеры фильмов загружаются по URL библиотекой **Picasso**:

```kotlin
Picasso.get()
    .load(url)
    .placeholder(R.drawable.placeholder_poster)
    .error(R.drawable.placeholder_poster)
    .fit()
    .centerCrop()
    .into(imageView)
```

Реализация находится в `app/src/main/java/com/dignerdranch/anroid/prakt_6/util/PosterLoader.kt`.

## OMDB API

Ключ задаётся в `data/OmdbClient.kt` (`API_KEY`). Используются методы:

- поиск: `?apikey=...&s=...&type=movie&page=1`;
- карточка фильма: `?apikey=...&i=imdbID&plot=full`.

Если демонстрационный ключ перестанет работать, замените его на свой
с [omdbapi.com/apikey.aspx](https://www.omdbapi.com/apikey.aspx).

## Сборка и запуск

```powershell
$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"
.\gradlew.bat :app:assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`.

Демо-вход: любой корректный e-mail и пароль минимум из 4 символов.

## Технологии

- Kotlin, AndroidX AppCompat, Material 3
- `androidx.wear.widget.BoxInsetLayout` — интерфейс не обрезается на круглом экране часов
- RecyclerView, Retrofit + Gson, Picasso
- `compileSdk 34`, `minSdk 26`
