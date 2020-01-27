package com.android.cristian.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button previousButton;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                enablePreviousButtonIfItsDisabled();
                showNextQuestion();
            }
        });

        questionTextView = findViewById(R.id.question_text_view);
        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enablePreviousButtonIfItsDisabled();
                showNextQuestion();
            }
        });

        previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 0) {
                    disablePreviousButtonWhenFirstQuestionIfShowed();
                } else {
                    showPreviousQuestion();
                }
            }
        });

        disablePreviousButtonWhenFirstQuestionIfShowed();
        updateQuestion();
    }

    private void enablePreviousButtonIfItsDisabled() {
        if (!previousButton.isEnabled()) {
            previousButton.setEnabled(true);
        }
    }

    private void disablePreviousButtonWhenFirstQuestionIfShowed() {
        if (currentIndex == 0) {
            previousButton.setEnabled(false);
        }
    }

    private void showNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.length;
        updateQuestion();
    }

    private void showPreviousQuestion() {
        if (currentIndex != 0) {
            currentIndex = (currentIndex - 1) % questionBank.length;
            updateQuestion();
        }
    }

    private void updateQuestion() {
        int question = questionBank[currentIndex].getTextResId();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();

        int messageResId;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
