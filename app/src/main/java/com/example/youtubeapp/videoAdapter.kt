package com.example.youtubeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import kotlinx.android.synthetic.main.view_item.view.*

//this class will take 2 parameter 1- video list array 2- youtube player (video itselt)


class videoAdapter (/*1-*/ val videoList: Array<Array<String>>, /*2-*/ val player: YouTubePlayer): RecyclerView.Adapter<videoAdapter.VideoHolder>(){
    class VideoHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    //implemented method:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false))
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val currentTitle=videoList[position][0]
        val currentLink=videoList[position][1]
        holder.itemView.apply {
            btVideo.text=currentTitle
            btVideo.setOnClickListener(){
                player.loadVideo(currentLink,0F)
            }
        }
    }

    override fun getItemCount(): Int= videoList.size


}//end class