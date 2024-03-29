package com.ahsanfarooq210.music_player

import android.media.MediaMetadataRetriever
import java.util.concurrent.TimeUnit

data class Music(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String,
    val artUri: String
)

fun formateDuration(duration: Long): String
{
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
    return String.format("%2d:%2d", minutes, seconds)
}

fun getImageArt(path: String): ByteArray?
{
    val reteriever = MediaMetadataRetriever()
    reteriever.setDataSource(path)
    return reteriever.embeddedPicture
}

fun setSongposition(increment: Boolean)
{
    if (!PlayerActivity.repeat)
    {
        if (increment)
        {
            if (PlayerActivity.musicListPA.size - 1 == PlayerActivity.songPosition)
            {
                PlayerActivity.songPosition = 0
            }
            else
            {
                ++PlayerActivity.songPosition
            }
        }
        else
        {
            if (0 == PlayerActivity.songPosition)
            {
                PlayerActivity.songPosition = PlayerActivity.musicListPA.size - 1
            }
            else
            {
                --PlayerActivity.songPosition
            }
        }
    }


}