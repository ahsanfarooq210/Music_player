package com.ahsanfarooq210.music_player

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahsanfarooq210.music_player.databinding.ActivityPlayerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PlayerActivity : AppCompatActivity(),ServiceConnection
{
    companion object
    {
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0

        var isPlaying: Boolean = false
        var musicService:MusicService?=null
    }

    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setTheme(R.style.coolPink)
        setContentView(binding.root)
        startMusicService()
        getIntentData()

        setListinners()

    }

    private fun startMusicService()
    {
        val intent=Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)
    }

    private fun setListinners()
    {
        binding.playPauseBtn.setOnClickListener {
            if (isPlaying)
            {
                pauseMusic()
            }
            else
            {
                playMusic()
            }
        }

        binding.previousBtnPA.setOnClickListener {
            prevNextSong(increment = false)
        }
        binding.nextBtnPA.setOnClickListener {
            prevNextSong(increment = true)
        }
    }

    private fun getIntentData()
    {
        songPosition = intent.getIntExtra("index", 0)
        when (intent.getStringExtra("class"))
        {
            "MusicAdapter" ->
            {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.musicMA)
                setLayout()
                createMediaPlayer()
            }
            "MainActivity"->{
                musicListPA= ArrayList()
                musicListPA.addAll(MainActivity.musicMA)
                musicListPA.shuffle()
                setLayout()
                createMediaPlayer()
            }
        }

    }

    private fun setLayout()
    {
        Glide.with(this).load(musicListPA[songPosition].artUri).apply(RequestOptions().placeholder(R.mipmap.music_player_icon_round).centerCrop()).into(binding.songImgPA)
        binding.songNamePA.text = musicListPA[songPosition].title
    }

    private fun createMediaPlayer()
    {
        try
        {
            if (musicService!!.mediaPlayer == null)
            {
                musicService!!.mediaPlayer = MediaPlayer()
            }
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isPlaying = true
            binding.playPauseBtn.setIconResource(R.drawable.pause_icon)
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return
        }
    }

    private fun playMusic()
    {
        binding.playPauseBtn.setIconResource(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic()
    {
        binding.playPauseBtn.setIconResource(R.drawable.play_icon)
        isPlaying = false
        musicService!!.mediaPlayer!!.pause()
    }

    override fun onDestroy()
    {
        musicService!!.mediaPlayer!!.stop()
        musicService!!.mediaPlayer = null
        super.onDestroy()
    }

    private fun prevNextSong(increment: Boolean)
    {
        if (increment)
        {
            setSongposition(increment = true)
            setLayout()


        }
        else
        {
            setSongposition(increment = false)
            setLayout()

        }
    }

    private fun setSongposition(increment: Boolean)
    {
        if(increment)
        {
            if(musicListPA.size-1== songPosition)
            {
                songPosition=0
            }
            else
            {
                ++songPosition
            }
        }
        else
        {
            if(0== songPosition)
            {
                songPosition= musicListPA.size-1
            }
            else
            {
                --songPosition
            }
        }
    }

    override fun onServiceConnected(p0: ComponentName?, service: IBinder?)
    {
        val binder=service as MusicService.MyBinder
        musicService=binder.currentSerivce()
        createMediaPlayer()
        musicService!!.showNotification()
    }

    override fun onServiceDisconnected(p0: ComponentName?)
    {
        musicService=null
    }
}