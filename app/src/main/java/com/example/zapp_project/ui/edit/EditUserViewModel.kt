package com.example.zapp_project.ui.edit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.zapp_project.GetUserQuery
import com.example.zapp_project.UpdateUserMutation
import com.example.zapp_project.entity.UserModel
import com.example.zapp_project.type.UpdateUserInput
import kotlinx.coroutines.launch
import javax.inject.Inject


class EditUserViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var client: ApolloClient
    var userData = MutableLiveData<GetUserQuery.User?>()
    var updateUserDataResponse = MutableLiveData<UserModel>()
    var updatedResponse = MutableLiveData<Boolean>()

    fun getUser(id: String) {
        viewModelScope.launch {
            val response = client.query(GetUserQuery(id)).execute()
            userData.postValue(response.data?.user)
        }
    }

    fun updateUserData(id: String, input: UpdateUserInput) {
        viewModelScope.launch {
            val response = client.mutation(UpdateUserMutation(id, input)).execute()
            updatedResponse.postValue(response.hasErrors())
            updateUserDataResponse.postValue(
                UserModel(
                    response.data?.updateUser?.id,
                    response.data?.updateUser?.name,
                    response.data?.updateUser?.username,
                    response.data?.updateUser?.email
                )
            )
        }
    }
}