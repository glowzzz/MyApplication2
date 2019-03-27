package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButtontrue, mButtonfalse;//正确和错误按钮
    private TextView mQuestionTextView;//显示题目
    private Button mButtonNext;//下一题
    private Button mButtonTips;//查看答案
    private int mQuestionIndex = 0;//题目索引
    public static final String TAG = "MainActivity";//日志来源
    private static final String KEY_INDEX = "index";//索引键
    private static final int REQUEST_CODE_ANSWER=10;//请求代码,发送给answeractivity
    private TranslateAnimation mTranslateAnimation;//创建平移动画
    private Question[] mQuestions = new Question[]{
            new Question(R.string.Q1, false),
            new Question(R.string.Q2, true),
            new Question(R.string.Q3, true),
            new Question(R.string.Q4, true),
            new Question(R.string.Q5, true),
            new Question(R.string.Q6, true),
            new Question(R.string.Q7, true),
            new Question(R.string.Q8, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate创建界面");
        if (savedInstanceState != null) {
            mQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            Log.d(TAG, "Bundle()恢复状态");
        }
        setContentView(R.layout.activity_main);
        mButtontrue = findViewById(R.id.butture);
        mButtonfalse = findViewById(R.id.butfalse);
        mQuestionTextView = findViewById(R.id.question_text_View);//显示题目
        mButtonNext = findViewById(R.id.butnext);
        mButtonTips = findViewById(R.id.buttips);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionIndex = (mQuestionIndex + 1) % mQuestions.length;//防溢出循环
                updataQeestion();//更新题目
                mButtonNext.setEnabled(false);//恢复按钮不可用状态
                if (mQuestionIndex == mQuestions.length - 1) {
                    mQuestionIndex = 0;
                } else {
                    mQuestionIndex++;
                }
                if (mQuestionIndex == mQuestions.length - 1) {
                    Toast.makeText(MainActivity.this, "最后一题了", Toast.LENGTH_SHORT).show();
                    mButtonNext.setText(R.string.reset);
                    updataButtonNext(R.drawable.ic_reset);
                }
                if (mQuestionIndex == 0) {
                    mButtonNext.setText("下一题");
                    updataButtonNext(R.drawable.next);


                }

            }
        });
        mButtonTips = findViewById(R.id.buttips);
        mButtonTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp;
                if (mQuestions[mQuestionIndex].isAnswer()){
                    tmp="正确";
                }else {
                    tmp="错误";
                }
                Intent intent = new Intent(MainActivity.this, AnswerActivity.class);//显示调用
                intent.putExtra("msg",tmp);
                startActivityForResult(intent,REQUEST_CODE_ANSWER);

                /*if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent(MainActivity.this, AnswerActivity.class);

                    startActivity(intent);
                 Intent intent=new Intent(Intent.ACTION_DIAL);
                 intent.setData(Uri.parse("tel:13106774010"));
                 startActivity(intent);
                }
*/
            }
        });
        updataQeestion();//获取题目
        mButtonfalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQestion(false);
            }
        });
        mButtontrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQestion(true);
            }
        });
    }

    public void updataQeestion() {
        int i = mQuestions[mQuestionIndex].getMtextId();//获取题目
        mQuestionTextView.setText(i);//显示题目出来
      /*  mTranslateAnimation=new TranslateAnimation(0,200,0,0);
        mTranslateAnimation.setDuration(2000);
        mTranslateAnimation.setRepeatCount(1);
        mTranslateAnimation.setRepeatMode(Animation.REVERSE);
        mQuestionTextView.startAnimation(mTranslateAnimation);*/
        Animation set= AnimationUtils.loadAnimation(this,R.anim.animation_list);
        mQuestionTextView.startAnimation(set);

    }

    private void checkQestion(boolean userAnswer) {
        boolean trueAnswer = mQuestions[mQuestionIndex].isAnswer();//取得正确答案
        int message;
        if (userAnswer == trueAnswer) {
            message = R.string.yes;
            mButtonNext.setEnabled(true);

        } else {
            message = R.string.no;
            mButtonNext.setEnabled(false);

        }
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void updataButtonNext(int imageID) {
        Drawable d = getDrawable(imageID);
        d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        mButtonNext.setCompoundDrawables(null, null, d, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()使界面可见");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume(界面前台显示");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()界面离开前台");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()界面不可见");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()界面销毁" + TAG);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()我有回来了");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()保存状态");
        outState.putInt(KEY_INDEX, mQuestionIndex);//形成键值对
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//3个参数：第一个是请求代码，第二个是返回代码，第三个是返回的intent
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ANSWER && resultCode== Activity.RESULT_OK){
            String tmp=data.getStringExtra("answer_show");
            Toast.makeText(MainActivity.this,tmp,Toast.LENGTH_SHORT).show();
        }
    }



}
