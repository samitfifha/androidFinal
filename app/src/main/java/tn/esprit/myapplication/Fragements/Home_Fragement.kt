package tn.esprit.myapplication.Fragements

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.maps.extension.style.expressions.dsl.generated.product
import tn.esprit.myapplication.Models.Product
import tn.esprit.myapplication.R
import tn.esprit.myapplication.adapter.produitAdapter
import tn.esprit.myapplication.utils.ClickHandler
import tn.esprit.myapplication.viewModel.viewModelProduit

import androidx.navigation.fragment.findNavController as androidxNavigationFragmentFindNavController

lateinit var adapter2:produitAdapter
lateinit var produit_id:String
lateinit var recyclerViewequipe: RecyclerView
private lateinit var navController: NavController

class Home_Fragement : Fragment(R.layout.fragment_home__fragement) , ClickHandler {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home__fragement, container, false)
        recyclerViewequipe = rootView.findViewById(R.id.recycleHome)
        recyclerViewequipe.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter2 = produitAdapter(this,this)
        recyclerViewequipe.adapter = adapter2
        initViewModel()
        return rootView
    }

    private fun navigateDetails(product: Product) {
        val i = Intent(activity, DetailScreen::class.java)
        i.putExtra("product object", product._id)
        startActivity(i)

    }

    private fun initViewModel() {
        val viewModel: viewModelProduit = ViewModelProvider(this).get(viewModelProduit::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if(it != null) {
                adapter2.setProduitList(it)
                adapter2.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall2(context)
    }

    override fun ClickItem(position: Int) {
        Toast.makeText(context, "Item $position clicked", Toast.LENGTH_SHORT).show()

        var stadium : Product? = null
        val viewModel: viewModelProduit = ViewModelProvider(this).get(viewModelProduit::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if(it != null) {
                produit_id = it[position]._id!!
                stadium = it[position]


            } else {
                Toast.makeText(context, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall2(context)
        navigateDetails(stadium!!)




    }
}
