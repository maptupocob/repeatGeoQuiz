package com.martirosov.sergey.repeatgeoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    public static final String ANSWER_SHOWN = "ANSWER_SHOWN";
    Button cheatButton;
    TextView answerTextView;

    public static final String CHEAT_ACTIVITY_KEY = "CHEAT_ACTIVITY_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        answerTextView=findViewById(R.id.answer_text_view);
        cheatButton = findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answer = getIntent().getBooleanExtra(CHEAT_ACTIVITY_KEY, false);
                int resID = (answer)?R.string.true_button:R.string.false_button;
                answerTextView.setText(resID);
                setAnswerShownResult(true);
            }
        });

    }

    public static Intent newIntent(Context con, boolean isAnswerTrue){
        Intent intent = new Intent(con, CheatActivity.class);
        intent.putExtra(CHEAT_ACTIVITY_KEY, isAnswerTrue);
        return intent;
    }

    public void setAnswerShownResult(boolean isAnswerShown){
        Intent intent = new Intent();
        intent.putExtra(ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }

    public static boolean wasAnswerShown(Intent intent){
        return intent.getBooleanExtra(ANSWER_SHOWN, false);
    }
}
