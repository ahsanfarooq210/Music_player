package com.ahsanfarooq210.music_player

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahsanfarooq210.music_player.databinding.ActivityPlayerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PlayerActivity : AppCompatActivity()
{
    companion object
    {
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0
        var mediaPlayer: MediaPlayer? = null
        var isPlaying: Boolean = false
    }

    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setTheme(R.style.coolPink)
        setContentView(binding.root)

        getIntentData()

        setListinners()

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
            if (mediaPlayer == null)
            {
                mediaPlayer = MediaPlayer()
            }
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
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
        mediaPlayer!!.start()
    }

    private fun pauseMusic()
    {
        binding.playPauseBtn.setIconResource(R.drawable.play_icon)
        isPlaying = false
        mediaPlayer!!.pause()
    }

    override fun onDestroy()
    {
        mediaPlayer!!.stop()
        mediaPlayer = null
        super.onDestroy()
    }

    private fun prevNextSong(increment: Boolean)
    {
        if (increment)
        {
            setSongposition(increment = true)
            setLayout()
            createMediaPlayer()

        }
        else
        {
            setSongposition(increment = false)
            setLayout()
            createMediaPlayer()
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
}