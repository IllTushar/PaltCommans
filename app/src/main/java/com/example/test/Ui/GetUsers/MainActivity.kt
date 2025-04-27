package com.example.test.Ui.GetUsers

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.Ui.CreateNewUser.GetOtherUsers
import com.example.test.Ui.CreateNewUser.Request.RequestUserInfo
import com.example.test.Ui.GetUsers.Adapter.UsersAdapter
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

// Custom LoadStateAdapter implementation
class LoadStateFooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateFooterAdapter.LoadStateViewHolder>() {


    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState,
    ) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
        return LoadStateViewHolder(view)
    }

    class LoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        private val retryButton: Button = view.findViewById(R.id.retryButton)
        private val errorMsg: TextView = view.findViewById(R.id.errorMsg)
        fun bind(loadState: LoadState, retry: () -> Unit) {
            retryButton.setOnClickListener { retry() }

            // Show the relevant views based on the load state
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
                    ?: "Unknown error occurred"
            }
        }
    }
}