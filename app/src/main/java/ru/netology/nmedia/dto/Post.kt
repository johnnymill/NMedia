package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val liked: Boolean,
    val likes: Long,
    val shares: Long,
    val views: Long,
    val videoUrl: String?
)
