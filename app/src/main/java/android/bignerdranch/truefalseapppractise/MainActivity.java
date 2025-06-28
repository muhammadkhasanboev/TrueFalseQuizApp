package android.bignerdranch.truefalseapppractise;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //initializing buttons on layout
    private Button mTrueButton, mFalseButton, mNextButton, mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex=0;

    /**
     * takes resource id and based on that resource id displays questions on string.xml
     * mQuestionBank is object of Question.java and it initialized as array
     * question variable taking resource id by .getTextResId() method on Question.java
     * */
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    /**
     * checks user answer
     * prints toast message according to the user answer*/
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;}
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**
         * connecting layout buttons to the logic
         * */
        mQuestionTextView = (TextView) findViewById(R.id.question);
        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(false);
            }
        });
        // Sets click listeners on the True and False buttons.
        // Passes the user's selected answer to checkAnswer() for validation.

        //previous button logic
        mNextButton = (Button) findViewById(R.id.nextbutton);
       mNextButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
               updateQuestion();
           }
       });

       //next button logic
       mPrevButton = (Button) findViewById(R.id.prevbutton);
       mPrevButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
               updateQuestion();
           }
       });
        updateQuestion();
    }
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, true),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, false)
    };
}