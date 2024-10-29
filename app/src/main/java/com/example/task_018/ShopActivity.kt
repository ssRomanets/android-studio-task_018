package com.example.task_018

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.IOException
import kotlin.system.exitProcess

class ShopActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 382
    var bitmap: Bitmap? = null
    var products: MutableList<Product> = mutableListOf()

    private lateinit var toolbarMain: Toolbar

    private lateinit var listViewLV: ListView
    private lateinit var productNameET: EditText
    private lateinit var productPriceET: EditText
    private lateinit var editImageIV: ImageView
    private lateinit var productSaveBTN: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Продуктовый магазин."

        init()

        editImageIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST )
        }

        productSaveBTN.setOnClickListener{
            createPerson()
            val listAdapter = ListAdapter(this@ShopActivity, products)
            listViewLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            clearEditFields()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain->{
                moveTaskToBack(true);
                exitProcess(-1)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        listViewLV = findViewById(R.id.listViewLV)
        productNameET = findViewById(R.id.productNameET)
        productPriceET = findViewById(R.id.productPriceET)
        editImageIV = findViewById(R.id.editImageIV)
        productSaveBTN = findViewById(R.id.productSaveBTN)
    }

    private fun createPerson() {
        val productName = productNameET.text.toString()
        val productPrice = productPriceET.text.toString()
        val productImage = bitmap
        val product = Product(productName, productPrice, productImage)
        products.add(product)
    }

    private fun clearEditFields() {
        productNameET.text.clear()
        productPriceET.text.clear()
        editImageIV.setImageResource(R.drawable.product_ic)
    }

    override fun onActivityResult(requestedCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestedCode, resultCode, data)
        editImageIV = findViewById(R.id.editImageIV)
        when (requestedCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                editImageIV.setImageBitmap(bitmap)
            }
        }
    }


}