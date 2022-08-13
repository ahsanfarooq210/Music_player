package com.ahsanfarooq210.music_player

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahsanfarooq210.music_player.databinding.MuicViewBinding

class MusicAdapter(private val context: Context,private val musicList:ArrayList<Music>):RecyclerView.Adapter<MusicAdapter.ViewHolder>()
{
    class  ViewHolder( val binding:MuicViewBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
       return ViewHolder(MuicViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val data=musicList[position]
        holder.binding.apply {
            this.songNameMV.text=data.title
            this.songAlbumMV.text=data.album
            this.songDuration.text=data.duration.toString()
        }
    }

    override fun getItemCount(): Int
    {
        return musicList.size
    }


}