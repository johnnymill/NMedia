package ru.netology.nmedia.repository

import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostInternal() {

    init {
        posts = dao.getAll()
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
    }

    override fun remove(id: Long) {
        dao.remove(id)
        super.remove(id)
    }

    override fun like(id: Long) {
        dao.like(id)
        super.like(id)
    }

    override fun share(id: Long) {
        dao.share(id)
        super.share(id)
    }

    override fun sync() {}
}
