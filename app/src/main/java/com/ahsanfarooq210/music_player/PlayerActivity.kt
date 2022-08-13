package com.ahsanfarooq210.music_player

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahsanfarooq210.music_player.databinding.ActivityMainBinding
import com.ahsanfarooq210.music_player.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity()
{
    companion object
    {
        lateinit var musicListPA:ArrayList<Music>
        var songPosition:Int=0
        var mediaPlayer:MediaPlayer?=null
    }
    private lateinit var binding:ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityPlayerBinding.inflate(layoutInflater)
        setTheme(R.style.coolPink)
        setContentView(binding.root)

        getIntentData()
    }

    private fun getIntentData()
    {
        songPosition=intent.getIntExtra("index",0)
        when(intent.getStringExtra("class"))
        {
            "MusicAdapter"->{
                musicListPA= ArrayList()
                musicListPA.addAll(MainActivity.musicMA)
                if(mediaPlayer==null)
                {
                    mediaPlayer= MediaPlayer()
                }
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            }
        }

    }

    override fun onDestroy()
    {
        mediaPlayer!!.stop()
        mediaPlayer=null
        super.onDestroy()
    }
}