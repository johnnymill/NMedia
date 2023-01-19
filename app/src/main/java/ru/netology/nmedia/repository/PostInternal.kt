package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

abstract class PostInternal : PostRepository {
    private var nextId = 1L
    private val data = MutableLiveData<List<Post>>()
    protected var posts = emptyList<Post>()
        set(value) {
            field = value
            data.value = field
        }

    fun actualizeNextId() {
        // Может быть его тоже хранить в каком-нибудь файле настроек?
        nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun add(post: Post) {
        // TODO: remove hardcoded author & published
        posts = listOf(
            post.copy(
                id = nextId++,
                author = post.author.ifBlank { "Me" },
                published = post.published.ifBlank { "now" },
            )
        ) + posts
    }

    abstract fun sync()

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            add(post)
        } else {
            posts = posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
        sync()
    }

    override fun remove(id: Long) {
        posts = posts.filter { it.id != id }
        sync()
    }

    override fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                liked = !it.liked,
                likes = it.likes + if (!it.liked) 1 else -1
            )
        }
        sync()
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        sync()
    }
}
