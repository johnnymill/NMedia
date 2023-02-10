package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    published = "",
    liked = false,
    likes = 0,
    shares = 0,
    views = 0,
    videoUrl = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: PostRepository = PostRepositoryInMemoryImpl()
//    private val repository: PostRepository = PostRepositorySharedPrefsImpl(application)
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    private val edited = MutableLiveData(empty)
    val data = repository.getAll()

    fun getPost(id: Long) : Post? {
        return data.value?.firstOrNull { it.id == id }
            ?.copy()
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun remove(id: Long) = repository.remove(id)

    fun like(id: Long) = repository.like(id)
    fun share(id: Long) = repository.share(id)
}
