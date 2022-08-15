package com.example.zapp_project.di

import android.app.Application
import com.example.zapp_project.Zapp
import com.example.zapp_project.di.modules.ActivityBindingModule
import com.example.zapp_project.di.modules.ApiModule
import com.example.zapp_project.di.modules.ApplicationBindingModule
import com.example.zapp_project.di.modules.FragmentBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidInjectionModule::class), (ActivityBindingModule::class), (ApplicationBindingModule::class),
        (FragmentBindingModule::class), (ApiModule::class)]
)
interface AppComponent : AndroidInjector<Zapp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}