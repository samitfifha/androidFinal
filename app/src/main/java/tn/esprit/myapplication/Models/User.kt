package tn.esprit.myapplication.Models

data class User(val _id: String ? = null,
                var phone: String ? = null,
                var email: String ? = null,
                val image: String ? = null,
                var password: String ? = null,
                var firstname: String ? = null,
                var lastname: String ? = null,
                var adress: String ? = null,
                val role:String?=null,
                val token:String?=null,




                )
