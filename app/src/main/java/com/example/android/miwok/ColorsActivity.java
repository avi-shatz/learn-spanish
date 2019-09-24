package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener onCompletionListener =
            new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8800A0")));

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(getString(R.string.red), "rojo", R.drawable.color_red, R.raw.color_rojo));
        words.add(new Word(getString(R.string.green), "verde", R.drawable.color_green, R.raw.color_verde));
        words.add(new Word(getString(R.string.brown), "marrón", R.drawable.color_brown, R.raw.color_marron));
        words.add(new Word(getString(R.string.gray), "gris", R.drawable.color_gray, R.raw.color_gris));
        words.add(new Word(getString(R.string.black), "negro", R.drawable.color_black, R.raw.color_negro));
        words.add(new Word(getString(R.string.white), "blanco", R.drawable.brightness_28dp, R.raw.color_blanco));
        words.add(new Word(getString(R.string.yellow), "amarillo", R.drawable.color_yellow, R.raw.color_amarillo));
        words.add(new Word(getString(R.string.purple), "púrpura", R.drawable.color_purple, R.raw.color_purpura));
        words.add(new Word(getString(R.string.orange), "naranja", R.drawable.color_orange, R.raw.color_naranja));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.wordList);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);

                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResId());

                    // Start the audio file
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}



