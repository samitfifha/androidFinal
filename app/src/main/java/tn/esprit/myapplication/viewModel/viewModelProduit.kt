package tn.esprit.myapplication.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.api.RetrofiteInstance
import tn.esprit.myapplication.Models.Product

class viewModelProduit : ViewModel() {
    lateinit var lifeDataList: MutableLiveData<MutableList<Product>?>
    init {
        lifeDataList = MutableLiveData()

    }
    fun getLiveDataObserver(): MutableLiveData<MutableList<Product>?> {
        return lifeDataList
    }
    fun makeApiCall2(context: Context?){
        val apiInterface = RetrofiteInstance.api(context)
        apiInterface.getProduct().enqueue(object : Callback<MutableList<Product>> {

            override fun onFailure(call: Call<MutableList<Product>>, t: Throwable) {
                lifeDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<MutableList<Product>>,
                response: Response<MutableList<Product>>
            ) {
                lifeDataList.postValue(response.body())
            }


        })

    }


}