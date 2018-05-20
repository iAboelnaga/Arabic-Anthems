package com.example.android;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class Anthem extends AppCompatActivity{

    // media player object
    private MediaPlayer mMediaPlayer;

    // Handles audio focus while playing sound file
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // it means we have gained focused and start playback
                mMediaPlayer.start();
            }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // it means we have completely lost the focus and we
                // have to stop the playback and free up the playback resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create and setup link to get audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(getString(R.string.egypt_anthem),R.drawable.egypt,R.raw.egypt));
        words.add(new Word(getString(R.string.algeria_anthem),R.drawable.algeria,R.raw.algeria));
        words.add(new Word(getString(R.string.sudan_anthem),R.drawable.sudan,R.raw.sudan));
        words.add(new Word(getString(R.string.iraq_anthem),R.drawable.iraq,R.raw.iraq));
        words.add(new Word(getString(R.string.morocco_anthem),R.drawable.morocco,R.raw.morocco));
        words.add(new Word(getString(R.string.saudi_anthem),R.drawable.saudi,R.raw.saudi));
        words.add(new Word(getString(R.string.yemen_anthem),R.drawable.yemen,R.raw.yemen));
        words.add(new Word(getString(R.string.syria_anthem),R.drawable.syria,R.raw.syria));
        words.add(new Word(getString(R.string.tunisia_anthem),R.drawable.tunisia,R.raw.tunisia));
        words.add(new Word(getString(R.string.somalia_anthem),R.drawable.somalia,R.raw.somalia));
        words.add(new Word(getString(R.string.jordan_anthem),R.drawable.jordan,R.raw.jordan));
        words.add(new Word(getString(R.string.emirates_anthem),R.drawable.emirates,R.raw.emirates));
        words.add(new Word(getString(R.string.libya_anthem),R.drawable.libya,R.raw.libya));
        words.add(new Word(getString(R.string.plastinian_anthem),R.drawable.palestine,R.raw.palestine));
        words.add(new Word(getString(R.string.lebnan_anthem),R.drawable.lebnan,R.raw.lebnan));
        words.add(new Word(getString(R.string.oman_anthem),R.drawable.oman,R.raw.oman));
        words.add(new Word(getString(R.string.kuwait_anthem),R.drawable.kuwait,R.raw.kuwait));
        words.add(new Word(getString(R.string.moretanya_anthem),R.drawable.moretanya,R.raw.moretanya));
        words.add(new Word(getString(R.string.qatar_anthem),R.drawable.qatar,R.raw.qatar));
        words.add(new Word(getString(R.string.bahrain_anthem),R.drawable.bahrain,R.raw.bahrain));
        words.add(new Word(getString(R.string.jibouti_anthem),R.drawable.jibouti,R.raw.jibouti));
        words.add(new Word(getString(R.string.comoros_anthem),R.drawable.comoros,R.raw.comoros));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(Anthem.this, word.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}