package com.android.jaiswalshiwam.quizzi;

import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    int mIndex;
    int ques;
    Button trubtn;
    Button falbtn;
    TextView question;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mScore;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1,true),
            new TrueFalse(R.string.question_2,true),
            new TrueFalse(R.string.question_3,true),
            new TrueFalse(R.string.question_4,true),
            new TrueFalse(R.string.question_5,true),
            new TrueFalse(R.string.question_6,false),
            new TrueFalse(R.string.question_7,true),
            new TrueFalse(R.string.question_8,false),
            new TrueFalse(R.string.question_9,true),
            new TrueFalse(R.string.question_10,true),
            new TrueFalse(R.string.question_11,false),
            new TrueFalse(R.string.question_12,false),
            new TrueFalse(R.string.question_13,true),

    };
    int PROGRESS_BAR_INCREMENT=(int)Math.ceil(100.0/(mQuestionBank.length));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mScore=savedInstanceState.getInt("ScoreKey");
            mIndex=savedInstanceState.getInt("IndexKey");
        }else{
            mScore = 0;
            mIndex=0;
        }
        trubtn=(Button) findViewById(R.id.trubtn);
        falbtn=(Button) findViewById(R.id.falbtn);
        question=(TextView) findViewById(R.id.question);
        mProgressBar=(ProgressBar) findViewById(R.id.progressBar2);
        mScoreTextView=(TextView) findViewById(R.id.score);



        mScoreTextView.setText("Score"+mScore+"/"+mQuestionBank.length);
        ques=mQuestionBank[mIndex].getQuestionId();
        question.setText(ques);

        trubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
               updateQuestion();

            }
        });
        falbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();

            }
        });

        }
        private void updateQuestion(){
        mIndex=(mIndex+1)%mQuestionBank.length;
        if(mIndex==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You Scored "+ mScore + " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            /*alert.setPositiveButton("Restart Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recreate();
                }
            });*/
            alert.show();
        }
        ques=mQuestionBank[mIndex].getQuestionId();
        question.setText(ques);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score"+mScore+"/"+mQuestionBank.length);
        }

        private void checkAnswer(boolean userSelection){
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();
        if(userSelection == correctAnswer){
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mScore=mScore+1;
        }else{
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }

        }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey",mIndex);
    }
}

