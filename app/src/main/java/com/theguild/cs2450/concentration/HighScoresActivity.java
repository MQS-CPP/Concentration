/** HighScoresActivity
 * This activity contains the methods necessary for saving, retrieving, and displaying information from
 * the high score table.
 * @author  Gina Martinez Bronco #12111216, Kaye Reeves Bronco #014865383
 * @since   2021-05-12
 */

package com.theguild.cs2450.concentration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HighScoresActivity extends AppCompatActivity {

    // Class member field variables
    private Button hsBackButton;
    private static int sHighScore1;
    private static int sHighScore2;
    private static int sHighScore3;
    private static int sNumCards;
    private static String sName1;
    private static String sName2;
    private static String sName3;
    private TextView mTextViewScore;

    // prompts the user with the card number dialog box and also wires the screens widgets
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        showCardNumDialog();
        wireWidgets();
    }

    /** showCardNumDialog()
     * Shows a dialog prompting the user to choose the card number from a list that they would
     * like to see the appropriate high score set for.
     */
    private void showCardNumDialog() {
        CharSequence[] possCardAmounts = { "4", "6", "8", "10", "12", "14", "16", "18", "20" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_card_number)
                .setCancelable(false)
                .setItems(possCardAmounts, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sNumCards = Integer.parseInt(possCardAmounts[which].toString());
                        SharedPreferences prefs = getSharedPreferences("PREFS", 0);
                        getHighScorePlaces(prefs, sNumCards);

                        mTextViewScore.setText("High Scores for " + Integer.toString(sNumCards) + "-card games:" + "\n1. " + sName1 + " " + sHighScore1 + "\n2. " + sName2 + " " + sHighScore2 + "\n3. " + sName3 + " " + sHighScore3);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /** wireWidgets()
     * Wires the text view that will be displaying the scores to a text view variable
     * and wires a back button that finishes the activity, going back to the main menu.
     */
    private void wireWidgets() {
        mTextViewScore = (TextView) findViewById(R.id.scores_list_text_view);
        mTextViewScore.setTextSize(36);

        hsBackButton = (Button) findViewById(R.id.hs_back_button);
        hsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /** updateScores()
     * This method updates the high score table information in Shared Preferences.
     * @param name The name entered by the user
     * @param score The user's score
     * @param prefs The SharedPreferences object used to make the changes
     * @param noOfCards The number of cards the game was played with, used to place the user's score
     *                  in the appropriate category
     */
    public static void updateScores(String name, int score, SharedPreferences prefs, int noOfCards) {
        SharedPreferences.Editor editor = prefs.edit();

        getHighScorePlaces(prefs, noOfCards);

        // places the new high score where it belongs on the list in the file according to its rank
        if (score > sHighScore1) {
            int temp = prefs.getInt("HighScore1-" + Integer.toString(noOfCards), 0);
            String tempName = prefs.getString("Name1-" + Integer.toString(noOfCards), "YOURNAMEHERE");
            editor.putString("Name2-" + Integer.toString(noOfCards), tempName);
            editor.putString("Name1-" + Integer.toString(noOfCards), name);
            editor.putInt("HighScore2-" + Integer.toString(noOfCards), temp);
            editor.putInt("HighScore1-" + Integer.toString(noOfCards), score);
            editor.apply();
        }
        else if (score > sHighScore2) {
            int temp = prefs.getInt("HighScore2-" + Integer.toString(noOfCards), 0);
            String tempName = prefs.getString("Name2-" + Integer.toString(noOfCards), "YOURNAMEHERE");
            editor.putString("Name3-" + Integer.toString(noOfCards), tempName);
            editor.putString("Name2-" + Integer.toString(noOfCards), name);
            editor.putInt("HighScore3-" + Integer.toString(noOfCards), temp);
            editor.putInt("HighScore2-" + Integer.toString(noOfCards), score);
            editor.apply();
        }
        else if (score > sHighScore3) {
            editor.putString("Name3-" + Integer.toString(noOfCards), name);
            editor.putInt("HighScore3-" + Integer.toString(noOfCards), score);
            editor.apply();
        }

    }

    /** getHighScorePlaces()
     * Helper method that retrieves the usernames and scores at the first, second, and third place of
     * a given category.
     * @param prefs The SharedPreferences object used to access high score table information
     * @param numOfCards The number of cards the high score table will be accessed for
     */
    private static void getHighScorePlaces(SharedPreferences prefs, int numOfCards) {
        sHighScore1 = prefs.getInt("HighScore1-" + numOfCards, 0);
        sHighScore2 = prefs.getInt("HighScore2-" + numOfCards, 0);
        sHighScore3 = prefs.getInt("HighScore3-" + numOfCards, 0);
        sName1 = prefs.getString("Name1-" + numOfCards, "YOURNAMEHERE");
        sName2 = prefs.getString("Name2-" + numOfCards, "YOURNAMEHERE");
        sName3 = prefs.getString("Name3-" + numOfCards, "YOURNAMEHERE");
    }
}
