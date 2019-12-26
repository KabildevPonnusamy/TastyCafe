package com.tastycafe.mykotlinsample.Common.CommonModels

class ProfileDatas  {

    var id: Int = 0
    var email: String? = null
    var password : String ? =null
    var name : String? = null
    var mobile : String? = null
    var created_date: String? = null

    constructor()

    constructor(id: Int, email: String, password : String, name : String, mobile: String,
                created_date: String) {
        this.id = id
        this.email = email
        this.password = password
        this.name = name
        this.mobile = mobile
        this.created_date = created_date
                }

    constructor(userView: String, passView: String, emailView: String, mobileView: String) {
        this.email = email
        this.password = password
        this.name = name
        this.mobile = mobile
                }

}