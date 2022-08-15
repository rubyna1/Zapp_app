package com.example.zapp_project.di.modules

import com.apollographql.apollo3.ApolloClient
import com.example.zapp_project.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides

@Module
class ApiModule {
    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder().serverUrl(BASE_URL).build()
    }
}