package com.example.helloandroid.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.helloandroid.R;
import com.example.helloandroid.adapter.MyViewPager;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Integer> mList;
    private MyViewPager adapter;
    private int i;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            mViewPager.setCurrentItem(what);
        }
    };
    private Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(R.drawable.guide1);
        mList.add(R.drawable.guide2);
        mList.add(R.drawable.guide3);
        adapter = new MyViewPager(mList, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mList.size() * 1000);
        i = mViewPager.getCurrentItem();
        Log.d("SplashActivity", "原始值i=" + i);
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(3000);
                        i++;
                        Log.d("SplashActivity", "变化值i=" + i);
                        mHandler.sendEmptyMessage(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position % mList.size() == mList.size() - 1) {
                    mBt.setVisibility(View.VISIBLE);
                } else {
                    mBt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mBt = (Button) findViewById(R.id.btn);
    }
}
