package com.tipklemoa.tipkle.src

import android.Manifest
import android.content.Intent
import android.media.MediaScannerConnection
import android.os.Bundle
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivitySelectPicBinding
import android.provider.MediaStore
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.tipklemoa.tipkle.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault

class SelectPicActivity : BaseActivity<ActivitySelectPicBinding>(ActivitySelectPicBinding::inflate) {

    var adapter:SelectPicAdapter?=null
    var uriArr = arrayListOf("1")
    private lateinit var timeStamp:String
    private lateinit var imageFileName:String //현재 파일 이름
    var selectedviewList = arrayListOf<View>()
    var selectedimageUrlList = arrayListOf<String>()
    var selectCount = 0

    //아래는 카메라 관련
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var currentPhotoPath: String

    private var readGalleryListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            getAllShownImagesPath()

            adapter = SelectPicAdapter(this@SelectPicActivity, uriArr)
            binding.rvGallery.numColumns = 3 // 한 줄에 3개씩
            binding.rvGallery.adapter = adapter
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("갤러리 권한 접근을 허용하지 않으셨습니다")
        }
    }

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

        //권한 체크
        TedPermission.with(applicationContext)
            .setPermissionListener(readGalleryListener)
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .check()

        binding.imgPicBack.setOnClickListener {
            finish()
        }

//        selectedimageUrlList = if (intent.getStringArrayListExtra("selectedimageUrlList")!=null){
//            intent.getStringArrayListExtra("selectedimageUrlList")!!
//        } else{
//            arrayListOf()
//        }

        binding.rvGallery.setOnItemClickListener { parent, view, position, id ->
            val selectedImage = binding.rvGallery.getItemAtPosition(position)
            val imgBackground = view.findViewById<ImageView>(R.id.imgSelectBackGround)
            val tvSelectImageCount = view.findViewById<TextView>(R.id.tvSelectImageCount)
            val imgFrame = view.findViewById<ImageView>(R.id.imgFrame)

            if (position>=1) { //이미지 부분 눌러서 추가해주는 부분
                if (imgBackground.visibility == View.INVISIBLE) { //이미지 처음 누를때
                    if (selectCount>=5){ //5개 이상인 상태에서 누르면 띄워주고 막기
                        showCustomToast("사진은 최대 5개 선택할 수 있습니다.")
                    }
                    else { //5개 미만
                        selectedviewList.add(view)
                        selectCount++

                        imgBackground.visibility = View.VISIBLE
                        tvSelectImageCount.visibility = View.VISIBLE
                        imgFrame.visibility = View.VISIBLE

                        selectedimageUrlList.add(selectedImage.toString())
                        Log.d("test", selectedimageUrlList.size.toString())
                    }
                } else { //이미 눌린 상태면
                    selectedviewList.remove(view)
                    selectCount--

                    imgBackground.visibility = View.INVISIBLE
                    tvSelectImageCount.visibility = View.INVISIBLE
                    imgFrame.visibility = View.INVISIBLE
                    //바로 전 뷰의 순서를 -1 해주어야함!
                    if (selectCount>=2) { //2개이상일때
                        selectedviewList[(selectedviewList.size-1)].findViewById<TextView>(R.id.tvSelectImageCount).text = selectCount.toString()
                    }

                    selectedimageUrlList.remove(selectedImage)
                    Log.d("test", selectedimageUrlList.size.toString())
                }
                tvSelectImageCount.text = selectCount.toString()
                binding.tvSelectPicCount.text = selectCount.toString()
                //checkSelecetOneMore()
            }
            else if (position==0){ //사진찍기
                if (selectCount>=5){ //5개 이상인 상태에서 누르면 띄워주고 막기
                    showCustomToast("사진은 최대 5개 선택할 수 있습니다.")
                }
                else{
                    TedPermission.with(applicationContext)
                        .setPermissionListener(takePicListener)
                        .setPermissions(
                            Manifest.permission.CAMERA
                        )
                        .check()
                }
            }
        }

        binding.tvCompleteAddPic.setOnClickListener {
            val toRegisterIntent = Intent(this, RegisterNewTipActivity::class.java)
            toRegisterIntent.putExtra("selectedimageUrlList", selectedimageUrlList)
            startActivity(toRegisterIntent)
            finish()
        }
    }

//    private fun checkSelecetOneMore(){
//        if (selectedviewList.size<1){
//            binding.tvCompleteAddPic.isEnabled = false
//            binding.tvCompleteAddPic.setTextColor(resources.getColor(R.color.DBGray))
//        }
//        else{
//            binding.tvCompleteAddPic.isEnabled = true
//            binding.tvCompleteAddPic.setTextColor(resources.getColor(R.color.black))
//        }
//    }

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
        timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", getDefault()).format(Date())
        imageFileName = "JPEG_$timeStamp.jpg"
        val imageFile: File?
        val storageDir = File(
            getExternalStorageDirectory().toString() + "/Pictures",
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

    private fun getAllShownImagesPath() {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var columnIndexID: Int
        var imageId: Long
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 사진 경로 Uri 가져오기
                columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext()) {
                    imageId = cursor.getLong(columnIndexID)
                    val uriImage = Uri.withAppendedPath(uriExternal, "" + imageId)
                    uriArr.add(uriImage.toString())
                }
            }
            cursor.close()
        }
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
}