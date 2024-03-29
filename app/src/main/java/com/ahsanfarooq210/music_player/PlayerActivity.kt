package com.ahsanfarooq210.music_player

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ahsanfarooq210.music_player.databinding.ActivityPlayerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PlayerActivity : AppCompatActivity(),ServiceConnection,MediaPlayer.OnCompletionListener
{

    companion object
    {
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0

        var isPlaying: Boolean = false
        var musicService:MusicService?=null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPlayerBinding
        var repeat:Boolean=false
    }


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

        binding.seekBarPA.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                if(fromUser)
                {
                    musicService!!.mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?)= Unit


            override fun onStopTrackingTouch(p0: SeekBar?)= Unit


        })

        binding.btnRepeatPA.setOnClickListener{
            if(!repeat)
            {
                repeat=true
                binding.btnRepeatPA.setColorFilter(ContextCompat.getColor(this@PlayerActivity,R.color.purple_500))
            }
            else
            {
                repeat=false
                binding.btnRepeatPA.setColorFilter(ContextCompat.getColor(this@PlayerActivity,R.color.color_pink))
            }
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
        if(repeat)
        {
            binding.btnRepeatPA.setColorFilter(ContextCompat.getColor(this@PlayerActivity,R.color.purple_500))
        }
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
            musicService!!.showNotification(R.drawable.pause_icon)
            binding.tvSeekbarStart.text= formateDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
            binding.tvSeekBarEnd.text= formateDuration(musicService!!.mediaPlayer!!.duration.toLong())
            binding.seekBarPA.progress=0
            binding.seekBarPA.max= musicService!!.mediaPlayer!!.duration
            musicService!!.mediaPlayer!!.setOnCompletionListener ( this )
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
        musicService!!.showNotification(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic()
    {
        binding.playPauseBtn.setIconResource(R.drawable.play_icon)
        musicService!!.showNotification(R.drawable.play_icon)
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
            createMediaPlayer()

        }
        else
        {
            setSongposition(increment = false)
            setLayout()
            createMediaPlayer()
        }
    }



    override fun onServiceConnected(p0: ComponentName?, service: IBinder?)
    {
        val binder=service as MusicService.MyBinder
        musicService=binder.currentService()
        createMediaPlayer()
        musicService!!.seekBarSetup()
    }

    override fun onServiceDisconnected(p0: ComponentName?)
    {
        musicService=null
    }

    override fun onCompletion(p0: MediaPlayer?)
    {
        prevNextSong(true)
    }
}