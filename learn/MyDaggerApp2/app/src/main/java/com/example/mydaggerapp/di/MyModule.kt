package com.example.mydaggerapp.di

import android.util.Log
import com.example.mydaggerapp.api.ApiUser
import com.example.mydaggerapp.model.Constants.Companion.TAG
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object MyModule {

    @Provides
    fun provideApiService(): ApiUser {
        Log.i(TAG, "provideApiUser")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiUser::class.java)
    }
}
