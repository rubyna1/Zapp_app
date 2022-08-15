package com.example.zapp_project.di.modules

import com.example.zapp_project.di.scopes.ActivityScope
import com.example.zapp_project.ui.MainContainerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainContainerActivity(): MainContainerActivity
}