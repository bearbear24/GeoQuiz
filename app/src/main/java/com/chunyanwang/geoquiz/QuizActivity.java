package com.chunyanwang.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String QUESTION_TEXT_INDEX_KEY = "QUESTION_TEXT_INDEX";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.chunyanwang.geoquiz.QuizActivity.answer_is_true";
    private static final int REQUEST_CODE = 0;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button cheatButton;

    private TextView questionTextView;
    private int currentViewIndex;
    private boolean isCheater;

    private Question[] questionBank = new Question[] {
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_mideast, true),
    };

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
         if (savedInstanceState.containsKey(QUESTION_TEXT_INDEX_KEY)) {
             currentViewIndex = savedInstanceState.getInt(QUESTION_TEXT_INDEX_KEY);
         }
        } else {
            currentViewIndex = 0;
        }
        setContentView(R.layout.activity_quiz);
        trueButton = (Button)findViewById(R.id.true_button);
        falseButton = (Button)findViewById(R.id.false_button);
        nextButton = (Button)findViewById(R.id.next_button);
        questionTextView = (TextView)findViewById(R.id.question_text);
        cheatButton = (Button) findViewById(R.id.cheat_button);
        updateQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewIndex = (currentViewIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = questionBank[currentViewIndex].getAnswerTrue();
                Intent i =  newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            if (data == null) {
                return;
            }
        }
        isCheater = CheatActivity.wasAnswerShown(data);
    }

    private void updateQuestion() {
        int question = questionBank[currentViewIndex].getTextResId();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questionBank[currentViewIndex].getAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(QUESTION_TEXT_INDEX_KEY, currentViewIndex);
    }
}
