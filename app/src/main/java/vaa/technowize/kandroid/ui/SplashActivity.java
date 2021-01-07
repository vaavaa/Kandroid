package vaa.technowize.kandroid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.ActionBar;

import android.view.View;
import android.widget.ProgressBar;

import kandroid.R;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import vaa.technowize.kandroid.KandroidApplication;
import vaa.technowize.kandroid.mvp.presenters.SplashPresenter;
import vaa.technowize.kandroid.mvp.views.SplashView;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    // You want Dagger to provide an instance of
    @InjectPresenter
    SplashPresenter mSplashPresenter;

    private final int UI_ANIMATION_DELAY = 3000;
    private final int UI_PROGRESS_DELAY = 30;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private ProgressBar mThinProgress;
    private int exit_while = 1;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };


    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
                mThinProgress.setVisibility(View.VISIBLE);
                exit_while = mThinProgress.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (exit_while < mThinProgress.getMax()) {
                            exit_while += 1;
                            // Update the progress bar and display the current value in text view
                            mThinProgress.post(new Runnable() {
                                public void run() {
                                    mThinProgress.setProgress(exit_while);
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(UI_PROGRESS_DELAY);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (exit_while == mThinProgress.getMax()) {
                            checkAccess();
                        }
                    }
                }).start();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KandroidApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mContentView = findViewById(R.id.fullscreen_content);
        mThinProgress = findViewById(R.id.thin_progress);

        finishAnimation();
    }

    @Override
    public void startAnimation() {
        show();
    }

    @Override
    public void finishAnimation() {
        hide();
    }

    @Override
    public void successSignIn() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(mainActivity, bundle);
        finish();
    }

    @Override
    public void checkAccess() {

        successSignIn();


    }

    @Override
    public void startSignIn() {
        Intent iLoginScreen = new Intent(this, LoginActivity.class);
        startActivity(iLoginScreen);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        startAnimation();
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }


    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

}