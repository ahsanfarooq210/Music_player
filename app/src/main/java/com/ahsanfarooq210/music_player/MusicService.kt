package com.ahsanfarooq210.music_player

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.session.MediaSession
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat

class MusicService : Service()
{
    private val myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var runnable: Runnable

    override fun onBind(p0: Intent?): IBinder
    {
        mediaSession = MediaSessionCompat(baseContext, "My Music session")
        return myBinder
    }


    inner class MyBinder : Binder()
    {
        fun currentService(): MusicService
        {
            return this@MusicService
        }
    }

    fun showNotification(playPauseButton: Int)
    {
        val prevIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.previous)
        val prevPendingIntent = PendingIntent.getBroadcast(baseContext, 0, prevIntent, PendingIntent.FLAG_IMMUTABLE)

        val playIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(baseContext, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)

        val nextIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(baseContext, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)

        val exitIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.EXIT)
        val exitPendingIntent = PendingIntent.getBroadcast(baseContext, 0, exitIntent, PendingIntent.FLAG_IMMUTABLE)

        val imageArt = getImageArt(PlayerActivity.musicListPA[PlayerActivity.songPosition].path)
        val image = if (imageArt != null)
        {
            BitmapFactory.decodeByteArray(imageArt, 0, imageArt.size)
        }
        else
        {
            BitmapFactory.decodeResource(resources, R.drawable.splash_screen)
        }

        //make the notification
        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID).setContentTitle(PlayerActivity.musicListPA[PlayerActivity.songPosition].title)
            .setContentText(PlayerActivity.musicListPA[PlayerActivity.songPosition].artist)
            .setSmallIcon(R.drawable.music_icon)
            .setLargeIcon(image)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.previous_icon, "Previous", prevPendingIntent)
            .addAction(playPauseButton, "Play", playPendingIntent)
            .addAction(R.drawable.next_icon, "Next", nextPendingIntent)
            .addAction(R.drawable.exit_icon, "Exit", exitPendingIntent)
            .build()

        //show the notification
        startForeground(1, notification)
        // (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).notify(1,notification)

    }

    fun createMediaPlayer()
    {
        try
        {
            if (PlayerActivity.musicService!!.mediaPlayer == null)
            {
                PlayerActivity.musicService!!.mediaPlayer = MediaPlayer()
            }
            PlayerActivity.musicService!!.mediaPlayer!!.reset()
            PlayerActivity.musicService!!.mediaPlayer!!.setDataSource(PlayerActivity.musicListPA[PlayerActivity.songPosition].path)
            PlayerActivity.musicService!!.mediaPlayer!!.prepare()
            PlayerActivity.binding.playPauseBtn.setIconResource(R.drawable.pause_icon)
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon)
            PlayerActivity.binding.tvSeekbarStart.text = formateDuration(mediaPlayer!!.currentPosition.toLong())
            PlayerActivity.binding.tvSeekBarEnd.text = formateDuration(mediaPlayer!!.duration.toLong())
            PlayerActivity.binding.seekBarPA.progress = 0
            PlayerActivity.binding.seekBarPA.max = mediaPlayer!!.duration
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return
        }
    }

    fun seekBarSetup()
    {
        runnable= Runnable {
            PlayerActivity.binding.tvSeekbarStart.text = formateDuration(mediaPlayer!!.currentPosition.toLong())
            PlayerActivity.binding.seekBarPA.progress=mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable,200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable,0)

    }

}