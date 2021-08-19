package com.tipklemoa.tipkle.src.mypage

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tipklemoa.tipkle.databinding.LayoutMypageBottomsheetBinding
import com.tipklemoa.tipkle.util.LoadingDialog
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditProfileBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: LayoutMypageBottomsheetBinding
    lateinit var mLoadingDialog: LoadingDialog

    private val REQUEST_TAKE_PHOTO = 1 //안겹치게 잘 확인하자.. 바보조이^^
    private val Gallery = 2
    var uri: Uri? = null
    var file: File? = null
    private var currentPhotoPath: String? = null
    private var timeStamp: String? = null
    private var imageFileName: String? = null //현재 파일 이름

    private var takePicListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            takePictureIntent()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("카메라 권한을 허용하지 않으셨습니다")
        }
    }

    private var galleryListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            loadImage()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("갤러리 접근 권한을 허용하지 않으셨습니다")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = LayoutMypageBottomsheetBinding.inflate(inflater, container, false)

        binding.layoutTakePicture.setOnClickListener {
            TedPermission.with(requireContext())
                .setPermissionListener(takePicListener)
                .setPermissions(
                    Manifest.permission.CAMERA
                )
                .check()
        }

        binding.layoutGallery.setOnClickListener {
            TedPermission.with(requireContext())
                .setPermissionListener(galleryListener)
                .setPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .check()
        }

        binding.layoutDeleteProfile.setOnClickListener {
            val bundle = bundleOf("takePic" to "ok")
            bundle.putString("uri", null)
            setFragmentResult("takePic", bundle)
            this.dismiss()
        }

        binding.btnMyPageBottomCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    //   갤러리 띄우기
    private fun loadImage() {
        Log.d("확인", "이미지 불러오는 중입니다.")
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//         사진추가
            if (currentPhotoPath != null) {
                file = File(currentPhotoPath)
                uri = Uri.fromFile(file)
                Log.d("확인", uri.toString())

            }
        }
        else if (requestCode == Gallery && resultCode==RESULT_OK) {
            uri = data?.data

            try {
                Log.d("확인", uri.toString())
                //val bitmap: Bitmap =MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
                //Log.d("확인", "선택한 사진은$bitmap")
            } catch (e: Exception) {
                Log.d("확인", "이미지 업로드 오류" + e.toString())
            }
        } else {
            Log.d("확인", "이미지 가져오기 오류")
        }

        val bundle = bundleOf("takePic" to "ok")
        bundle.putString("uri", uri.toString())
        setFragmentResult("takePic", bundle)
        this.dismiss()
    }


    // 사진 찍는 인텐트
    private fun takePictureIntent() {
        Log.d("test", "takePictureIntent")
        //editor.putString("")

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (context?.let { takePictureIntent.resolveActivity(it.packageManager) } != null) {

            val photoFile: File?
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.e("captureCamera Error", ex.toString())
                return
            }
            // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함
            val providerURI = FileProvider.getUriForFile(requireContext(), requireContext().packageName, photoFile)
            // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI)
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File { // Create an image file name
        timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        imageFileName = "JPEG_$timeStamp.jpg"
        val imageFile: File?
        val storageDir = File(
            Environment.getExternalStorageDirectory().toString() + "/Pictures",
            "팁끌"
        )
        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString())
            storageDir.mkdirs()
        }
        imageFile = File(storageDir, imageFileName)
        currentPhotoPath = imageFile.absolutePath
        return imageFile
    }

    // 로딩 다이얼로그, 즉 로딩창을 띄워줌.
    // 네트워크가 시작될 때 사용자가 무작정 기다리게 하지 않기 위해 작성.
    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }
    // 띄워 놓은 로딩 다이얼로그를 없앰.
    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    // 토스트를 쉽게 띄울 수 있게 해줌.
    fun showCustomToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}