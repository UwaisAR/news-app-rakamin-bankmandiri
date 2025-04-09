
# 📰 News App

**News App** is a news application that displays the latest news from around the world. Built using Android Studio with Kotlin, it integrates with **NewsAPI.org** to fetch up-to-date news content. The app features automatic **AI summarization** powered by **Gemini AI**, allowing users to quickly grasp the essence of news articles.

---

## 🎯 Project Objective

This project is part of the final task for the **Project-Based Virtual Internship Program** hosted by **Rakamin Academy** in collaboration with **Bank Mandiri**.

---

## 🌟 Key Features

- 🔥 Display **Breaking News** and **Latest News** from trusted sources
- 🔎 **Search News** by keyword
- 📌 **Save Favorite Articles**
- 🧠 **AI-generated Summaries** for long articles
- 📷 **Modern UI** with dynamic images and color palettes
- 🔁 **Pull-to-refresh** and smooth loading animations (Shimmer & Lottie)

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
   git clone https://github.com/username/news-app-mandiri-final.git
   ```

2. **Open the project in Android Studio**

3. **Create a `local.properties` file** in the root directory (if it doesn't exist), and set your SDK path:
   ```properties
   sdk.dir=/Users/your-username/Library/Android/sdk
   ```

4. **Create an `apikey.properties` file** with your API keys from [NewsAPI.org](https://newsapi.org):

   ```properties
   NEWS_API_KEY=your_news_api_key_here
   GEMINI_API_KEY=your_gemini_api_key_here
   ```

5. **Build and run the project** as usual using Android Studio.

---

## 📸 Screenshot

![News App Screenshot](image.png)

---

## 👤 Author

> Uwais Alqarani Rachmawan  
> Final Task – Rakamin x Bank Mandiri  
> Mobile App Developer Track  

---

## 📌 Notes

- Make sure you are connected to the internet when running the application.
- It is recommended not to push your API keys to the repository. Use `apikey.properties` and configure it via `build.gradle`.

---

## 📄 License

This project is for educational and non-commercial purposes. All APIs and tools used are subject to their respective licenses.


---

## 🛠️ API Key Notes

If the provided API key on GitHub exceeds its usage limit or stops working, you can replace it by updating your personal key in the `local.properties` file:

```properties
NEWS_API_KEY=f5bb08e6620f4aa3a45eef531a06b771
GEMINI_API_KEY=AIzaSyAOoPu-JwcxrUkoZfbgebxFR3FjApIk5uY
LOGODEV_API_KEY=pk_ZyrzE-v_SUeNQguveS671w
```

After updating the key, make sure to **Clean Project** and then **Rebuild** it from Android Studio to apply the changes.
