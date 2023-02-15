package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun remove(id: Long)
    fun like(id: Long)
    fun share(id: Long)
}
