package com.remotebash.model

class UsuarioModel() {

    constructor(email:String, password:String) : this() {
        this.email = email
        this.password = password
    }

    var id:Int? = null
    var name:String? = null
    var password:String? = null
    var cellphone:String? = null
    var email:String? = null
    var address:String? = null

}