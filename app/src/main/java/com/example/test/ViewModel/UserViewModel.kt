package com.example.test.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.test.Network.NetworkAvailblity
import com.example.test.Network.NetworkStatus
import com.example.test.Repo.Repository
import com.example.test.RoomDB.UserResponseRoomDB
import com.example.test.Ui.CreateNewUser.Request.RequestUserInfo
import com.example.test.Ui.CreateNewUser.Response.CreateUserResponse
import com.example.test.Ui.GetUsers.Model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: Repository,
    private val networkObserver: NetworkAvailblity,
) : ViewModel() {

    private val _isOffline = MutableLiveData<Boolean>()
    val isOffline: LiveData<Boolean> = _isOffline

    init {
        viewModelScope.launch {
            networkObserver.observe().collect { status ->
                _isOffline.postValue(status == NetworkStatus.Lost)
            }
        }
    }


    private val _response = MutableLiveData<CreateUserResponse>()
    val response: LiveData<CreateUserResponse> = _response

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val usersLiveData: LiveData<PagingData<User>> = repo.getUsers()
        .flow
        .cachedIn(viewModelScope)
        .asLiveData()


    fun sendPostData(request: RequestUserInfo) {
        viewModelScope.launch {
            try {
                // Call repository method within coroutine scope
                val response = repo.postData(request)

                // Process the result
                if (response.isSuccessful) {
                    _response.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }

    val usersFromDb: LiveData<List<UserResponseRoomDB>> = repo.getUsersFromDb()


    val moviePagingFlow = repo.getMovies().flow.cachedIn(viewModelScope)
}