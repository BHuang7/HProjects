package com.example.brianhuang.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Hangman extends AppCompatActivity {
    Button quitButton;
    TextView attempts;
    TextView word;
    EditText answer;
    TextView guesses;
    Button enterButton;
    int correctNumber = 0;
    int attemptsNum = 6;
    ArrayList<String> guessedLetters = new ArrayList<>();
    ArrayList<String> underScores = new ArrayList<>();
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        quitButton = findViewById(R.id.button_quit);
        attempts = findViewById(R.id.textView_attempts);
        word = findViewById(R.id.textView_word);
        answer = findViewById(R.id.editText_answer);
        guesses = findViewById(R.id.textView_guesses);
        enterButton = findViewById(R.id.button_enter);
        Bundle fromMenu = this.getIntent().getExtras();
        String theWord = fromMenu.getString("theWord");
        for (int i = 0; i < theWord.length(); i++) {
            underScores.add("_");
        }
        String underScoreString = underScores.toString();
        underScoreString = underScoreString.replace("[","");
        underScoreString = underScoreString.replace("]","");
        underScoreString = underScoreString.replace(",","");
        word.setText(underScoreString);
        attempts.setText("You have " + attemptsNum + " attempts left!");
        Toast.makeText(getApplicationContext(),theWord,Toast.LENGTH_SHORT).show();
    }


    public void onClickQuit (View view) {
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hangman.this, Menu.class));
            }
        });
    }
   public void onClickPlayButton (View view) {
        Bundle fromMenu = this.getIntent().getExtras();
        String theWord = fromMenu.getString("theWord");
        theWord = theWord.toLowerCase();
        String guess1 = answer.getText().toString();
        char userGuess = guess1.charAt(0);
       if (guessedLetters.contains(userGuess)) {
           AlertDialog instructions = new AlertDialog.Builder(this).create();
           instructions.setCancelable(true);
           instructions.setTitle("Error");
           instructions.setMessage("You have already tried that letter!");
           instructions.setButton(DialogInterface.BUTTON_NEGATIVE, "Continue", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           });
           instructions.show();
       }
       else {
           if (theWord.contains(guess1)) {
               for (int i = 0; i < theWord.length(); i++) {
                   if (theWord.charAt(i) == userGuess) {
                       correctNumber++;
                       underScores.set(i,guess1);
                       newString = underScores.toString();
                       newString = newString.replace("[","");
                       newString = newString.replace("]","");
                       newString = newString.replace(",","");
                       word.setText(newString);
                   }
               }
           } else {
               attemptsNum--;
           }
           if (correctNumber == theWord.length()) {
               AlertDialog instructions = new AlertDialog.Builder(this).create();
               instructions.setCancelable(true);
               instructions.setTitle("Congratulations!");
               instructions.setMessage("You Won!");
               instructions.setButton(DialogInterface.BUTTON_NEGATIVE, "Continue", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Intent myIntent = new Intent(Hangman.this,Menu.class);
                       startActivity(myIntent);
                       dialog.dismiss();
                   }
               });
               instructions.show();
           }
       }
        guessedLetters.add(guess1);
        answer.setText("");
        guesses.setText("Your guessed letters are " + guessedLetters.toString());
        attempts.setText("You have " + attemptsNum + " attempts left!");
       if (attemptsNum == 0) {
           AlertDialog instructions = new AlertDialog.Builder(this).create();
           instructions.setCancelable(true);
           instructions.setTitle("You Lost!");
           instructions.setMessage("Better Luck Next Time!");
           instructions.setButton(DialogInterface.BUTTON_NEGATIVE, "Continue", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   Intent myIntent = new Intent(Hangman.this,Menu.class);
                   startActivity(myIntent);
               }
           });
           instructions.show();
       }
    }

}
