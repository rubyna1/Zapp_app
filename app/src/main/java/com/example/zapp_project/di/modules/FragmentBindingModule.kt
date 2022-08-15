package com.example.zapp_project.di.modules

import com.example.zapp_project.di.scopes.FragmentScope
import com.example.zapp_project.ui.create.CreateUserFragment
import com.example.zapp_project.ui.edit.EditUserFragment
import com.example.zapp_project.ui.onBoarding.OnBoardingFragment
import com.example.zapp_project.ui.view.ViewUserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindDataFragment(): OnBoardingFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindCreateUserFragment(): CreateUserFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindEditUserFragment(): EditUserFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindViewUserFragment(): ViewUserFragment
}