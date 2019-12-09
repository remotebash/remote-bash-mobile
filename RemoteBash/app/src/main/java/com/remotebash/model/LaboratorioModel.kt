package com.remotebash.model

class LaboratorioModel() {
    constructor(nome:String, descricao:String): this(){
        this.name = nome
        this.description = descricao
    }

    var id: Long? = 0
    var name: String? = null
    var description: String? = null
    var user: UsuarioModel? = null
    var computerSet: List<ComputadorModel>? = null

}
