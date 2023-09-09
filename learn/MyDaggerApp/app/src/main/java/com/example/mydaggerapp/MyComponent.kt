package com.example.mydaggerapp

import dagger.Component

@Component(modules = [MyModule::class])
interface MyComponent {
    fun getString(): String
}
