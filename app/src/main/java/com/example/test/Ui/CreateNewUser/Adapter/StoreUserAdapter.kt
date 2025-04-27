package com.example.test.Ui.CreateNewUser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.RoomDB.UserResponseRoomDB

class StoreUserAdapter(
    private val context: Context,
    private val dataList: List<UserResponseRoomDB>,
) : RecyclerView.Adapter<StoreUserAdapter.myViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): myViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_row_xml, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: myViewHolder,
        position: Int,
    ) {
        holder.name.text = dataList[position].name
        holder.job.text = dataList[position].job
        holder.id.text = dataList[position].id
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.room_name)
        val job = itemView.findViewById<TextView>(R.id.room_job)
        val id = itemView.findViewById<TextView>(R.id.room_id)
    }
}