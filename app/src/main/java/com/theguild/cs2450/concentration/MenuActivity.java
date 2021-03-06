/** MenuActivity
 * This activity contains the methods and objects that comprise the main menu screen and allow the
 * user to navigate between the menu, the game, and the high score table and toggle the music on and
 * off.
 * @author  Matthew Scott Bronco #014966432, Kaye Reeves Bronco #014865383
 * @since   2021-05-12
 */

package com.theguild.cs2450.concentration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private Button mPlayButton;
    private Button mHighScoresButton;
    private Button mMusicButton;
    private Intent musicIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        musicIntent = new Intent(this, AudioService.class);
        musicIntent.setAction(null);
        if (!AudioService.sMusicPaused) {
            startService(musicIntent);
        }

        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        mHighScoresButton = (Button) findViewById(R.id.highscores_button);
        mHighScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, HighScoresActivity.class);
                startActivity(intent);
            }
        });

        mMusicButton = (Button) findViewById(R.id.music_button);

        if (AudioService.sMusicPaused) {
            mMusicButton.setText("Enable Music");

        }

        mMusicButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!AudioService.sMusicPaused) { //disable music
                    stopService(musicIntent);
                    AudioService.sMusicPaused = true;
                    mMusicButton.setText("Enable Music");
                }
                else { //enable music
                    startService(musicIntent);
                    AudioService.sMusicPaused = false;
                    mMusicButton.setText("Disable Music");
                }
            }
        });

    }
}