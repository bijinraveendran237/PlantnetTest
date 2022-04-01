package com.example.myapplication

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var btn: Button? = null
    private var tvRetakepic: TextView? = null
    private val GALLERY = 1
    private val CAMERA = 2
    private lateinit var customAdapter: CustomAdapter
    lateinit var recyclerView: RecyclerView
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById<View>(R.id.btn) as Button
        tvRetakepic = findViewById<View>(R.id.tv_retakepic) as TextView
        // finding progressbar by its id
        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar
        // getting the recyclerview by its id and setting the adapter
        recyclerView = findViewById(R.id.recyclerview)
        customAdapter = CustomAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter

        btn!!.setOnClickListener { showDemoData() }
        tvRetakepic!!.setOnClickListener { showPictureDialog() }

        //method used to give popup for camera when app gets on
        showPictureDialog()
    }

    private fun showDemoData() {
        progressBar!!.visibility = View.VISIBLE
        //hardcoded values with samle image url to show demo data
        val apiInterface = ApiInterface.create().getMovies("2b10n8b9VWY3NlKtvMJpZgt6aO",
            "https://my.plantnet.org/images/image_1.jpeg","flower")

        apiInterface.enqueue( object : Callback<Details> {
            override fun onResponse(call: Call<Details>?, response: Response<Details>?) {
                progressBar!!.visibility = View.INVISIBLE
                Log.d("response", response.toString())
                if(response?.body() != null)
                {
                    // This will pass the ArrayList to our Adapter
                    customAdapter.setMovieListItems(listOf(response.body()))
                }
            }
            override fun onFailure(call: Call<Details>?, t: Throwable?) {
                progressBar!!.visibility = View.INVISIBLE
                Log.d("error", t.toString())
            }
        })
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
        {
        return
        }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    saveImage(bitmap,data.data)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            saveImage(thumbnail,data.data)
        }
    }

    fun saveImage(myBitmap: Bitmap, data: Uri?):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

//            val file: File = File(f.path)
//            val apiInterface = ApiInterface.create().getMovies("2b10n8b9VWY3NlKtvMJpZgt6aO",
//                file,"flower")
//            apiInterface.enqueue( object : Callback<Details> {
//                override fun onResponse(call: Call<Details>?, response: Response<Details>?) {
//                    Log.d("response", response.toString())
//                    if(response?.body() != null)
//                    {
//                        Log.d("response", response.toString())
//                        customAdapter.setMovieListItems(listOf(response.body()))
//                        // This will pass the ArrayList to our Adapter
//                    }
//                }
//                override fun onFailure(call: Call<Details>?, t: Throwable?) {
//                    Log.d("error", t.toString())
//                }
//            })
        return ""
    }

}
