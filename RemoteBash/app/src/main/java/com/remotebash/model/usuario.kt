package com.remotebash.model

class usuario(val id:Long, val name:String, val password:String, val cellPhone:String,
              val email:String, val address:String, val role:List<role>) {
}