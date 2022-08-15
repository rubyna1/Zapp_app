package com.example.zapp_project.ui

import android.os.Bundle
import com.example.zapp_project.R
import com.example.zapp_project.ui.create.CreateUserFragment
import com.example.zapp_project.ui.edit.EditUserFragment
import com.example.zapp_project.ui.onBoarding.OnBoardingFragment
import com.example.zapp_project.ui.view.ViewUserFragment
import com.example.zapp_project.utils.Constants.CREATE_USER_FRAGMENT
import com.example.zapp_project.utils.Constants.EDIT_USER_FRAGMENT
import com.example.zapp_project.utils.Constants.ON_BOARDING_FRAGMENT
import com.example.zapp_project.utils.Constants.VIEW_USER_FRAGMENT
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment

class MainContainerActivity : DaggerAppCompatActivity(), OnBoardingFragment.NavigationInterface,
    ViewUserFragment.NavigationInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
        initialize()
    }

    private fun showFragment(fragment: DaggerFragment, tag: String) {
        supportFragmentManager.beginTransaction().add(R.id.activity_main_container, fragment, tag)
            .addToBackStack(tag).commit()
    }

    private fun initialize() {
        showFragment(OnBoardingFragment.newInstance(), ON_BOARDING_FRAGMENT)
    }

    override fun onCreateUserClicked() {
        showFragment(CreateUserFragment.newInstance(), CREATE_USER_FRAGMENT)
    }

    override fun onViewUserClicked(id: String) {
        showFragment(ViewUserFragment.newInstance(id), VIEW_USER_FRAGMENT)
    }

    override fun onEditUserClicked(id: String?) {
        showFragment(EditUserFragment.newInstance(id), EDIT_USER_FRAGMENT)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}