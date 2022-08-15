package com.example.zapp_project.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationBindingModule {
    @Binds
    abstract fun bindContext(app: Application): Context
}