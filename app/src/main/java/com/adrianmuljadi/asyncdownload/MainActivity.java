package com.adrianmuljadi.asyncdownload;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    private TextView tv;
    private Handler mHandler = new Handler();

    private Button button;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        tv = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mProgressStatus = 0;
//                new Thread(new Runnable() {
//                    public void run() {
//                        while (mProgressStatus < 100) {
//                            mProgressStatus++;
//                            mHandler.post(new Runnable() {
//                                public void run() {
//                                    mProgress.setProgress(mProgressStatus);
//                                    tv.setText("Progress: " + mProgressStatus);
//                                }
//                            });
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
//            }
//        });

        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus++;
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                            tv.setText("Progress: " + mProgressStatus);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
