package com.remotebash.model

class ComandoModel() {

    constructor(comando: String, idComputador: Int, sistemaOperacional: String, idUsuario: Int): this(){
        this.command = comando
        this.idComputer = idComputador
        this.operationalSystem = sistemaOperacional
        this.userId = idUsuario
    }

    var command: String? = null
    var end: String? = null
    var executed: Boolean? = null
    var idCommand: String? = null
    var idComputer: Int = 0
    var operationalSystem: String? = null
    var result: String? = null
    var start: String? = null
    var userId: Int = 0

}