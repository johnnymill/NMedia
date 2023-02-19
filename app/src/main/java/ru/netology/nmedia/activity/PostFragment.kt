package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.helpers.HelperNumbers
import ru.netology.nmedia.helpers.HelperStringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by HelperStringArg
    }

    private var _binding: FragmentPostBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding: FragmentPostBinding
        get() = _binding!!

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private fun playVideo(post: Post) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
        if (intent.resolveActivity(requireActivity().packageManager) == null) {
            Snackbar.make(
                binding.root, getString(R.string.error_no_activity_to_open_media),
                BaseTransientBottomBar.LENGTH_SHORT
            ).show()
            return
        }
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val post = arguments?.textArg?.toLong()?.let { viewModel.getPost(it) }
            ?: throw NullPointerException("Oops! Post is not found")

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            with (posts.first { it.id == post.id }) {
                binding.post.content.text = content
                binding.post.liked.text = HelperNumbers.compactNumber(likes)
                binding.post.shared.text = HelperNumbers.compactNumber(shares)
            }
        }

        binding.post.apply {
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
                                viewModel.edit(post)
                                findNavController().navigate(
                                    R.id.action_postFragment_to_editPostFragment,
                                    Bundle().apply {
                                        textArg = post.content
                                    }
                                )
                                true
                            }
                            R.id.remove -> {
                                viewModel.data.removeObservers(viewLifecycleOwner)
                                viewModel.remove(post.id)
                                findNavController().navigateUp()
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            liked.setOnClickListener {
                viewModel.like(post.id)
            }

            shared.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.share(post.id)
            }

            if (post.videoUrl.isNullOrBlank()) {
                groupVideo.visibility = View.GONE
            } else {
                groupVideo.visibility = View.VISIBLE
                videoPreview.setOnClickListener {
                    playVideo(post)
                }
                fabVideoPlayback.setOnClickListener {
                    playVideo(post)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
