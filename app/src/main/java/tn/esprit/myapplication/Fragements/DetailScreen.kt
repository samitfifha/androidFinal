package tn.esprit.myapplication.Fragements

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mapbox.maps.extension.style.expressions.dsl.generated.product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.Models.Product
import tn.esprit.myapplication.R
import tn.esprit.myapplication.api.RetrofiteInstance


class DetailScreen : Fragment() {
    private  val args :  DetailScreenArgs   by navArgs()
    lateinit var buyButton: Button
    lateinit var nomdetail : TextView
    lateinit var prixdetail : TextView
    lateinit var imagedetail: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_detail_screen, container, false)
        //buyButton = rootView.findViewById(R.id.)
        val imagedetail : ImageView = rootView.findViewById(R.id.imagedetail)
        val nomdetail :TextView = rootView.findViewById(R.id.nomdetail)

        val apiInterface = RetrofiteInstance.api(context)
        apiInterface.getProductById(args.idProduit).enqueue(object : Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(context, "Probleme de connection", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful){
                    val product : Product = response.body()!!
                    nomdetail.text = product.nameproduct
                    prixdetail.text = product.price
                    val replaced = product.image!!.replace("\\", "/")

                    Glide.with(this@DetailScreen).load(RetrofiteInstance.BASE_URL + replaced).into(imagedetail)



                }
            }


        })

        return rootView

    }


}