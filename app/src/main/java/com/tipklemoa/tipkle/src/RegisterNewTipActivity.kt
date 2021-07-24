package com.tipklemoa.tipkle.src

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tipklemoa.tipkle.R
import com.tipklemoa.tipkle.config.ApplicationClass
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.config.BaseResponse
import com.tipklemoa.tipkle.databinding.ActivityRegisterNewTipBinding
import com.tipklemoa.tipkle.src.model.DetailFeedResponse
import com.tipklemoa.tipkle.src.model.NewTipResponse
import com.tipklemoa.tipkle.src.model.PostNewTipRequest
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterNewTipActivity : BaseActivity<ActivityRegisterNewTipBinding>(ActivityRegisterNewTipBinding::inflate),
    MainView {

    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var currentPhotoPath: String
    private lateinit var newTipPicAdapter: NewTipPicAdapter
    private lateinit var timeStamp:String
    private lateinit var imageFileName:String //현재 파일 이름
    var selectedimageUrlList = java.util.ArrayList<String>()
    private var uploadImageList = java.util.ArrayList<String>()
    lateinit var editor:SharedPreferences.Editor
    var storage: FirebaseStorage? = null //파이어베이스
    var tvCatNotEmpty = false

    private var takePicListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            takePictureIntent()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("카메라 권한을 허용하지 않으셨습니다")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = FirebaseStorage.getInstance()
        editor = ApplicationClass.sSharedPreferences.edit()

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

        binding.layoutCategory.setOnClickListener {
            val cetegoryDialog = NewTipCategoryDialogFragment()
            cetegoryDialog.show(supportFragmentManager, cetegoryDialog.tag)
        }

        binding.edtHow.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edtHow.text.isNotEmpty()){
                    binding.tvHowStar.visibility = View.INVISIBLE
                }
                else{
                    binding.tvHowStar.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                activateButton()
            }

        })

        binding.edtWhen.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.edtWhen.text.isNotEmpty()){
                    binding.tvWhenStar.visibility = View.INVISIBLE
                }
                else{
                    binding.tvWhenStar.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                activateButton()
            }

        })

        binding.edtTipLine.setOnFocusChangeListener { v, hasFocus ->
            activateButton()
        }

        //액티비티에서 프래그먼트가 보내는 데이터를 받아올때!
        supportFragmentManager
            .setFragmentResultListener("category", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("category")
                Log.d("result", result.toString())
                // Do something with the result
                binding.tvNewTipCategory.text = result
                activateButton()
            }
    }

//    override fun onResume() {
//        super.onResume()
//
//
//    }

    private val onClicked = object: NewTipPicAdapter.OnItemClickListener{
        override fun onClicked(position: Int) {
            selectedimageUrlList.remove(selectedimageUrlList[position])
            newTipPicAdapter.notifyItemRemoved(position)
            binding.tvPickedPicCount.text = selectedimageUrlList.size.toString()
            activateButton()
        }
    }

    // 사진 찍는 인텐트
    private fun takePictureIntent() {
        Log.d("test", "takePictureIntent")
        //editor.putString("")

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
        Log.d("test", uri.toString())
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

//         사진추가
            newTipPicAdapter.notifyItemInserted(selectedimageUrlList.size-1)
            binding.tvPickedPicCount.text  = selectedimageUrlList.size.toString()
        }
    }

    private fun checkIsEmpty(){
        val dialogIsEmpty = NewTipAlertDialogFragment()
        val bundle = Bundle()
        var dialogText:String?=null
            //모두 입력
        if (selectedimageUrlList.isNotEmpty() && binding.edtWhen.text.length<4 ||
            binding.edtHow.text.length<4 && binding.tvNewTipCategory.text.toString()!="*카테고리"){
                dialogText = ""
            if (binding.edtHow.text.length<4) dialogText += "How를 4자 이상 40자 미만 작성해주세요.\n"
            if (binding.edtWhen.text.length<4) dialogText += "When을 4자 이상 40자 미만 작성해주세요."

            bundle.putString("dialogText", dialogText)
            dialogIsEmpty.arguments = bundle
            dialogIsEmpty.show(supportFragmentManager, dialogIsEmpty.tag)
        }
        else { //게시글 등록 API
            imageUploadFirebase()
        }
    }

    private fun activateButton() {
        //사진을 안올렸을때
        if (selectedimageUrlList.size!=0 && binding.edtWhen.text.isNotEmpty() &&
            binding.edtHow.text.isNotEmpty() && binding.tvNewTipCategory.text.toString()!="*카테고리") {
            binding.tvCompleteNewTip.isEnabled = true
            binding.tvCompleteNewTip.setTextColor(resources.getColor(R.color.mint))
        }
        else{
            binding.tvCompleteNewTip.isEnabled = false
            binding.tvCompleteNewTip.setTextColor(resources.getColor(R.color.DBGray))
        }
    }

    private fun imageUploadFirebase(){
        showLoadingDialog(this)

        if(selectedimageUrlList.size>1) { //사진 여러장일때
            for (i in selectedimageUrlList) {
                val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                var fileN = i.split("/").last()
                val split = fileN.split(".")
                fileN = split.first() + timestamp.split('.').first() + "." + split.last()
                val storageRef = storage?.reference?.child("media")?.child(fileN)

                storageRef?.putFile(Uri.parse(i)!!)?.addOnSuccessListener {
                    Log.d("test", "파이어베이스 업로드완료")
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        Log.d("test", uri.toString())
                        uploadImageList.add(uri.toString())

                        if (uploadImageList.size==selectedimageUrlList.size){
                            val postNewTipRequest = PostNewTipRequest(binding.tvNewTipCategory.text.toString(),
                                binding.edtWhen.text.toString(), binding.edtHow.text.toString(), binding.edtTipLine.text.toString(),
                                uploadImageList)

                            MainService(this).tryPostNewTip(postNewTipRequest)
                        }
                    }
                }
            }
        }else{ //사진 한장일때
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFile = "Image_"+timestamp+"_.jpg"
            val storageRef = storage?.reference?.child("media")?.child(imageFile)

            storageRef?.putFile(Uri.parse(selectedimageUrlList[0]))?.addOnSuccessListener {
                Log.d("photo", "파이어베이스 업로드완료")
                storageRef.downloadUrl.addOnSuccessListener { uri->
                    uploadImageList.add(uri.toString())
                    Log.d("size", uploadImageList.size.toString())
                    Log.d("size", uploadImageList[0])

                    val postNewTipRequest = PostNewTipRequest(binding.tvNewTipCategory.text.toString(),
                        binding.edtWhen.text.toString(), binding.edtHow.text.toString(), binding.edtTipLine.text.toString(),
                        uploadImageList)

                    MainService(this).tryPostNewTip(postNewTipRequest)
                }
            }
        }
    }

    override fun onGetFeedDetailSuccess(response: DetailFeedResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetFeedDetailFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFeedSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostSuccess(response: NewTipResponse) {
        dismissLoadingDialog()
        showCustomToast("팁 등록이 완료되었습니다!")
        this.finish()
        val intent = Intent(this, FeedDetailActivity::class.java)
        intent.putExtra("postId", response.result.postId)
        startActivity(intent)
    }

    override fun onPostFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostBookMarkSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostBookMarkFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteBookmarkSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteBookmarkFailure(message: String) {
        TODO("Not yet implemented")
    }
}