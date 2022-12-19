package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ladushkinySkazky.ladushkinnyskazki.R
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
    private val idRand: UUID = UUID.randomUUID()
    private val id = idRand.toString()
    private var filePath: Uri? = null
    private var storageReference: StorageReference? = null

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
        observeViewModel()
    }

    private fun setupView() {
        with(binding) {
            imageView = imgInteractiveAdd
            val contractImage = ActivityResultContracts.GetContent()
            val launcherImage = registerForActivityResult(contractImage) {
                filePath = it
                if (filePath != null) {
                    imageView.load(filePath) { placeholder(R.drawable.ic_loading) }
                }
            }
            imageView.setOnClickListener { launcherImage.launch("image/*") }

            edtNameAuthor = edtNameInteractiveAdd
            edtYear = edtYearInteractiveAdd
            edtComment = edtCommentInteractiveAdd
            btnSendInteractiveAdd.setOnClickListener { checkSend() }
        }
    }

    private fun sendImage() {
        if (filePath != null) {
            val progressDialog = ProgressDialog(requireActivity())
            progressDialog.setTitle("Загрузка...")
            progressDialog.show()
            //соединение с бд
            storageReference = FirebaseStorage.getInstance().reference
            val ref = storageReference!!.child("Interactive/$id")
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    //диалоговое окно с состоянием загрузки
                    progressDialog.dismiss()
                    Toast.makeText(requireActivity(), "Загружено!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    //диалоговое окно на случай ошибки
                    progressDialog.dismiss()
                    Toast.makeText(
                        requireActivity(),
                        "Ошибка загрузки изображения" + e.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    //оповещение об успешной отправки
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Загружено " + progress.toInt() + "%")
                    //создать и отправить объект в бд
                    sendInteractive()
                }
                .addOnCompleteListener {
                    openCloseDialog()
                }
        } else {
            Toast.makeText(requireActivity(), "Прикрепите изображение", Toast.LENGTH_SHORT)
                .show()
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
        val nameAuthor = edtNameAuthor.text.toString()
        val year = edtYear.text.toString()
        val comment = edtComment.text.toString()
        val image = "gs://skazki-99ce4.appspot.com/Interactive/$id"
        val dataTime = DateFormat.format("yyyy-MM-dd hh:mm:ss a", Date())

        var mDataBase: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Interactive/$id/ID")
        mDataBase.ref.setValue(id)

        mDataBase = FirebaseDatabase.getInstance().getReference("Interactive/$id/DataTime")
        mDataBase.ref.setValue(dataTime.toString())

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
            sendImage()
        }
        sendDialog.setNegativeButton("Ой, нет!") { _, _ ->
        }
        sendDialog.show()
    }

    private fun openCloseDialog() {
        val sendDialog = AlertDialog.Builder(
            requireActivity()
        )
        sendDialog.setTitle("Большое спасибо!")
        sendDialog.setMessage("Мы проверим корректность художества, затем добавим его в галерею!")
        sendDialog.setPositiveButton("Отлично)") { _, _ ->
            goInteractive()
        }
        sendDialog.show()
    }

    private fun goInteractive() {
        findNavController().popBackStack()
    }
}