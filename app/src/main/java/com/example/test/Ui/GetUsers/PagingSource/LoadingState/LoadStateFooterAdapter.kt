package com.example.test.Ui.GetUsers.PagingSource.LoadingState

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R

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