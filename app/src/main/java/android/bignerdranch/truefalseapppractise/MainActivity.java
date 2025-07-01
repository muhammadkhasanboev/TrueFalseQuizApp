package android.bignerdranch.truefalseapppractise;

import android.app.Activity;
import android.content.Intent;
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
    private static final String KEY_INDEX = "index";

    private Button mCheatButton;
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;
    private int score = 0;

    /**
     * takes resource id and based on that resource id displays questions on string.xml
     * mQuestionBank is object of Question.java and it initialized as array
     * question variable taking resource id by .getTextResId() method on Question.java
     * */
    private void updateQuestion(){
        if(mCurrentIndex<mQuestionBank.length){
            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
            updatePrevButtonVisibility();
        }
        else{
            mQuestionTextView.setText("Quiz is finished! \n Your score is "+score+"/"+mQuestionBank.length);
            mTrueButton.setVisibility(View.INVISIBLE);
            mFalseButton.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);}
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**
         * connecting layout buttons to the logic
         * */

        //when user clicks to the text it will go to next question
        mQuestionTextView = (TextView) findViewById(R.id.question);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex < mQuestionBank.length){
                    mCurrentIndex++;
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    updateQuestion();
                }
            }
        });
        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex < mQuestionBank.length) {
                    checkAnswer(true);
                    mCurrentIndex++;
                    updateQuestion();
                }
            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex < mQuestionBank.length) {
                    checkAnswer(false);
                    mCurrentIndex++;
                    updateQuestion();
                }
            }
        });
        // Sets click listeners on the True and False buttons.
        // Passes the user's selected answer to checkAnswer() for validation.

        //next button logic
        mNextButton = (Button) findViewById(R.id.nextbutton);
       mNextButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mCurrentIndex < mQuestionBank.length){
                   mCurrentIndex++;
                   mTrueButton.setEnabled(true);
                   mFalseButton.setEnabled(true);
                   updateQuestion();
               }
           }
       });

       //prev button logic
       mPrevButton = (Button) findViewById(R.id.prevbutton);
       mPrevButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              mTrueButton.setEnabled(false);
              mFalseButton.setEnabled(false);
              mCurrentIndex--;
              updateQuestion();
           }
       });

       //cheat button
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
                Intent i = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);

            }
        });
        updateQuestion();
    }

    /**
     * checks user answer
     * prints toast message according to the user answer*/
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            score++;
        } else {
            messageResId = R.string.incorrect_toast;}
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);}
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, true),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, false)
    };

    private void updatePrevButtonVisibility() {
        if (mCurrentIndex == 0) {
            mPrevButton.setVisibility(View.INVISIBLE); // or .setEnabled(false)
        } else {
            mPrevButton.setVisibility(View.VISIBLE);  // or .setEnabled(true)
        }
    }


}