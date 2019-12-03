package com.remotebash.model

class ComputadorModel() {

    constructor(
        ip: String, operationalSystem: String, ramMemory: String, hdTotal: String, hdUsage: String
        , processorModel: String, macaddress: String
    ) : this() {
        this.ip = ip
        this.operationalSystem = operationalSystem
        this.ramMemory = ramMemory
        this.hdTotal = hdTotal
        this.hdUsage = hdUsage
        this.processorModel = processorModel
        this.macaddress = macaddress
    }

    var id: Long? = null
    var macaddress: String? = null
    var ip: String? = null
    var operationalSystem: String? = null
    var ramMemory: String? = null
    var hdTotal: String? = null
    var hdUsage: String? = null
    var processorBrand: String? = null
    var processorModel: String? = null
    val user: UsuarioModel? = null

}