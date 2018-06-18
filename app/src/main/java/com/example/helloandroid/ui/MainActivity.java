package com.example.helloandroid.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloandroid.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Runnable {

    private TextView mTv;
    private Button mBt;
    private boolean isClick = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTv.setText((String) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        new Thread(this).start();
    }


    private void initData() {
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClick) {
                    mTv.setText("你好，Android");
                    mBt.setClickable(isClick);
                    isClick = false;
                } else {
                    mTv.setText("我爱你，Android");
                    mBt.setClickable(isClick);
                    isClick = true;
                }
            }
        });
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "弹出", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mBt = (Button) findViewById(R.id.bt);
    }

    public String CalendarInstance() {
        Calendar mCalendar = Calendar.getInstance();
        int Year = mCalendar.get(Calendar.YEAR);
        String Month = formatTime(mCalendar.get(Calendar.MONTH) + 1);
        String Day = formatTime(mCalendar.get(Calendar.DAY_OF_MONTH));
        String Hour = formatTime(mCalendar.get(Calendar.HOUR_OF_DAY));
        String Minute = formatTime(mCalendar.get(Calendar.MINUTE));
        String Second = formatTime(mCalendar.get(Calendar.SECOND));
        String time = Year + "年" + Month + "月" + Day + "日 " + Hour + "时" + Minute + "分" + Second + "秒";
        return time;
    }

    private String formatTime(int t) {
        return t >= 10 ? "" + t : "0" + t;//三元运算符 t>10时取 ""+t
    }

    @Override
    public void run() {

        try {
            while (true) {
                String time = CalendarInstance();
                handler.sendMessage(handler.obtainMessage(100, time));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
