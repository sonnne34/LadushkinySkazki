package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.*
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.textfield.TextInputLayout
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentInteractiveAddBinding
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.AddInteractiveModel


class InteractiveAddFragment : Fragment() {

    private var _binding: FragmentInteractiveAddBinding? = null
    private val binding: FragmentInteractiveAddBinding
        get() = _binding ?: throw RuntimeException("FragmentInteractiveAddBinding == null")

    private val viewModelFactory by lazy {
        InteractiveAddViewModelFactory(requireActivity().application)
    }

    private val viewModel: InteractiveAddViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[InteractiveAddViewModel::class.java]
    }

    private lateinit var linearLayout: LinearLayout
    private lateinit var imageView: ImageView
    private lateinit var imageProgressBar: ProgressBar
    private lateinit var textProgressBar: TextView
    private lateinit var edtName: EditText
    private lateinit var edtYear: EditText
    private lateinit var edtComment: EditText
    private lateinit var inputEdtName: TextInputLayout
    private lateinit var inputEdtYear: TextInputLayout

    private var filePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInteractiveAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            linearLayout = llInteractiveAdd
            edtName = edtNameInteractiveAdd
            edtYear = edtYearInteractiveAdd
            edtComment = edtCommentInteractiveAdd
            inputEdtName = inputEdtNameInteractiveAdd
            inputEdtYear = inputEdtYearInteractiveAdd
            imageView = imgInteractiveAdd
            imageProgressBar = interactiveAddDeterminateBar
            textProgressBar = interactiveAddDeterminateBarTextView

            val contractImage = ActivityResultContracts.GetContent()
            val launcherImage = registerForActivityResult(contractImage) { uri ->
                filePath = uri
                if (filePath != null) {
                    imageView.load(filePath) { placeholder(R.drawable.ic_loading) }
                }
            }

            imageView.setOnClickListener { launcherImage.launch("image/*") }
            btnSendInteractiveAdd.setOnClickListener { checkSend() }
        }
    }

    private fun checkSend() {
        val errorEdtName = viewModel.isEmptyEditText(edtName.text.toString())
        val errorEdtYear = viewModel.isEmptyEditText(edtYear.text.toString())
        val isCompleted = errorEdtName == null && errorEdtYear == null && filePath != null
        inputEdtName.error = errorEdtName
        inputEdtYear.error = errorEdtYear

        when {
            filePath == null -> {
                showToast(getString(R.string.toast_error_image))
            }
            isCompleted -> {
                openSendDialog()
            }
            else -> {}
        }
    }

    private fun openSendDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.dialog_send_title_add_interactive))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ -> sendInteractive() }
            .setNegativeButton(getString(R.string.dialog_no)) { _, _ -> }
            .show()
    }

    private fun sendInteractive() {
        val nameAuthor = edtName.text.toString()
        val year = edtYear.text.toString()
        val comment = edtComment.text.toString()
        val image = filePath ?: throw RuntimeException("filePath == null")

        viewModel.addInteractive(AddInteractiveModel(
            nameAuthor = nameAuthor,
            year = year,
            comment = comment,
            image = image
        ),
            onProgress = { showProgress(it) },
            onSuccess = { openOnSussesDialog() },
            onFailure = { error ->
                showToast(String.format(getString(R.string.toast_fail), error))
                goInteractive()
            })
    }

    private fun openOnSussesDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.dialog_send_title_on_susses))
            .setMessage(getString(R.string.dialog_send_message_on_susses))
            .setPositiveButton(getString(R.string.dialog_great)) { _, _ -> goInteractive() }
            .show()
    }

    private fun goInteractive() {
        findNavController().popBackStack()
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(progressLoad: Int) {
        linearLayout.visibility = View.INVISIBLE
        textProgressBar.visibility = View.VISIBLE
        imageProgressBar.apply {
            startAnimation(getCustomAnimation())
            visibility = View.VISIBLE
            progress = progressLoad
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showToast(getString(R.string.toast_wait_load))
        }
    }

    private fun getCustomAnimation(): Animation {
        return AlphaAnimation(0.7f, 1.0f).apply {
            duration = 1000
            startOffset = 50
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}