package com.example.mydaggerapp

import dagger.Module
import dagger.Provides

@Module
object MyModule {
    @Provides
    fun provideGreeting(): String {
        return "Hello, Dagger!"
    }
}
