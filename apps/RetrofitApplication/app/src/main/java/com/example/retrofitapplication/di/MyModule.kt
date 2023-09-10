package com.example.retrofitapplication.di

import android.content.Context
import com.example.retrofitapplication.api.ApiUser
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object MyModule {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiUser = retrofit.create(ApiUser::class.java)

    @Provides
    fun provideApiUser(): ApiUser = apiUser

//    @Provides
//    fun provideContext(): Context =
}