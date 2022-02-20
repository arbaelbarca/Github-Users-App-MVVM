package com.arbaelbarca.githublistapp.data.api

import com.arbaelbarca.githublistapp.presentation.model.response.ResponseSearchUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUserName
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsersRepo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("search/users")
    suspend fun callUsers(
        @QueryMap map: Map<String, String>
    ): ResponseSearchUsers

    @GET("users/{username}")
    suspend fun callUserName(
        @Path("username") userName: String
    ): ResponseUserName

    @GET("users/{username}/repos")
    suspend fun callUsersRepo(
        @Path("username") userName: String,
        @QueryMap map: Map<String, String>
    ): List<ResponseUsersRepo.ResponseUsersRepoItem>
}