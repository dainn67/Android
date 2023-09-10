package com.example.retrofitapplication.di

import com.example.retrofitapplication.api.ApiUser
import dagger.Component

@Component(modules = [MyModule::class])
interface AppComponent {

    fun injectActivity(): ApiUser
}