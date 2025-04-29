package com.example.test.Ui.GetUsers

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.Ui.CreateNewUser.GetOtherUsers
import com.example.test.Ui.CreateNewUser.Request.RequestUserInfo
import com.example.test.Ui.GetUsers.Adapter.UsersAdapter
import com.example.test.Ui.GetUsers.PagingSource.LoadingState.LoadStateFooterAdapter
import com.example.test.ViewModel.UserViewModel
import com.example.test.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: UsersAdapter
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar(binding)
        setSupportActionBar(binding.toolbar.root)

        // Initialize the adapter Get UserInfo Adapter
        adapter = UsersAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateFooterAdapter { adapter.retry() },
            footer = LoadStateFooterAdapter { adapter.retry() }
        )

        viewModel.isOffline.observe(this) {
            if (it) {
                binding.offlineMode.root.visibility = View.VISIBLE
            } else {
                binding.offlineMode.root.visibility = View.GONE
            }
        }
        viewModel.usersLiveData.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        binding.addMember.setOnClickListener {
            val dialog = Dialog(this@MainActivity, R.style.CustomDialogStyle)
            dialog.setContentView(R.layout.create_new_users)
            dialog.show()
            dialog.findViewById<Button>(R.id.saveJobs).setOnClickListener {
                val txtName = dialog.findViewById<TextView>(R.id.name).text.toString()
                val txtJob = dialog.findViewById<TextView>(R.id.job).text.toString()
                if (!txtName.isEmpty() || !txtJob.isEmpty()) {
                    viewModel.response.observe(this) {
                        Toast.makeText(this, "Success: ${it.name}", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }

                    viewModel.error.observe(this) {
                        Toast.makeText(this, "Failed: $it", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    val request = RequestUserInfo(txtName, txtJob)
                    viewModel.sendPostData(request)
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }

            }

            dialog.findViewById<Button>(R.id.getOtherStored).setOnClickListener {
                val intent = Intent(this@MainActivity, GetOtherUsers::class.java)
                startActivity(intent)
            }
        }
    }
}

private fun setUpToolbar(binding: ActivityMainBinding) {
    binding.toolbar.title.text = "Main-Page"
}

