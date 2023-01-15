package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

internal object PostInternal {
    private var nextId = 1L
    internal var posts = emptyList<Post>()
    internal val data = MutableLiveData(posts)

    internal fun actualizeNextId() {
        // Может быть его тоже хранить в каком-нибудь файле настроек?

//        var maxId = 0L
//        posts.forEach {
//            if (it.id > maxId)
//                maxId = it.id
//        }
//        nextId = maxId + 1

        nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
    }

    internal fun add(post: Post) {
        // TODO: remove hardcoded author & published
        posts = listOf(
            post.copy(
                id = nextId++,
                author = post.author.ifBlank { "Me" },
                published = post.published.ifBlank { "now" },
            )
        ) + posts
        data.value = posts
    }

    internal fun update(post: Post) {
        if (post.id == 0L) {
            add(post)
        } else {
            posts = posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
            data.value = posts
        }
    }

    internal fun remove(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    internal fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                liked = !it.liked,
                likes = it.likes + if (!it.liked) 1 else -1
            )
        }
        data.value = posts
    }

    internal fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }
}
