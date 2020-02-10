package com.android.cristian.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private Question[] questionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int currentIndex;
    private Boolean[] answers = new Boolean[questionBank.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.question_text_view);

        trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAllQuestionsAnswered()) {
                    nextButton.setEnabled(false);
                    showAllQuestionsAnsweredAlert();
                } else {
                    showNextQuestion();
                }
            }
        });

        updateQuestion();
    }

    private boolean isAllQuestionsAnswered() {
        boolean flag = true;

        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == null) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private void showAllQuestionsAnsweredAlert() {
        int correctAwnswers = 0;
        int incorrectAnswers = 0;

        for (int i = 0; i < answers.length; i++) {
            if (answers[i]) {
                correctAwnswers++;
            } else {
                incorrectAnswers++;
            }
        }

        String message = String.format(
                getString(R.string.all_questions_answered_message),
                correctAwnswers,
                incorrectAnswers);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.length;
        updateQuestion();
    }

    private void updateQuestion() {
        int question = questionBank[currentIndex].getTextResId();
        questionTextView.setText(question);
        updateTrueAndFalseButtonsAvailability();
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();

        int messageResId;

        if (userPressedTrue == answerIsTrue) {
            answers[currentIndex] = true;
            messageResId = R.string.correct_toast;
        } else {
            answers[currentIndex] = false;
            messageResId = R.string.incorrect_toast;
        }

        updateTrueAndFalseButtonsAvailability();

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateTrueAndFalseButtonsAvailability() {

        if (answers[currentIndex] != null) {
            trueButton.setEnabled(false);
            falseButton.setEnabled(false);
        } else {
            trueButton.setEnabled(true);
            falseButton.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentIndex = savedInstanceState.getInt(KEY_INDEX);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
}
