package com.ladushkinySkazky.ladushkinnyskazki.presentation.feedbackFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentFeedbackBinding
import com.ladushkinySkazky.ladushkinnyskazki.domian.models.FeedbackModel

class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private val binding: FragmentFeedbackBinding
        get() = _binding ?: throw RuntimeException("FragmentFeedbackBinding == null")

    private lateinit var name: TextView
    private lateinit var feedback: TextView
    private lateinit var contact: TextView
    private lateinit var btnSend: TextView
    private lateinit var btnClose: TextView

    private lateinit var viewModel: FeedbackViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFeedbackBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
        onClickListenerSend()
        onClickListenerClose()
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    private fun setupViews() {
        with(binding) {
            name = edtNameMenuDialog
            feedback = edtFeedbackMenuDialog
            contact = edtContactMenuDialog
            btnSend = imgBtnSendMenuDialog
            btnClose = btnCloseMenuDialog
        }
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[FeedbackViewModel::class.java]
    }

    private fun onClickListenerSend() {
        btnSend.setOnClickListener {
            if (feedback.text.isNotEmpty()) {
                val nameText = name.text.toString()
                val feedbackText = feedback.text.toString()
                val contactText = contact.text.toString()
                viewModel.addFeedback(FeedbackModel(nameText, feedbackText, contactText),
                    onSuccess = {
                        showToast(getString(R.string.toast_thanks))
                        findNavController().popBackStack()
                    },
                    onFail = {
                        showToast(getString(R.string.toast_error_send_feedback))
                        findNavController().popBackStack()
                    })
            } else {
                showToast(getString(R.string.toast_empty_message))
            }
        }
    }

    private fun onClickListenerClose() {
        btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}