package com.tipklemoa.tipkle. src

import android.Manifest
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivityRegisterNewTipBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterNewTipActivity : BaseActivity<ActivityRegisterNewTipBinding>(ActivityRegisterNewTipBinding::inflate) {

    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var currentPhotoPath: String
    private lateinit var newTipPicAdapter: NewTipPicAdapter
    private lateinit var timeStamp:String
    private lateinit var imageFileName:String //현재 파일 이름
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

        binding.tvCompleteNewTip.setOnClickListener {
            checkIsEmpty()
        }
    }

    private val onClicked = object: NewTipPicAdapter.OnItemClickListener{
        override fun onClicked(position: Int) {
            selectedimageUrlList.remove(selectedimageUrlList[position])
            newTipPicAdapter.notifyItemRemoved(position)
            binding.tvPickedPicCount.text = selectedimageUrlList.size.toString()
        }
    }

    // 사진 찍는 인텐트
    private fun takePictureIntent() {
        Log.d("test", "takePictureIntent")
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {

            val photoFile: File?
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.e("captureCamera Error", ex.toString())
                return
            }
            // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함
            val providerURI = FileProvider.getUriForFile(this, packageName, photoFile)
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

    //갤러리에 저장 & 방금 찍은 사진 리스트에 저장
    private fun savePhoto() {
        //사진 폴더에 저장하기 위한 경로 선언
        val file = File(currentPhotoPath)
        val uri = Uri.fromFile(file)
        MediaScannerConnection.scanFile(this, arrayOf(file.toString()),
            null, null)
        selectedimageUrlList.add(file.toUri().toString())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
//         사진저장
            savePhoto()

//           팁 등록 페이지로 이미지 리스트를 넘김
            val intent = Intent(this, RegisterNewTipActivity::class.java)
            intent.putStringArrayListExtra("selectedimageUrlList", selectedimageUrlList)
            startActivity(intent)
            finish()
        }
    }

    private fun checkIsEmpty(){
        val dialogIsEmpty = NewTipAlertDialogFragment()
        val bundle = Bundle()
        var dialogText:String?=null

        //아무것도 등록 안했을때
        if (selectedimageUrlList.size==0 && binding.edtWhen.text.isNullOrEmpty() &&
            binding.edtHow.text.isNullOrEmpty() && binding.tvNewTipCategory.text.toString()=="*카테고리"){
            dialogText = "사진 등록, 카테고리, When, How는 필수 입력 항목이에요."
        } //사진만 등록
        else if (selectedimageUrlList.size!=0 && binding.edtWhen.text.isNullOrEmpty() &&
            binding.edtHow.text.isNullOrEmpty() && binding.tvNewTipCategory.text.toString()=="*카테고리"){
            dialogText = "카테고리, When, How는 필수 입력 항목이에요."
        } //When, How 입력 안함
        else if (selectedimageUrlList.size!=0 && binding.edtWhen.text.isNullOrEmpty() &&
            binding.edtHow.text.isNullOrEmpty() && binding.tvNewTipCategory.text.toString()!="*카테고리"){
            dialogText = "When, How는 필수 입력 항목이에요."
        } //사진, 카테고리 등록 안함
        else if (selectedimageUrlList.size==0 && binding.edtWhen.text.isNotEmpty() &&
            binding.edtHow.text.isNotEmpty() && binding.tvNewTipCategory.text.toString()=="*카테고리"){
            dialogText = "사진 등록, 카테고리는 필수 입력 항목이에요."
        } //사진 등록안함
        else if (selectedimageUrlList.isNullOrEmpty() && binding.edtWhen.text.isNotEmpty() &&
            binding.edtHow.text.isNotEmpty() && binding.tvNewTipCategory.text.toString()!="*카테고리"){
            dialogText = "게시글 사진을 등록해주세요."
        } //모두 입력
        else if (!selectedimageUrlList.isNullOrEmpty() && !binding.edtWhen.text.isNullOrEmpty() &&
            !binding.edtHow.text.isNullOrEmpty() && binding.tvNewTipCategory.text.toString()!="*카테고리"){
            if (binding.edtHow.text.length<4) dialogText = "How를 4자 이상 40자 미만 작성해주세요."
            else if (binding.edtWhen.text.length<4) dialogText = "When을 4자 이상 40자 미만 작성해주세요."
            else if (binding.edtHow.text.length<4 && binding.edtWhen.text.length<4) dialogText = "When, How를 4자 이상 40자 미만 작성해주세요."
        }
//        else{
//
//        }
        if (dialogText != null) {
            Log.d("dialogText", dialogText)
        }
        bundle.putString("dialogText", dialogText)
        dialogIsEmpty.arguments = bundle
        dialogIsEmpty.show(supportFragmentManager, dialogIsEmpty.tag)
    }
}