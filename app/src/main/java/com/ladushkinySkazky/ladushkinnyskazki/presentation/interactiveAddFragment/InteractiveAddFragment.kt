package com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveAddFragment

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ladushkinySkazky.ladushkinnyskazki.R
import com.ladushkinySkazky.ladushkinnyskazki.databinding.FragmentInteractiveAddBinding
import com.ladushkinySkazky.ladushkinnyskazki.presentation.interactiveFragment.InteractiveFragment
import com.squareup.picasso.Picasso
import java.io.IOException
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
            imageView.setOnClickListener {
                imageLoad()
            }
            btnSendInteractiveAdd.setOnClickListener {
                checkSend()
            }
        }
    }

    private fun imageLoad() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, data)",
            "androidx.fragment.app.Fragment"
        )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data!!
            try {
                Picasso.get().load(filePath).into(imageView)
            } catch (e: IOException) {
                e.printStackTrace()
            }
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

        var mDataBase: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Interactive/$id/ID")
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
        val interactiveFragment = InteractiveFragment.newInstance()
        val manager = (activity as AppCompatActivity).supportFragmentManager
        manager.beginTransaction()
            .replace(R.id.container, interactiveFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(InteractiveAddFragment.toString())
            .commit()
    }

    companion object {
        fun newInstance(): InteractiveAddFragment {
            return InteractiveAddFragment()
        }

        private const val PICK_IMAGE_REQUEST = 71
    }
}