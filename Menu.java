package com.example.brianhuang.hangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Menu extends AppCompatActivity {
    Button gameButton;
    Button instructionsButton;
    Button creditsButton;
    List<String> wordList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        gameButton = findViewById(R.id.button_game);
        instructionsButton = findViewById(R.id.button_instructions);
        creditsButton = findViewById(R.id.button_credits);
    }

    public List<String> getWordsFromFile(String textFileName, Context context){
        List<String> wordList = new ArrayList<>();
        String textStr = "";
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(textFileName);
            textStr = getStringFromInputStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(textStr !=null){
            String[] words = textStr.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replaceAll("[^\\w]", "");
                wordList.add(words[i]);
            }
        }
        return wordList;
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader bufferedReader = null;
        ArrayList<String> allWords = new ArrayList<>();

        String line;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            while ((line = bufferedReader.readLine()) != null) {
                allWords.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return allWords.toString();


    }
    public void onClickPlay (View view) {
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordList = getWordsFromFile("hangman.txt", Menu.this);
                Random r = new Random();
                int random = r.nextInt(25000);
                String theWord = wordList.get(random);
                int theWordLength = theWord.length();
                Intent myIntent = new Intent(Menu.this,Hangman.class);
                myIntent.putExtra("theWord",theWord);
                myIntent.putExtra("theWordLength",theWordLength);
                startActivity(myIntent);
            }

        });
    }
    public void onClickInstructions (View view) {
        AlertDialog instructions = new AlertDialog.Builder(this).create();
        instructions.setCancelable(true);
        instructions.setTitle("How To Play");
        instructions.setMessage("The objective of the game is to guess a word one letter at a time! You have 6 tries before you lose the game. Good Luck!");
        instructions.setButton(DialogInterface.BUTTON_NEGATIVE, "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        instructions.show();
    }
    public void onClickCredits (View view) {
        AlertDialog credits = new AlertDialog.Builder(this).create();
        credits.setCancelable(true);
        credits.setTitle("Credits");
        credits.setMessage("Created and Coded by Brian Huang");
        credits.setButton(DialogInterface.BUTTON_NEGATIVE, "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        credits.show();
    }
}
