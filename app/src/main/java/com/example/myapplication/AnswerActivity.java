package com.example.myapplication;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Objects;

public class AnswerActivity extends AppCompatActivity {
private TextView mAnsnwerTextView;
private ImageView mImageView;
private static final String EXTRA_ANSWER="answer_show";//返回的键值
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_answer);
        mAnsnwerTextView=findViewById(R.id.Answer);
        Intent data=getIntent();
        String answer=data.getStringExtra("msg");//取出msg的值
        mAnsnwerTextView.setText(answer);//显示到文本组件中
        data.putExtra(EXTRA_ANSWER,"你已查看答案");
        setResult(Activity.RESULT_OK,data);//返回代码和Intent对象

        mImageView=findViewById(R.id.image_view);
        Animator set= AnimatorInflater.loadAnimator(this,R.animator.animation_alpha);
        set.setTarget(mImageView);
        set.start();
      set.addListener(new Animator.AnimatorListener() {
          @Override
          public void onAnimationStart(Animator animation) {

         }

          @Override
        public void onAnimationEnd(Animator animation) {
              //数字上涨
              ValueAnimator moneyAnimator=ValueAnimator.ofFloat(0f,152019.03f);//Float估值器
              moneyAnimator.setInterpolator(new LinearInterpolator());//减速插值器
              moneyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                  @Override
                  public void onAnimationUpdate(ValueAnimator animation) {
                    float money= (float) animation.getAnimatedValue();
                    mAnsnwerTextView.setText(String.format("%.1f$",money));//"%.1f$"显示小数后方多少位

                  }
              });

              int startcolor= Color.parseColor("#FFDEAD");
              int endcolor= Color.parseColor(" #0000CD");
              ValueAnimator coloranimator=ValueAnimator.ofArgb(startcolor,endcolor);
              coloranimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                  @Override
                  public void onAnimationUpdate(ValueAnimator animation) {
                      int color= (int) animation.getAnimatedValue();
                      mAnsnwerTextView.setTextColor(color);
                  }
              });
              AnimatorSet set=new AnimatorSet();//设置组合动画
              set.playTogether(moneyAnimator,coloranimator);//组合文字动画和颜色动画
              set.setDuration(3000);//3000毫秒
              set.start();//动画开始
          }

           @Override
           public void onAnimationCancel(Animator animation) {

          }

           @Override
           public void onAnimationRepeat(Animator animation) {

            }
       });
    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        mImageView.setImageResource(R.drawable.animation_frame);//将帧动画文件设置到图片组件的图片资源中
//        AnimationDrawable animationDrawable=(AnimationDrawable) mImageView.getDrawable();//将图片资源传输给动画对象
//        animationDrawable.start();
    }
}
