package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map { it.toDto() }
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun remove(id: Long) {
        dao.remove(id)
    }

    override fun like(id: Long) {
        dao.like(id)
    }

    override fun share(id: Long) {
        dao.share(id)
    }
}
