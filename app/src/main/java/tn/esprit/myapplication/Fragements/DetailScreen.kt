package tn.esprit.myapplication.Fragements

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mapbox.maps.extension.style.expressions.dsl.generated.product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.Models.Product
import tn.esprit.myapplication.R
import tn.esprit.myapplication.api.RetrofiteInstance


class DetailScreen : AppCompatActivity() {
    //private  val args :  DetailScreenArgs   by navArgs()
    lateinit var buyButton: Button
    lateinit var nomdetail : TextView
    lateinit var prixdetail : TextView
    lateinit var imagedetail: ImageView
    lateinit var mSharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
     {
        // Inflate the layout for this fragment
        //val rootView = inflater.inflate(R.layout.fragment_detail_screen, container, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detail_screen)

        buyButton = findViewById(R.id.Buybutton)
        val imagedetail : ImageView = findViewById(R.id.imagedetail)
        val nomdetail :TextView = findViewById(R.id.nomdetail)
         val prixdetail:TextView = findViewById(R.id.prixdetail)
        val ss:String = intent.getStringExtra("product object").toString()

        val apiInterface = RetrofiteInstance.api(this)
        apiInterface.getProductById(ss).enqueue(object : Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(this@DetailScreen, "Probleme de connection", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful){
                    val product : Product = response.body()!!
                    nomdetail.text = product.nameproduct
                    prixdetail.text = product.price
                    val replaced = product.image!!.replace("\\", "/")

                    Glide.with(this@DetailScreen).load(RetrofiteInstance.BASE_URL + replaced).into(imagedetail)
                    buyButton.setOnClickListener{
                        mSharedPref = getSharedPreferences("LOGIN_PREF", AppCompatActivity.MODE_PRIVATE);
                        val idUser: String = mSharedPref.getString("USER_ID", null).toString()

                        apiInterface.buyProduit(product._id!!,idUser).enqueue(object : Callback<Product> {
                            override fun onFailure(call: Call<Product>, t: Throwable) {
                                Toast.makeText(this@DetailScreen, "Probleme de connection", Toast.LENGTH_SHORT).show()

                            }

                            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                                if (response.isSuccessful){
                                    val stade : Product = response.body()!!
                                    Toast.makeText(this@DetailScreen, "Ajouter", Toast.LENGTH_SHORT).show()


                                }
                            }


                        })

                    }


                }
            }


        })


    }


}