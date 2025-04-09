
# 📰 News App

**News App** is a news application that displays the latest news from around the world. Built using Android Studio with Kotlin, it integrates with **NewsAPI.org** to fetch up-to-date news content. The app features automatic **AI summarization** powered by **Gemini AI**, allowing users to quickly grasp the essence of news articles.

---

## 🎯 Project Objective

This project is part of the final task for the **Project-Based Virtual Internship Program** hosted by **Rakamin Academy** in collaboration with **Bank Mandiri**.

---

## 📚 Table of Contents
 
- [🗂️ Project Structure](#️-project-structure)  
- [🌟 Key Features](#-key-features)  
- [⚙️ Technologies & Tools](#️-technologies--tools)  
- [🚀 How to Run the Project](#-how-to-run-the-project)
- [👤 Author](#-author)  
- [📄 License](#-license)  
- [🛠️ API Key Notes](#️-api-key-notes)  

---

## 🗂️ Project Structure

```
com.rakamin.bankmandiri.newsapp
│
├── 📁 data                  # 📦 Data layer: repositories and local data source
│   ├── 📁 local             # 🗃️ Room database, DAO classes
│   └── 📁 repository        # 🔄 Repository classes for fetching and storing data
│
├── 📁 model                 # 📑 Data models (e.g. News, Category, etc.)
│
├── 📁 network               # 🌐 API services (Retrofit interfaces)
│
├── 📁 ui                    # 🎨 Presentation layer (Fragments, Adapters, etc.)
│   ├── 📁 category          # 🗂️ News by category UI
│   ├── 📁 detail            # 📖 Detailed news view
│   ├── 📁 home              # 🏠 Home page, breaking & latest news
│   ├── 📁 organize          # 📌 Organize/saved news section
│   ├── 📁 search            # 🔍 Search functionality
│   ├── 📁 spotlight         # 🌟 Trending/featured news
│   └── 📁 webview           # 🌐 In-app WebView to display full articles
│
├── 📁 utils                 # 🛠️ Utility classes (helpers, extensions, constants)
│
├── 🎬 MainActivity.kt       # 🧭 Main navigation container
└── 🎬 SplashActivity.kt     # 🚀 Splash screen on app start
```

### 📁 Links:
- [`data`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/data)
  - [`local`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/data/local)
  - [`repository`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/data/repository)
- [`model`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/model)
- [`network`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/network)
- [`ui`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui)
  - [`category`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui/category)
  - [`detail`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui/detail)
  - [`home`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui/home)
  - [`organize`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui/organize)
  - [`search`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui/search)
  - [`spotlight`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui/spotlight)
  - [`webview`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/ui/webview)
- [`utils`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/tree/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/utils)
- [`MainActivity.kt`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/blob/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/MainActivity.kt)
- [`SplashActivity.kt`](https://github.com/UwaisAR/news-app-rakamin-bankmandiri/blob/main/app/src/main/java/com/rakamin/bankmandiri/newsapp/SplashActivity.kt)
 
---

## 🌟 Key Features

- 🔥 Display **Breaking News** and **Latest News** from trusted sources
- 📂 **Category Menu** for easy navigation through different news topics
- 🌟 **Spotlight Menu** highlighting featured articles and updates
- 🔎 **Search News** by keyword for quick access to specific topics also using filter for accurate search
- 📌 **Save Favorite Articles** for later reading
- 🧠 **AI-generated Summaries** for long articles, enhancing readability
- 📷 **Modern UI** with dynamic images, color palettes, and automatic theme adaptation (light/dark mode) based on your device settings for an engaging experience.
- 🔁 **Pull-to-refresh** and smooth loading animations (Shimmer & Lottie) for seamless browsing
- 📖 **Detail Page** for in-depth article views after clicking on news items
- 🤖 **AI Summary Tab** in detail pages for quick insights and highlights
- 🌐 **Webview Integration** enhances user understanding by providing AI-generated summaries of news content available within the webview.

---

## ⚙️ Technologies & Tools

- **Kotlin** + **Android Studio**
- **MVVM Architecture**
- **Retrofit + Gson** – Fetch data from NewsAPI
- **Gemini AI (Generative AI)** – AI-powered summarization
- **OpenNLP** – Keyword extraction for spotlight recommendation menu
- **Room Database** – Store saved articles
- **Coroutines** – Asynchronous processing
- **Glide** – Load images
- **RecyclerView + ViewPager2** – Dynamic UI
- **Lottie + Shimmer** – Splashscreen & Loading animations
- **SwipeRefreshLayout** – Refresh content
- **Navigation Component** – Fragment navigation
- **View Binding** – Bind UI elements to Kotlin code

---

## 🚀 How to Run the Project

1. **Clone this repository**:
   ```bash
   git clone https://github.com/UwaisAR/news-app-mandiri-final.git
   ```

2. **Open the project in Android Studio**

3. **Build and run the project** as usual using Android Studio.

---


## 👤 Author

> Uwais Alqarani Rachmawan  
> Final Task – Rakamin x Bank Mandiri

---


## 📄 License

This project is for educational and non-commercial purposes. All APIs and tools used are subject to their respective licenses.


---

## 🛠️ API Key Notes

If the provided API key on GitHub exceeds its usage limit or stops working, you can replace it by updating your personal key in the `local.properties` file:

```properties
NEWS_API_KEY=your_new_api_key_here
GEMINI_API_KEY=your_new_api_key_here
LOGODEV_API_KEY=your_new_api_key_here
```

After updating the key, make sure to **Clean Project** and then **Rebuild** it from Android Studio to apply the changes.
