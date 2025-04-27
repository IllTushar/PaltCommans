package com.example.test.Ui.CreateNewUser

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.R
import com.example.test.RoomDB.UserResponseRoomDB
import com.example.test.Ui.CreateNewUser.Adapter.StoreUserAdapter
import com.example.test.ViewModel.UserViewModel
import com.example.test.databinding.ActivityGetOtherUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetOtherUsers : AppCompatActivity() {
    lateinit var binding: ActivityGetOtherUsersBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetOtherUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.usersFromDb.observe(this) { users ->

            // Example: show the first user's name
            val dataList = mutableListOf<UserResponseRoomDB>()
            if (users.isNotEmpty()) {
                for (user in users) {
                    dataList.add(user)
                }
            }
            val adapter = StoreUserAdapter(this@GetOtherUsers, dataList)
            binding.getRecyclerView.adapter = adapter
        }
    }
}