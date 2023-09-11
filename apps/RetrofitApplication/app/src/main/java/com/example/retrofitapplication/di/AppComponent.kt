package com.example.retrofitapplication.di

import com.example.retrofitapplication.viewmodel.MyViewModel
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {

    //inject into viewModel
    fun injectActivity(viewModel: MyViewModel)
}