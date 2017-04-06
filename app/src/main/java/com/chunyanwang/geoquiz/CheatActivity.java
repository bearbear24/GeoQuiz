package com.chunyanwang.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.chunyanwang.geoquiz.QuizActivity.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.chunyanwang.geoquiz.QuizActivity.answer_shown";

    private Button showCheatButton;
    private TextView answerText;
    private boolean answerIsTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        showCheatButton = (Button) findViewById(R.id.show_cheat_button);
        answerText = (TextView) findViewById(R.id.answer_text);
        showCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerIsTrue) {
                    answerText.setText(R.string.true_button);
                } else {
                    answerText.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    public static boolean wasAnswerShown(Intent data) {
        return data.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
