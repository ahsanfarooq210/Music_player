package com.ahsanfarooq210.music_player

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahsanfarooq210.music_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Music_player)
        setContentView(binding.root)


        initViews()

    }

    /**
     * this method is to initialize all the views
     * */
    private fun initViews()
    {
        binding.shuffleBtn.setOnClickListener {
            val intent= Intent(this@MainActivity,PlayerActivity::class.java)
            startActivity(intent)
        }
        binding.playlistBtn.setOnClickListener {
            val intent= Intent(this@MainActivity,PlaylistActivity::class.java)
            startActivity(intent)
        }
        binding.favoutireBtn.setOnClickListener {
            val intent= Intent(this@MainActivity,FavouriteActivity::class.java)
            startActivity(intent)
        }
    }
}