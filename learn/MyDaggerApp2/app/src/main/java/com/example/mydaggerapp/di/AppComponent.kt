package com.example.mydaggerapp.di

import com.example.mydaggerapp.MainActivity
import dagger.Component

@Component(modules = [MyModule::class])
interface AppComponent {
    fun injectActivity(activity: MainActivity)
}