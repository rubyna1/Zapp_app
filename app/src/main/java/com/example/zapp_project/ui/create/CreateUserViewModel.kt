package com.example.zapp_project.ui.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.zapp_project.CreateUserDataMutation
import com.example.zapp_project.entity.UserModel
import com.example.zapp_project.type.CreateUserInput
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateUserViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var client: ApolloClient
    var createUserResponse = MutableLiveData<UserModel>()
    fun createUser(input: CreateUserInput) {
        viewModelScope.launch {
            val response = client.mutation(CreateUserDataMutation(input)).execute()
            createUserResponse.postValue(
                UserModel(
                    response.data?.createUser?.id, response.data?.createUser?.name,
                    response.data?.createUser?.username,
                    response.data?.createUser?.email
                )
            )
        }
    }
}