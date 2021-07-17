package com.tipklemoa.tipkle. src

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityRegisterNewTipBinding
import kotlinx.coroutines.selects.select
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterNewTipActivity : BaseActivity<ActivityRegisterNewTipBinding>(ActivityRegisterNewTipBinding::inflate) {

    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var currentPhotoPath: String
    private lateinit var newTipPicAdapter: NewTipPicAdapter
    var selectedimageUrlList = arrayListOf<String>()

    private var takePicListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            takePictureIntent()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("카메라 권한을 허용하지 않으셨습니다")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedimageUrlList = if (intent.getStringArrayListExtra("selectedimageUrlList").isNullOrEmpty()){
            arrayListOf()
        } else{
            intent.getStringArrayListExtra("selectedimageUrlList") as ArrayList<String>
        }

        Log.d("test", selectedimageUrlList.size.toString())

        binding.btnNewTipBack.setOnClickListener {
            finish()
        }

        binding.btnTakePic.setOnClickListener {
            if (selectedimageUrlList.size==5){
                showCustomToast("사진은 최대 5개 선택할 수 있습니다.")
            }
            else {
                TedPermission.with(applicationContext)
                    .setPermissionListener(takePicListener)
                    .setPermissions(
                        Manifest.permission.CAMERA
                    )
                    .check()
            }
        }

        Log.d("test", selectedimageUrlList.size.toString())
        binding.tvPickedPicCount.text = selectedimageUrlList.size.toString()

        val imagelayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPickedPicList.layoutManager = imagelayoutManager
        newTipPicAdapter = NewTipPicAdapter(this, selectedimageUrlList)
        newTipPicAdapter.setOnButtonClickListener(onClicked)
        binding.rvPickedPicList.adapter = newTipPicAdapter
    }

    private val onClicked = object: NewTipPicAdapter.OnItemClickListener{
        override fun onClicked(position: Int) {
            selectedimageUrlList.remove(selectedimageUrlList[position])
            newTipPicAdapter.notifyItemRemoved(position)
            binding.tvPickedPicCount.text = selectedimageUrlList.size.toString()
        }
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // 사진 파일을 만듭니다.
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d("test", "error: $ex")
                    null
                }
                // photoUri를 보내는 코드
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        packageName,
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File { // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.d("test", "currentPhotoPath : $currentPhotoPath")
        }
    }

    //갤러리에 저장 & 방금 찍은 사진 리스트에 저장
    private fun savePhoto() {
        //사진 폴더에 저장하기 위한 경로 선언
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            Log.d("test", "currentPhotoPath2 : $currentPhotoPath")
            val f = File(currentPhotoPath)
            val uri = Uri.fromFile(f)
            mediaScanIntent.data = Uri.fromFile(f)
            selectedimageUrlList.add(uri.toString())
            sendBroadcast(mediaScanIntent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//         사진저장
        savePhoto()

        newTipPicAdapter.notifyItemInserted(selectedimageUrlList.size-1)
        binding.tvPickedPicCount.text = selectedimageUrlList.size.toString()
    }
}