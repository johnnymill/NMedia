package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.helpers.HelperNumbers

interface OnInteractionListener {
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onPlay(post: Post) {}
    fun onView(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            liked.isChecked = post.liked
            liked.text = HelperNumbers.compactNumber(post.likes)
            shared.text = HelperNumbers.compactNumber(post.shares)
            views.text = HelperNumbers.compactNumber(post.views)

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            liked.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            shared.setOnClickListener {
                onInteractionListener.onShare(post)
            }

            if (post.videoUrl.isNullOrBlank()) {
                groupVideo.visibility = View.GONE
            } else {
                groupVideo.visibility = View.VISIBLE
                videoPreview.setOnClickListener {
                    onInteractionListener.onPlay(post)
                }
                fabVideoPlayback.setOnClickListener {
                    onInteractionListener.onPlay(post)
                }
            }

            avatar.setOnClickListener {
                onInteractionListener.onView(post)
            }

            author.setOnClickListener {
                onInteractionListener.onView(post)
            }

            published.setOnClickListener {
                onInteractionListener.onView(post)
            }

            content.setOnClickListener {
                onInteractionListener.onView(post)
            }
        }
    }
}
