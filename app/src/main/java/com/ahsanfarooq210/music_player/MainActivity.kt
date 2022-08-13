package com.ahsanfarooq210.music_player

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahsanfarooq210.music_player.databinding.ActivityMainBinding
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var storagePermission: ActivityResultLauncher<String>
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var adapter: MusicAdapter

    companion object
    {
        lateinit var musicMA:ArrayList<Music>
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestRuntimePermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.coolPinkNav)
        setContentView(binding.root)


        initViews()
        setAdapters()

    }

    private fun setAdapters()
    {
        binding.musicRV.layoutManager = LinearLayoutManager(this)
        musicMA=getAllAudio()
        binding.musicRV.setHasFixedSize(true)
        binding.musicRV.setItemViewCacheSize(13)
        adapter = MusicAdapter(this@MainActivity, musicMA)
        binding.musicRV.adapter = adapter
        binding.totalSongs.text = "Total Songs : ${adapter.itemCount}"

    }

    /**
     * this method is to initialize all the views
     * */
    private fun initViews()
    {
        //for nav drawer
        toggle = ActionBarDrawerToggle(this@MainActivity, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.shuffleBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
            startActivity(intent)
        }
        binding.playlistBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, PlaylistActivity::class.java)
            startActivity(intent)
        }
        binding.favoutireBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, FavouriteActivity::class.java)
            startActivity(intent)
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId)
            {
                R.id.navFeedback -> Toast.makeText(this@MainActivity, "Feedback", Toast.LENGTH_SHORT).show()
                R.id.navSettings -> Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT).show()
                R.id.navAbout    -> Toast.makeText(this@MainActivity, "About", Toast.LENGTH_SHORT).show()
                R.id.navExit     -> exitProcess(1)
            }
            true
        }
    }

    /**
     * for requesting permisssions
     * */
    private fun requestRuntimePermission()
    {
        if (ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this@MainActivity, "denied", Toast.LENGTH_SHORT).show()
            initiatePermissionContract()
            storagePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    /**
     * function to initiate the permission contracts
     * */
    private fun initiatePermissionContract()
    {
        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission())
        {
            if (it)
            {
                Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAllAudio(): ArrayList<Music>
    {
        val tempList = ArrayList<Music>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val projection = arrayOf(MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATE_ADDED, MediaStore.Audio.Media.DATA)
        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, MediaStore.Audio.Media.TITLE, null)
        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    var titleC=cursor.getString(
                        if(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)>=0)
                        {
                            cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                        }
                    else
                        {
                            0
                        }
                    )
                    var idC=cursor.getString(
                        if(cursor.getColumnIndex(MediaStore.Audio.Media._ID)>=0)
                        {
                            cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                        }
                        else
                        {
                            0
                        }
                    )
                    var albumC=cursor.getString(
                        if(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)>=0)
                        {
                            cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                        }
                        else
                        {
                            0
                        }
                    )

                    var artistC=cursor.getString(
                        if(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)>=0)
                        {
                            cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                        }
                        else
                        {
                            0
                        }
                    )
                    var pathC=cursor.getString(
                        if(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)>=0)
                        {
                            cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                        }
                        else
                        {
                            0
                        }
                    )
                    var durationC=cursor.getLong(
                        if(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)>=0)
                        {
                            cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                        }
                        else
                        {
                            0
                        }
                    )

                    val music=Music(idC,titleC,albumC,artistC,durationC,pathC)
                    val file=File(music.path)
                    if(file.exists())
                    {
                        tempList.add(music)
                    }
                }
                while (cursor.moveToNext())
                cursor.close()
            }
        }
        return tempList
    }
}