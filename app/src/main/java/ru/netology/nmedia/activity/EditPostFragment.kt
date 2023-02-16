package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.helpers.HelperAndroid
import ru.netology.nmedia.helpers.HelperStringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class EditPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by HelperStringArg
    }

    private var _binding: FragmentEditPostBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding: FragmentEditPostBinding
        get() = _binding!!

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edit.requestFocus()

        arguments?.textArg?.let(binding.edit::setText)

        binding.save.setOnClickListener {
            if (binding.edit.text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_content, BaseTransientBottomBar.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.changeContent(binding.edit.text.toString())
                viewModel.save()
                HelperAndroid.hideKeyboard(requireView())
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
