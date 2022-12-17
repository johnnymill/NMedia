package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var liked: Boolean,
    var likes: Long,
    var shares: Long,
    var views: Long
)
