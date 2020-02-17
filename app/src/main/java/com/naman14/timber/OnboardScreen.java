package com.naman14.timber;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naman14.timber.activities.MainActivity;

import naman14.timber.dev.Splashscreen;

public class OnboardScreen extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout layoutshow;
    TextView[] Dots;
    Button mNextBtn;
    Button mBackBtn;
    private int currentPage;
    private SlideAdapter slideAdapter;
    SharedPreferences onboarding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboard_screen);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        layoutshow = (LinearLayout) findViewById(R.id.layoutshow);

        mNextBtn = (Button) findViewById(R.id.btnnext);
        mBackBtn = (Button) findViewById(R.id.btnback);

        slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);
        
        addDotIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(currentPage - 1);
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNextBtn.getText().equals("FINISH")){

                    SharedPreferences onboarding = getSharedPreferences("MyPrefsFile", 0);
                    SharedPreferences.Editor editor = onboarding.edit();
                    editor.putBoolean("isused", true).apply();
                    Intent intent = new Intent(OnboardScreen.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
                viewPager.setCurrentItem(currentPage + 1);
            }
        });
    }

    private void addDotIndicator(int position) {
        Dots = new TextView[6];
        layoutshow.removeAllViews();
        for(int i = 0; i < Dots.length; i++){
            Dots[i] = new TextView(this);
            Dots[i].setText(Html.fromHtml("&#8226"));
            Dots[i].setTextSize(35);
            Dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            layoutshow.addView(Dots[i]);
        }

        if(Dots.length > 0){
            Dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotIndicator(position);
            currentPage = position;

            if(position == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("NEXT");
                mBackBtn.setText("");
            } else if(position == Dots.length - 1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("FINISH");
                mBackBtn.setText("BACK");
            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("NEXT");
                mBackBtn.setText("BACK");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
