package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentInteractiveAddBinding
import java.util.*

class InteractiveAddFragment : Fragment() {

    private var _binding: FragmentInteractiveAddBinding? = null
    private val binding: FragmentInteractiveAddBinding
        get() = _binding ?: throw RuntimeException("FragmentInteractiveAddBinding == null")
    private lateinit var viewModel: InteractiveAddViewModel
    private lateinit var imageView: ImageView
    private lateinit var edtNameAuthor: EditText
    private lateinit var edtYear: EditText
    private lateinit var edtComment: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInteractiveAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        with(binding) {
            edtNameAuthor = edtNameInteractiveAdd
            edtYear = edtYearInteractiveAdd
            edtComment = edtCommentInteractiveAdd
            imageView = imgInteractiveAdd
            btnSendInteractiveAdd.setOnClickListener {
                checkSend()
            }
        }
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[InteractiveAddViewModel::class.java]
    }

    private fun checkSend() {
        if (edtNameAuthor.text.isEmpty() || edtYear.text.isEmpty()) {
            if (edtNameAuthor.text.isEmpty()) {
                binding.inputEdtNameInteractiveAdd.error = "Обязательное поле"
            } else {
                binding.inputEdtNameInteractiveAdd.error = null
            }
            if (edtYear.text.isEmpty()) {
                binding.inputEdtYearInteractiveAdd.error = "Обязательное поле"
            } else {
                binding.inputEdtYearInteractiveAdd.error = null
            }
        } else {
            openSendDialog()
        }
    }

    private fun sendInteractive() {
        var mDataBase: DatabaseReference
        val idRand: UUID = UUID.randomUUID()
        val id = idRand.toString()
        val nameAuthor = edtNameAuthor.text.toString()
        val year = edtYear.text.toString()
        val comment = edtComment.text.toString()
        val image = "gs://skazki-99ce4.appspot.com/Interactive/$id"

        mDataBase = FirebaseDatabase.getInstance().getReference("Interactive/$id/ID")
        mDataBase.ref.setValue(id)

        mDataBase = FirebaseDatabase.getInstance().getReference("Interactive/$id/Check")
        mDataBase.ref.setValue(false)

        mDataBase = FirebaseDatabase.getInstance().getReference("Interactive/$id/Name")
        mDataBase.ref.setValue(nameAuthor)

        mDataBase = FirebaseDatabase.getInstance().getReference("Interactive/$id/Year")
        mDataBase.ref.setValue(year)

        mDataBase = FirebaseDatabase.getInstance().getReference("Interactive/$id/Comment")
        mDataBase.ref.setValue(comment)

        mDataBase = FirebaseDatabase.getInstance().getReference("Interactive/$id/Image")
        mDataBase.ref.setValue(image)
    }

    private fun openSendDialog() {
        val sendDialog = AlertDialog.Builder(
            requireActivity()
        )
        sendDialog.setTitle("Отправить художество?")
        sendDialog.setPositiveButton(
            "Да!)"
        ) { _, _ ->
            sendInteractive()
        }
        sendDialog.setNegativeButton("Ой, нет!") { _, _ ->
        }
        sendDialog.show()
    }

    companion object {
        fun newInstance(): InteractiveAddFragment {
            return InteractiveAddFragment()
        }
    }
}