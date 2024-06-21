package com.meone.montir.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.meone.montir.databinding.ListMusicBinding
import com.meone.montir.response.DataItemMusic

class MusicAdapter(private val onItemClick: (DataItemMusic) -> Unit) : androidx.recyclerview.widget.ListAdapter<DataItemMusic, MusicAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val music = getItem(position)
        holder.bind(music)

        holder.itemView.setOnClickListener{
            onItemClick(music)
        }
    }
    class MyViewHolder(val binding: ListMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(music: DataItemMusic){
            binding.tvTitleMusic.text = music.musicName
            binding.tvDurationMusic.text = music.duration
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemMusic>() {
            override fun areItemsTheSame(oldItem: DataItemMusic, newItem: DataItemMusic): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataItemMusic, newItem: DataItemMusic): Boolean {
                return oldItem == newItem
            }
        }
    }
}