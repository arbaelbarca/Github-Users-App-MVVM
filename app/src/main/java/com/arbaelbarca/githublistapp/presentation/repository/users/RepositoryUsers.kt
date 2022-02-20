package com.arbaelbarca.githublistapp.presentation.repository.users

import com.arbaelbarca.githublistapp.data.api.ApiService
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseSearchUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUserName
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsersRepo
import javax.inject.Inject

class RepositoryUsers @Inject constructor(val apiService: ApiService) {
    suspend fun callApiUsers(map: Map<String, String>): ResponseSearchUsers {
        return apiService.callUsers(map)
    }

    suspend fun callApiUserName(username: String): ResponseUserName {
        return apiService.callUserName(username)
    }

    suspend fun callApiUsersRepo(
        username: String,
        map: Map<String, String>
    ): List<ResponseUsersRepo.ResponseUsersRepoItem> {
        return apiService.callUsersRepo(username, map)
    }
}