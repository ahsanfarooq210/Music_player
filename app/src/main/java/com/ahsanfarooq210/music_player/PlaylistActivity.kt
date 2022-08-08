package com.ahsanfarooq210.music_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahsanfarooq210.music_player.databinding.ActivityPlayerBinding
import com.ahsanfarooq210.music_player.databinding.ActivityPlaylistBinding

class PlaylistActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityPlaylistBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding=ActivityPlaylistBinding.inflate(layoutInflater)
        setTheme(R.style.coolPink)
        setContentView(binding.root)
    }
}