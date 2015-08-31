package com.example.edward.std0;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class player extends Activity {
    int i = 0;
    int repeat = 0;
    boolean c = false;
    boolean isPaused=false,isChanging=false;
    private SeekBar seekbar;

    MediaPlayer mediaPlayer;
    SeekBarChangeEvent seekBarChangeEvent;
    Backend backend = new Backend();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        backend.LoadMusicList(this);
        music_list(null, backend.musicList);


        mediaPlayer = new MediaPlayer();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            mediaPlayer.setDataSource( (String)(backend.musicList.get(i).get("url")));
            mediaPlayer.prepare();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            Timer timer = new Timer();
            Task task = new Task();
            timer.schedule(task, 0, 1000);
            seekbar = (SeekBar) findViewById(R.id.seekBar);
            seekBarChangeEvent = new SeekBarChangeEvent();
            seekbar.setOnSeekBarChangeListener(seekBarChangeEvent);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onStartTrackingTouch(SeekBar arg0) {//开始拖动进度条
            // TODO Auto-generated method stub
            isChanging=true;
        }
        @Override
            public void onStopTrackingTouch(SeekBar arg0) {//停止拖动进度条
            // TODO Auto-generated method stub
            mediaPlayer.seekTo(arg0.getProgress());//将media进度设置为当前seekbar的进度
            isChanging=false;
        }
    }

    public void OnClk_btn_back(View v){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    public void OnClk_btn_play(final View v) {
        if(mediaPlayer.isPlaying()) {
            ((ImageButton) v).setBackgroundResource(R.drawable.play);
            mediaPlayer.pause();
        }
        else {
            ((ImageButton) v).setBackgroundResource(R.drawable.pause);
            mediaPlayer.start();
            textview_PlayingSong(null);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    try {
                        if(repeat == 0)
                            OnClk_btn_next(v);    //  在每次播放完成之后都用播放下一首
                        else if(repeat == 1) {
                            mediaPlayer.seekTo(0);
                            mediaPlayer.start();
                        }
                        else if(repeat == 2) {
                            i = random.nextInt(backend.musicList.size() - 1);
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource((String) (backend.musicList.get(i).get("url")));
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            textview_PlayingSong(null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void OnClk_btn_next(View v) {

        i++;
        if(i==backend.musicList.size())i = 0;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource((String) (backend.musicList.get(i).get("url")));
            mediaPlayer.prepare();
            mediaPlayer.start();
            textview_PlayingSong(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void OnClk_btn_last(View v){
        i--;
        if(i < 0)i=backend.musicList.size() - 1;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource((String) (backend.musicList.get(i).get("url")));
            mediaPlayer.prepare();
            mediaPlayer.start();
            textview_PlayingSong(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class Task extends TimerTask {
        public void run() {
            seekbar.setProgress(mediaPlayer.getCurrentPosition());
            seekbar.setMax(mediaPlayer.getDuration());
        }
    }

    public void music_list(final View v, ArrayList<HashMap<String, Object>> a){
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
        ListView list_view;
        list_view = (ListView)findViewById(R.id.list);
        for(int j = 0;j < a.size(); j++){
            Map<String, String> item = new HashMap<String, String>();
            item.put("title",(String)a.get(j).get("name"));
            item.put("artist",(String)a.get(j).get("artist"));
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,
                new String[]{"title","artist"},new int[]{android.R.id.text1,android.R.id.text2});
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource((String) (backend.musicList.get(position).get("url")));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    i=position;
                    textview_PlayingSong(null);
                    ((ImageButton)findViewById(R.id.btn_play)).setBackgroundResource(R.drawable.pause);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            try {
                                if(repeat == 0)
                                    OnClk_btn_next(v);    //  在每次播放完成之后都用播放下一首
                                else if(repeat == 1) {
                                    mediaPlayer.seekTo(0);
                                    mediaPlayer.start();
                                }
                                else if(repeat == 2) {
                                    i = random.nextInt(backend.musicList.size() - 1);
                                    mediaPlayer.reset();
                                    mediaPlayer.setDataSource((String) (backend.musicList.get(i).get("url")));
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                    textview_PlayingSong(null);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                catch (IOException ex)
                {

                }
            }
        });

    }
    public void OnClk_btn_repeat(View v){
        repeat++;
        if(repeat == 0) {
            ((ImageButton) v).setBackgroundResource(R.drawable.repeat);
        }
        if(repeat == 1){
            ((ImageButton) v).setBackgroundResource(R.drawable.repeat_one);
        }
        if(repeat == 2){
            ((ImageButton) v).setBackgroundResource(R.drawable.shuffle);
        }
        if(repeat == 3)repeat = 0;
    }

    public void textview_PlayingSong(View v){
        //mediaPlayer.setDataSource((String) (backend.musicList.get(position).get("url")));
        //((ImageButton)findViewById(R.id.btn_play)).setBackgroundResource(R.drawable.pause);
        ((TextView)findViewById(R.id.playing_song)).setText((String)(backend.musicList.get(i).get("name")));
        ((TextView)findViewById(R.id.artist)).setText((String)(backend.musicList.get(i).get("artist")));
    }

}
