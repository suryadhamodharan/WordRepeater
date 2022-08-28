package com.surya.wordrepeater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    EditText txtText, txtLimit;
    CheckBox boxNewline, boxSpace,boxPeriod;
    Button btnGenerate, btnReset;
    TextView txtRepeat;
    ImageView imgCopy, imgShare;
    LinearLayout linearSnackbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtText = findViewById(R.id.txtWord);
        txtLimit = findViewById(R.id.txtLimit);
        boxNewline = findViewById(R.id.boxNewline);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnReset = findViewById(R.id.btnReset);
        txtRepeat = findViewById(R.id.txtWordRepeat);
        imgCopy = findViewById(R.id.imgCopy);
        linearSnackbar = findViewById(R.id.txtSnackBar);
        boxSpace = findViewById(R.id.boxSpace);
        imgShare = findViewById(R.id.imgShare);
        boxPeriod=findViewById(R.id.boxPeriod);



        btnGenerate.setOnClickListener(view -> onGeneratePressed());

        btnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtText.setText("");
                txtLimit.setText("");
                txtRepeat.setText("");
                boxSpace.setChecked(false);
                boxNewline.setChecked(false);
                boxPeriod.setChecked(false);

            }
        });
        imgCopy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", txtRepeat.getText());
                clipboard.setPrimaryClip(clip);
                showSnackbar("Copied");


            }
        });
        imgShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, txtRepeat.getText());
                sendIntent.setType("text/plain");
                Intent shareIntend = Intent.createChooser(sendIntent, "Share Through");
                startActivity(shareIntend);
            }
        });

    }

    String repeat(int limit, String textToRepeat, Boolean hasNewLine, Boolean hasSpace,Boolean hasPeriod) {
        StringBuilder word = new StringBuilder();
        for (int n = 1; n <= limit; n++) {
            if(hasNewLine&&hasPeriod){
                word.append(textToRepeat).append(".\n");

            }
            else if (hasNewLine) {

                word.append(textToRepeat).append("\n");
            } else if (hasSpace) {
                word.append(textToRepeat).append(" ");
            }
            else if(hasPeriod){
                word.append(textToRepeat).append(".");

            }
            else {
                word.append(textToRepeat);

            }
        }
        return word.toString();

    }

    void showSnackbar(String content) {
        Snackbar snackbar = Snackbar.make(linearSnackbar, content, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    void onGeneratePressed() {

        final String text = txtText.getText().toString();
        final String limit1=txtLimit.getText().toString();
        if(text.isEmpty()&&limit1.isEmpty()){
            txtText.setError("Can't be empty");
            txtLimit.setError("Can't be empty");
            return;

        }
        else if (text.isEmpty()) {
            txtText.setError("Can't be empty");
            return;
        }
        else if (limit1.isEmpty()) {
            txtLimit.setError("Can't be empty");
            return;
        }

        final int limit = Integer.parseInt(limit1);
        txtLimit.setFocusableInTouchMode(false);



        txtRepeat.setText(repeat(limit, text, boxNewline.isChecked(), boxSpace.isChecked(),boxPeriod.isChecked()));

    }
}


