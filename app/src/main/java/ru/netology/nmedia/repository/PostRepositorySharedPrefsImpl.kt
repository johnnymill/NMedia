package ru.netology.nmedia.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositorySharedPrefsImpl(context: Context) : PostInternal() {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"

    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, type)
        }
        actualizeNextId()
    }

    override fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}
