package ru.netology.nmedia.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    private lateinit var gson: Gson
    private var prefs: SharedPreferences? = null
    private val type = TypeToken.getParameterized(String::class.java).type
    private val key = "content"

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

        val textArg = arguments?.textArg
        if (!textArg.isNullOrBlank()) {
            // We are in edit mode
            binding.edit.setText(textArg)
        } else {
            // We are in add mode
            gson = Gson()
            prefs = requireContext().getSharedPreferences("draft", Context.MODE_PRIVATE)

            prefs!!.getString(key, null)?.let {
                binding.edit.text = gson.fromJson(it, type)
            }
            // This callback will only be called when EditPostFragment is at least Started.
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                with(prefs!!.edit()) {
                    putString(key, gson.toJson(binding.edit.text.toString()))
                    apply()
                }
                findNavController().navigateUp()
            }
        }

        binding.save.setOnClickListener {
            if (binding.edit.text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_content, BaseTransientBottomBar.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.changeContent(binding.edit.text.toString())
                viewModel.save()
                if (prefs != null) {
                    // Delete the draft (make it empty)
                    with(prefs!!.edit()) {
                        putString(key, gson.toJson(""))
                        apply()
                    }
                }
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
