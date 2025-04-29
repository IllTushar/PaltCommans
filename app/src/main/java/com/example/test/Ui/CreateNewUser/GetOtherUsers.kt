package com.example.test.Ui.CreateNewUser

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        setupToolbar(binding)
        setSupportActionBar(binding.usersToolbar.root)

        viewModel.usersFromDb.observe(this) { users ->

            // Example: show the first user's name
            val dataList = mutableListOf<UserResponseRoomDB>()
            if (users.isNotEmpty()) {
                binding.emptyLayout.root.visibility = android.view.View.GONE
                binding.getRecyclerView.visibility = android.view.View.VISIBLE
                for (user in users) {
                    dataList.add(user)
                }
            } else {
                binding.getRecyclerView.visibility = android.view.View.GONE
                binding.emptyLayout.root.visibility = android.view.View.VISIBLE
                Toast.makeText(this@GetOtherUsers, "No User Available", Toast.LENGTH_SHORT).show()
            }
            val adapter = StoreUserAdapter(this@GetOtherUsers, dataList)
            binding.getRecyclerView.adapter = adapter
        }
    }

    private fun setupToolbar(binding: ActivityGetOtherUsersBinding) {
        binding.usersToolbar.title.text = "Offline Users"
    }
}