package com.remotebash.model

class LaboratorioModel() {
    constructor(floor:String, capacity:String): this(){
        this.floor = floor
        this.capacity = capacity
    }

    var id: Long? = null
    var floor: String? = null
    var capacity: String? = null

}
