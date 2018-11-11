package com.martirosov.sergey.repeatgeoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String KEY_BUNDLE = "CurrentIndex";
    public static final String ANSWER_KEY = "answerKey";
    public static final int REQUEST_CODE = 0;
    TextView questionText;
    LinearLayout answerLL;
    boolean wasAnswerShown;

    Question[] questions = {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true)
    };

    boolean[][] answers = new boolean[questions.length][2];


    int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
        answerLL = findViewById(R.id.answer_linear_layout);
        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);
        Button prevButton = findViewById(R.id.prev_button);
        Button nextButton = findViewById(R.id.next_button);
        questionText = findViewById(R.id.question_text);
        Button cheatButton = findViewById(R.id.cheat_button);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_BUNDLE, 0);
            answers = (boolean[][]) savedInstanceState.getSerializable(ANSWER_KEY);
        }

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(-1);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(1);
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CheatActivity.newIntent(MainActivity.this, questions[currentIndex].isAnswerTrue()), REQUEST_CODE);
            }
        });
        updateQuestion(0);
        Toast.makeText(this, "" + currentIndex, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BUNDLE, currentIndex);
        outState.putSerializable(ANSWER_KEY, answers);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    public void checkAnswer(boolean answered) {

        boolean isFull = true;
        int resID;
        if (questions[currentIndex].isAnswerTrue() == answered) {
            resID = R.string.correct;
            answers[currentIndex][1] = true;
        } else {
            resID = R.string.incorrect;
            answers[currentIndex][1] = false;
        }
        answers[currentIndex][0] = true;
        Toast toast = Toast.makeText(MainActivity.this, resID, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
        for (boolean[] b : answers) {
            isFull = isFull & b[0];
        }
        if (isFull) {
            showResult();
        }
        answerLL.setVisibility(View.INVISIBLE);
    }

    private void showResult() {
        int cor = 0;
        int total = 0;
        for (int i = 0; i < questions.length; i++) {
            if (answers[i][0] & answers[i][1]) {
                cor++;
            }
            if (answers[i][0]) {
                total++;
            }
        }
        int mark = cor * 5 / total;
        Toast.makeText(this, String.format(getString(R.string.result), mark), Toast.LENGTH_SHORT).show();
    }

    public void updateQuestion(int i) {
        wasAnswerShown=false;
        currentIndex = (currentIndex + i + 6) % 6;
        questionText.setText(questions[currentIndex].getTextResId());
        if (!answers[currentIndex][0]) {
            answerLL.setVisibility(View.VISIBLE);
        } else {
            answerLL.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if ((data != null)&&(resultCode == RESULT_OK)) {
                wasAnswerShown = CheatActivity.wasAnswerShown(data);
            } else {
                return;
            }
        }else{
            return;
        }
    }
}
