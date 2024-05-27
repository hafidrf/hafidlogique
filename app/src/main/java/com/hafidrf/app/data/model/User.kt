package com.hafidrf.app.data.model

import com.google.gson.annotations.SerializedName

class User (

    //preview
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("title")
    val title: String?,

    @SerializedName("gender")
    val gender: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("registerDate")
    val registerDate: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("location")
    val location: Location?
)

data class Location(
    @SerializedName("street")
    val street: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("timezone")
    val timezone: String?
){
    override fun toString(): String {
        return "$street, $city, $state"
    }
}