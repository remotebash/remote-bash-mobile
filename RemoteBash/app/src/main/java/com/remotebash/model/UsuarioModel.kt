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

    override fun toString(): String {
        return String.format("id:%d, nome:%s, celular:%s, email:%s, endereço:%s", id, name, cellphone, email, address)
    }
}