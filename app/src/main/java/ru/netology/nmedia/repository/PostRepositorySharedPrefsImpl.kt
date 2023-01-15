package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"

    init {
        prefs.getString(key, null)?.let {
            PostInternal.posts = gson.fromJson(it, type)
            PostInternal.data.value = PostInternal.posts
        }
        PostInternal.actualizeNextId()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(PostInternal.posts))
            apply()
        }
    }

    override fun getAll(): LiveData<List<Post>> = PostInternal.data

    override fun save(post: Post) {
        PostInternal.update(post)
        sync()
    }

    override fun remove(id: Long) {
        PostInternal.remove(id)
        sync()
    }

    override fun like(id: Long) {
        PostInternal.like(id)
        sync()
    }

    override fun share(id: Long) {
        PostInternal.share(id)
        sync()
    }
}
