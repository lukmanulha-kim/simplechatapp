package com.lukman.simplechatapp

class User {
    var nama: String?=null
    var email: String?=null
    var uid: String?=null

    constructor(){}

    constructor(nama: String?, email:String?, uid:String?){
        this.nama = nama
        this.email = email
        this.uid = uid
    }
}