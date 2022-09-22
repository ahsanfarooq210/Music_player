package com.ahsanfarooq210.music_player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        when(intent?.action)
        {
            ApplicationClass.previous->{
                prevNextSong(false,context!!)
            }

            ApplicationClass.NEXT->{
                prevNextSong(true,context!!)
            }

            ApplicationClass.PLAY->{
                if(PlayerActivity.isPlaying)
                {
                    pauseMusic()
                }
                else
                {
                    playMusic()
                }
            }
            ApplicationClass.EXIT->{
                PlayerActivity.musicService!!.stopForeground(true)
                PlayerActivity.musicService=null
                exitProcess(1)
            }
        }

    }

    private fun playMusic()
    {
        PlayerActivity.isPlaying=true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon)
        PlayerActivity.binding.playPauseBtn.setIconResource(R.drawable.pause_icon)
    }

    private fun pauseMusic()
    {
        PlayerActivity.isPlaying=false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        PlayerActivity.musicService!!.showNotification(R.drawable.play_icon)
        PlayerActivity.binding.playPauseBtn.setIconResource(R.drawable.play_icon )
    }

    private fun prevNextSong(increment:Boolean,context: Context)
    {
        setSongposition(increment=increment)
        PlayerActivity.musicService!!.createMediaPlayer()
        Glide.with(context).load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri).apply(RequestOptions().placeholder(R.mipmap.music_player_icon_round).centerCrop()).into(PlayerActivity.binding.songImgPA)
        PlayerActivity.binding.songNamePA.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
        playMusic()

    }
}