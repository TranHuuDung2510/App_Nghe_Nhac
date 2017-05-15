package com.example.truon.gametonho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by truon on 3/7/2017.
 */

public class Play_Song extends Activity {
    private double thoiGianDau = 0;
    private double thoiGianDung = 0;
    Random rd;
    ArrayList<Song> listSong = new ArrayList<Song>();
    private static int loadseekBar;
    SeekBar seekBar;
    int postion = 1;
    TextView time1, time2;
    ImageButton btnPlay, btnLui, btnTien;
    ImageButton btnRepeat;
    ImageButton btnShuffle;
    private Handler handler = new Handler();
    private TextView songTitleLabel, songArst;
    private boolean isRepeat = false;
    private boolean isShuffle = false;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    ImageView img;
    AnimationDrawable animationDrawable;
    Song song;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        addControl();

        Intent intent = getIntent();

        postion = intent.getExtras().getInt("pos");
        listSong = intent.getParcelableArrayListExtra("ds");

        if (listSong.get(postion) != null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(listSong.get(postion).getArtistID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.start();
        img = (ImageView) findViewById(R.id.img);
        img.setBackgroundResource(R.drawable.hieuung);
        animationDrawable = (AnimationDrawable) img.getBackground();
        animationDrawable.start();
        songTitleLabel.setText(listSong.get(postion).getTitle());
        songArst.setText(listSong.get(postion).getArtist());


        addEvent();
        Untilities();
        do {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();

                    if (isShuffle) {
                        rd = new Random();
                        postion = rd.nextInt(listSong.size());
                    }

                    if (isRepeat) {
                        --postion;
                    }
                    postion++;
                    try {
                        mediaPlayer.setDataSource(listSong.get(postion).getArtistID());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    songTitleLabel.setText(listSong.get(postion).getTitle());
                    songArst.setText(listSong.get(postion).getArtist());
                    mediaPlayer.start();
                    Untilities();
                }
            });
        } while (postion < listSong.size());
    }


    private void addEvent() {
        songTitleLabel.setText(listSong.get(postion).getTitle());

        btnLui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double thoigian = thoiGianDau - 3000;
                mediaPlayer.seekTo((int) thoigian);
            }
        });
        btnTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double thoigian = thoiGianDau + 3000;
                mediaPlayer.seekTo((int) thoigian);
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        btnPlay.setImageResource(R.drawable.av_play);
                        mediaPlayer.pause();

                    } else {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(listSong.get(postion).getArtistID());
                        mediaPlayer.prepare();
                        btnPlay.setImageResource(R.drawable.av_pause);
                        mediaPlayer.start();
                    }
                    // Displaying Song title
                    songTitleLabel.setText(listSong.get(postion).getTitle());
                    songArst.setText(listSong.get(postion).getArtist());
                    // Changing Button Image to pause image

                    // set Progress bar values
                    seekBar.setProgress(0);
                    seekBar.setMax(100);
                    Untilities();
                    updateProgressBar();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(listSong.get(postion + 1).getArtistID());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                songTitleLabel.setText(listSong.get(postion).getTitle());
                songArst.setText(listSong.get(postion).getArtist());
                postion++;
                mediaPlayer.start();
                Untilities();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(listSong.get(postion - 1).getArtistID());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                songTitleLabel.setText(listSong.get(postion - 1).getTitle());
                songArst.setText(listSong.get(postion - 1).getArtist());

                postion--;
                mediaPlayer.start();
                Untilities();
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepeat == false) {
                    isRepeat = true;
                    btnRepeat.setImageResource(R.drawable.repeat_active);
                }
                if (isRepeat == true) {
                    isRepeat = false;
                    btnRepeat.setImageResource(R.drawable.av_repeat);
                }

            }
        });
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffle == false) {
                    isShuffle = true;
                    btnShuffle.setImageResource(R.drawable.shuffle_active);
                }
                if (isShuffle == true) {
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.av_shuffle);
                }
            }
        });
    }

    private void Untilities() {
        thoiGianDau = mediaPlayer.getCurrentPosition();
        thoiGianDung = mediaPlayer.getDuration();
        Toast.makeText(getApplicationContext(), String.valueOf(thoiGianDau), Toast.LENGTH_LONG).show();
        if (loadseekBar == 0)
            seekBar.setMax((int) thoiGianDung);
        seekBar.setProgress((int) thoiGianDau);
        int seconds1 = (int) ((thoiGianDau / 1000) % 60);
        int minutes1 = (int) ((thoiGianDau / 1000) / 60);
        time1.setText(String.format("%d phút  %d giây", minutes1, seconds1));
        int seconds2 = (int) ((thoiGianDung / 1000) % 60);
        int minutes2 = (int) ((thoiGianDung / 1000) / 60);
        time2.setText(String.format("%d phút  %d giây", minutes2, seconds2));
        updateProgressBar();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void updateProgressBar() {
        handler.postDelayed(updateTime, 100);
    }

    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            thoiGianDau = mediaPlayer.getCurrentPosition();
            int seconds1 = (int) ((thoiGianDau / 1000) % 60);
            int minutes1 = (int) ((thoiGianDau / 1000) / 60);
            time1.setText(String.format("%d phút  %d giây", minutes1, seconds1));
            handler.postDelayed(this, 100);
            seekBar.setProgress((int) thoiGianDau);
        }
    };

    private void addControl() {
        seekBar = (SeekBar) findViewById(R.id.songProgressBar);
        time1 = (TextView) findViewById(R.id.songCurrentDurationLabel);
        time2 = (TextView) findViewById(R.id.songTotalDurationLabel);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnLui = (ImageButton) findViewById(R.id.action0);
        btnTien = (ImageButton) findViewById(R.id.action1);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songArst = (TextView) findViewById(R.id.songArst);
    }
}
