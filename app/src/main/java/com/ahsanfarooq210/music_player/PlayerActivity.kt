package com.ahsanfarooq210.music_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahsanfarooq210.music_player.databinding.ActivityMainBinding
import com.ahsanfarooq210.music_player.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityPlayerBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Music_player)
        setContentView(binding.root)
    }
}