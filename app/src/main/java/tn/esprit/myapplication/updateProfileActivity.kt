package tn.esprit.myapplication

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.myapplication.Models.User
import tn.esprit.myapplication.api.RetrofiteInstance

class updateProfileActivity : AppCompatActivity() {

    lateinit var nomUpdate: TextInputEditText
    lateinit var nomLayoutUpdate: TextInputLayout

    lateinit var prenomUpdate: TextInputEditText
    lateinit var prenomLayoutUpdate: TextInputLayout

    lateinit var emailUpdate: TextInputEditText
    lateinit var emailLayoutUpdate: TextInputLayout

    lateinit var phoneUpdate: TextInputEditText
    lateinit var phoneLayoutUpdate: TextInputLayout

    lateinit var addressUpdate: TextInputEditText
    lateinit var addressLayoutUpdate: TextInputLayout



    lateinit var imageUpdate: ImageView

    lateinit var updatebutton: Button



    lateinit var mSharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        nomUpdate = findViewById(R.id.nomUpdateText)
        nomLayoutUpdate = findViewById(R.id.nomUpdate)
        prenomUpdate = findViewById(R.id.prenomUpdateText)
        prenomLayoutUpdate = findViewById(R.id.prenomUpdate)
        emailUpdate = findViewById(R.id.nomUpdateText88)
        emailLayoutUpdate = findViewById(R.id.nomUpdate88)
        phoneUpdate = findViewById(R.id.prenomUpdateText88)
        phoneLayoutUpdate = findViewById(R.id.prenomUpdate88)
        addressUpdate = findViewById(R.id.prenomUpdateText99)
        addressLayoutUpdate = findViewById(R.id.prenomUpdate99)
        updatebutton = findViewById(R.id.button3)
        imageUpdate = findViewById(R.id.imageView4)
        mSharedPref = getSharedPreferences("LOGIN_PREF", AppCompatActivity.MODE_PRIVATE);
        val nomStr: String = mSharedPref.getString("NOM", null).toString()
        val prenomStr: String = mSharedPref.getString("PRENOM", null).toString()
        val imageStr: String = mSharedPref.getString("IMAGE2", null).toString()
        val idUser: String = mSharedPref.getString("USER_ID", null).toString()
        Glide.with(this).load(RetrofiteInstance.BASE_URL + imageStr).into(imageUpdate)
        updatebutton.setOnClickListener {

            val nom = nomUpdate.text.toString().trim()
            val prenom = prenomUpdate.text.toString().trim()
            val email = emailUpdate.text.toString().trim()
            val phone = phoneUpdate.text.toString().trim()
            val address = addressUpdate.text.toString().trim()

            if (validate()) {
                //val apiInterface = UserApi.create()
                val apiInterface = RetrofiteInstance.api(this)
                //progBar.visibility = View.VISIBLE

                var user = User()
                user.firstname = nom
                user.lastname = prenom
                user.phone = phone
                user.email = email
                user.adress = address
                apiInterface.updateProfile(idUser,
                    user
                ).enqueue(object :
                    Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {

                        val user = response.body()



                        Toast.makeText(this@updateProfileActivity, "Update Success", Toast.LENGTH_SHORT).show()
                        finish()


                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {

                        Toast.makeText(this@updateProfileActivity, "Connexion error!", Toast.LENGTH_SHORT).show()

                    }

                })

            }



        }

    }
    private fun validate(): Boolean {
        prenomLayoutUpdate.error = null
        nomLayoutUpdate.error = null
        emailUpdate.error = null
        addressLayoutUpdate.error = null
        phoneLayoutUpdate.error = null

        if (nomUpdate.text!!.isEmpty()) {
            nomLayoutUpdate.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (prenomUpdate.text!!.isEmpty()) {
            prenomLayoutUpdate.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (emailUpdate.text!!.isEmpty()) {
            emailLayoutUpdate.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (phoneUpdate.text!!.isEmpty()) {
            phoneLayoutUpdate.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (addressUpdate.text!!.length < 8) {
            addressLayoutUpdate.setError("Password Length must be more than " + 8 + "characters")
            return false
        }



        return true
    }


}