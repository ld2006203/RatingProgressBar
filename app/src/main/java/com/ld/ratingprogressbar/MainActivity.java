package com.ld.ratingprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ld.ratingprogressbar.customview.RatingProgressBar;

/**
 * 示例界面
 *
 * @author 梁栋 510655711@qq.com
 */
public class MainActivity extends AppCompatActivity {

    private RatingProgressBar ratingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ratingProgressBar = (RatingProgressBar) findViewById(R.id.rpb_progress);
        ratingProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentProgress = 0;
                mTotalProgress = 60;
                new Thread(new ProgressRunnable()).start();
            }
        });
    }

    private int mCurrentProgress;
    private int mTotalProgress;

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentProgress = 0;
        mTotalProgress = 60;
        new Thread(new ProgressRunnable()).start();
    }

    class ProgressRunnable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                ratingProgressBar.setProgress(mCurrentProgress);

                try {
                    Thread.sleep(17);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
