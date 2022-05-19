package tn.esprit.myapplication.Fragements

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.Models.Product
import tn.esprit.myapplication.R
import tn.esprit.myapplication.adapter.produitAdapter
import tn.esprit.myapplication.api.RetrofiteInstance
import tn.esprit.myapplication.utils.ClickHandler
import tn.esprit.myapplication.viewModel.viewModelProduit

lateinit var adapter23: produitAdapter
lateinit var produit_id23:String
lateinit var recyclerViewequipe25: RecyclerView
private var arrayList111: MutableList<Product> = mutableListOf()
private var arrayList251: MutableList<String>? = null
lateinit var mSharedPref : SharedPreferences



class Shopping_Fragements : Fragment() , ClickHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView  =inflater.inflate(R.layout.fragment_shopping__fragements, container, false)
        recyclerViewequipe25 = rootView.findViewById(R.id.recycleshopp)
        recyclerViewequipe25.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        adapter23 = produitAdapter(this,this)
        recyclerViewequipe25.adapter = adapter23
        initViewModel()

        return rootView
    }
    private fun initViewModel() {
        mSharedPref = requireActivity().getSharedPreferences("LOGIN_PREF", AppCompatActivity.MODE_PRIVATE);
        val idUser: String = mSharedPref.getString("USER_ID", null).toString()

        val apiInterface = RetrofiteInstance.api(context)
        apiInterface.getProductPanier(idUser).enqueue(object : Callback<MutableList<String>> {
            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                Toast.makeText(context, "Error in getting list", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {
                //adapter23.setProduitList(response.body())
                //adapter23.notifyDataSetChanged()
                arrayList251?.clear()
                arrayList251 = response.body()

                for (name in arrayList251!!) {

                    apiInterface.getProductById(name).enqueue(object : Callback<Product> {
                        override fun onFailure(call: Call<Product>, t: Throwable) {
                            Toast.makeText(context, "Probleme de connection", Toast.LENGTH_SHORT).show()

                        }

                        override fun onResponse(call: Call<Product>, response: Response<Product>) {
                                val product : Product = response.body()!!
                            Log.w("product", product.nameproduct.toString())
                            arrayList111.add(product)
                            println("rrrrrrrrrrrrr")
                            println(arrayList111)
                            println("rrrrrrrrrrrrr")


                        }


                    })


                }
                adapter23.setProduitList(arrayList111)
                adapter23.notifyDataSetChanged()


            }


        })

    }

    override fun ClickItem(position: Int) {
        TODO("Not yet implemented")
    }


}