package com.example.zapp_project.ui.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.zapp_project.DeleteUserMutation
import com.example.zapp_project.GetUserQuery
import com.example.zapp_project.utils.Constants.VIEW_USER_FRAGMENT
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewUserViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var client: ApolloClient
    var userData = MutableLiveData<GetUserQuery.User?>()
    var deleteUserResponse = MutableLiveData<Boolean?>()

    fun getUser(id: String) {
        viewModelScope.launch {
            val response = client.query(GetUserQuery(id)).execute()
            userData.postValue(response.data?.user)
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            val response = client.mutation(DeleteUserMutation(id)).execute()
            Log.i(VIEW_USER_FRAGMENT,"${response.data} ${response.data?.deleteUser}")
            deleteUserResponse.postValue(response.data?.deleteUser)
        }
    }
}