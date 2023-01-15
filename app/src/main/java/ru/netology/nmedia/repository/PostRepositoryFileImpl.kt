package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFileImpl(
    private val context: Context
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                PostInternal.posts = gson.fromJson(it, type)
                PostInternal.data.value = PostInternal.posts
            }
        } else {
            // если файла нет, создаем пустой
            sync()
        }
        PostInternal.actualizeNextId()
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(PostInternal.posts))
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
