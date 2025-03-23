package com.example.newsapp.api


import eg.iti.mad.productsappmvvm.model.ProductsItem
import eg.iti.mad.productsappmvvm.model.ProductsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface WebServices {

    @GET("products")
    suspend fun getProducts(): ProductsResponse
}