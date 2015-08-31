package com.example.edward.std0;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Backend {
    public ArrayList<HashMap<String, Object>> musicList;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private int playingIndex = -1;

    public Backend() {
        musicList = new ArrayList<HashMap<String, Object>>();
        mediaPlayer = new MediaPlayer();
    }

    public void LoadMusicList(Context context) {
        musicList.clear();

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, Object> song = new HashMap<String, Object>();
                song.put("title", cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))));
                song.put("name", cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))));
                song.put("artist", cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))));
                song.put("duration", cursor.getLong((cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))));
                song.put("url", cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.DATA))));

                if (cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)) != 0) {
                    musicList.add(song);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    public void Play() {
        mediaPlayer.start();
        isPlaying = true;
    }

    public void Pause() {
        mediaPlayer.pause();
        isPlaying = false;
    }

    public void Load(int idx) {
        if (idx < 0 || idx >= musicList.size()) return;
        try {
            mediaPlayer.setDataSource((String)musicList.get(idx).get("url"));
            mediaPlayer.prepare();
            isPlaying = false;
            playingIndex = idx;
        }
        catch (IOException ex) { }
    }
}
