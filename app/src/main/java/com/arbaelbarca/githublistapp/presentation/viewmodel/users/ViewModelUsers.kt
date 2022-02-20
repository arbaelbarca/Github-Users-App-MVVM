package com.arbaelbarca.githublistapp.presentation.viewmodel.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseSearchUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUserName
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsers
import com.arbaelbarca.githublistapp.presentation.model.response.ResponseUsersRepo
import com.arbaelbarca.githublistapp.presentation.repository.users.RepositoryUsers
import com.arbaelbarca.githublistapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelUsers @Inject constructor(val repositoryHome: RepositoryUsers) : ViewModel() {

    val stateGetUsers = MutableLiveData<UiState<ResponseSearchUsers>>()
    val stateGetUsersOnNext = MutableLiveData<UiState<ResponseSearchUsers>>()

    val stateGetUserName = MutableLiveData<UiState<ResponseUserName>>()
    val stateGetUsersRepo =
        MutableLiveData<UiState<List<ResponseUsersRepo.ResponseUsersRepoItem>>>()

    fun observerGetUsers() = stateGetUsers
    fun observerGetUsersOnNext() = stateGetUsersOnNext
    fun observerGetUsersRepo() = stateGetUsersRepo
    fun observerGetUsersName() = stateGetUserName

    fun getListUsers(map: Map<String, String>) {
        stateGetUsers.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                repositoryHome.callApiUsers(map)
            }.onSuccess {
                stateGetUsers.value = UiState.Success(it)
            }.onFailure {
                stateGetUsers.value = UiState.Failure(it)
            }
        }
    }

    fun getListUsersOnNext(map: Map<String, String>) {
        stateGetUsersOnNext.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                repositoryHome.callApiUsers(map)
            }.onSuccess {
                stateGetUsersOnNext.value = UiState.Success(it)
            }.onFailure {
                stateGetUsersOnNext.value = UiState.Failure(it)
            }
        }
    }

    fun getDetailUsername(userName: String) {
        stateGetUserName.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                repositoryHome.callApiUserName(userName)
            }.onSuccess {
                stateGetUserName.value = UiState.Success(it)
            }.onFailure {
                stateGetUserName.value = UiState.Failure(it)
            }
        }
    }

    fun getDetailUersRepo(
        userName: String,
        map: Map<String, String>
    ) {
        stateGetUsersRepo.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                repositoryHome.callApiUsersRepo(userName, map)
            }.onSuccess {
                stateGetUsersRepo.value = UiState.Success(it)
            }.onFailure {
                stateGetUsersRepo.value = UiState.Failure(it)
            }
        }
    }

}