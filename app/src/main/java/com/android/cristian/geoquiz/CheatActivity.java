package com.android.cristian.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String KEY_ANSWER_SHOW = "answer_shown";
    private static final String TAG = "CheatActivity";
    private boolean answerIsTrue;
    private boolean isAnswerShown;
    private TextView answerTextView;
    private Button showAnswerButton;
    private TextView apiLevelTextView;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_cheat);

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        answerTextView = findViewById(R.id.answer_text_view);

        apiLevelTextView = findViewById(R.id.api_level_text_view);
        apiLevelTextView.setText(String.format("API Level %d", Build.VERSION.SDK_INT));

        showAnswerButton = findViewById(R.id.show_answer_button);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextToAnswerTextView();
                setAnswerShownResult(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = showAnswerButton.getWidth() / 2;
                    int cy = showAnswerButton.getHeight() / 2;
                    float radius = showAnswerButton.getWidth();

                    Animator animator = ViewAnimationUtils
                            .createCircularReveal(showAnswerButton, cx, cy, radius, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            showAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });

                    animator.start();
                } else {
                    showAnswerButton.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    private void disableShowAnswerButton() {
        if (isAnswerShown) {
            showAnswerButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setTextToAnswerTextView() {
        if (answerIsTrue) {
            answerTextView.setText(R.string.true_button);
        } else {
            answerTextView.setText(R.string.false_button);
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        this.isAnswerShown = isAnswerShown;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, this.isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ANSWER_SHOW, isAnswerShown);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOW, false);
        setTextToAnswerTextView();
        disableShowAnswerButton();
    }
}
