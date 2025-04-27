package com.example.test.Ui.GetUsers.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.R
import com.example.test.Ui.GetUsers.Model.User
import com.example.test.Ui.Movie.MovieInfo
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter : PagingDataAdapter<User, UsersAdapter.UserViewHolder>(diffCallback) {

    object diffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.firstName)
        private val emailText: TextView = itemView.findViewById(R.id.lastName)
        private val avatarImage: CircleImageView = itemView.findViewById(R.id.profileImage)
        private val layout: ConstraintLayout = itemView.findViewById(R.id.userList)

        fun bind(user: User) {
            nameText.text = "${user.first_name} ${user.last_name}"
            emailText.text = user.email
            Glide.with(avatarImage.context)
                .load(user.avatar)
                .into(avatarImage)
            layout.setOnClickListener {
                val intent = Intent(itemView.context, MovieInfo::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_single_row_xml, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

