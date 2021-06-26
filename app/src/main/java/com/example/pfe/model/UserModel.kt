package com.example.pfe.model

import java.io.Serializable

class UserModel : Serializable {
    var id: String = ""
    var email: String? = ""
    var firstName: String = ""
    var lastName: String = ""
    var phone: String = ""
    var ville:String=""
    var role: String = ""


    constructor(
        id: String,
        email: String?,
        firstName: String,
        lastName: String,
        phone: String,
        ville:String,
        role: String
    ) {
        this.id = id
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.phone = phone
        this.ville=ville
        this.role = role
    }
}