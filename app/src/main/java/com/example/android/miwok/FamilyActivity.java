package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#379237")));

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add( new Word(getString(R.string.father),"padre", R.drawable.family_father, R.raw.family_padre) );
        words.add( new Word(getString(R.string.mother),"madre", R.drawable.family_mother, R.raw.family_madre) );
        words.add( new Word(getString(R.string.son),"hijo", R.drawable.family_son, R.raw.family_hijo) );
        words.add( new Word(getString(R.string.daughter),"hija", R.drawable.family_daughter, R.raw.family_hija) );
        words.add( new Word(getString(R.string.older_brother),"hermano mayor", R.drawable.family_older_brother, R.raw.family_hermano_mayor) );
        words.add( new Word(getString(R.string.younger_brother),"hermano más joven", R.drawable.family_younger_brother, R.raw.family_hermano_mas_joven) );
        words.add( new Word(getString(R.string.older_sister),"hermana mayor", R.drawable.family_older_sister, R.raw.family_hermana_mayor) );
        words.add( new Word(getString(R.string.younger_sister),"hermana menor", R.drawable.family_younger_sister, R.raw.family_hermana_menor) );
        words.add( new Word(getString(R.string.grandmother),"abuela", R.drawable.family_grandmother, R.raw.family_abuela) );
        words.add( new Word(getString(R.string.grandfather),"abuelo", R.drawable.family_grandfather, R.raw.family_abuelo) );
        words.add( new Word(getString(R.string.uncle),"tío", R.drawable.uncle, R.raw.family_tio) );
        words.add( new Word(getString(R.string.aunt),"tía", R.drawable.aunt, R.raw.family_tia) );
        words.add( new Word(getString(R.string.nephew),"sobrino", R.drawable.nephew, R.raw.family_sobrino) );
        words.add( new Word(getString(R.string.niece),"sobrina", R.drawable.niece, R.raw.family_sobrina) );


        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_family);

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

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResId());

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
