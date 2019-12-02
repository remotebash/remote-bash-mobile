package com.remotebash.model

class LaboratorioModel() {
    constructor(nome:String, descricao:String): this(){
        this.name = nome
        this.description = descricao
    }

    var id: Long? = null
    var name: String? = null
    var description: String? = null
    val user: UsuarioModel? = null
    val computerSet: List<ComputadorModel>? = null

}
