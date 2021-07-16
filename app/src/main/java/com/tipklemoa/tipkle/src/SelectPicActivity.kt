package com.tipklemoa.tipkle.src

import android.Manifest
import android.os.Bundle
import com.tipklemoa.tipkle.config.BaseActivity
import com.tipklemoa.tipkle.databinding.ActivitySelectPicBinding
import android.provider.MediaStore
import java.util.ArrayList
import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
class SelectPicActivity : BaseActivity<ActivitySelectPicBinding>(ActivitySelectPicBinding::inflate) {

    var adapter:SelectPicAdapter?=null

    private var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            getAllShownImagesPath()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            showCustomToast("갤러리 권한 접근을 허용하지 않으셨습니다")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //권한 체크
        TedPermission.with(applicationContext)
            .setPermissionListener(permissionListener)
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .check()

        binding.rvGallery.setOnItemClickListener { parent, view, position, id ->

        }
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
        val uriArr = arrayListOf("1")
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
        adapter = SelectPicAdapter(this, uriArr)
        binding.rvGallery.numColumns = 3 // 한 줄에 3개씩
        binding.rvGallery.adapter = adapter
    }
}