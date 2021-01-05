package vaa.technowize.kandroid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.ActionBar;

import android.view.View;

import kandroid.R;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import vaa.technowize.kandroid.KandroidApplication;
import vaa.technowize.kandroid.mvp.presenters.SplashPresenter;
import vaa.technowize.kandroid.mvp.views.SplashView;


public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    // You want Dagger to provide an instance of
    @InjectPresenter
    SplashPresenter mSplashPresenter;

    private final int UI_ANIMATION_DELAY = 3000;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

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
                checkAccess();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((KandroidApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mContentView = findViewById(R.id.fullscreen_content);

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
        if (mSplashPresenter.createKandoardAPI(this.getBaseContext())) {
            successSignIn();
        } else {
            startSignIn();
        }
    }

    @Override
    public void startSignIn() {
        Intent iLoginScreen = new Intent(this, LoginActivity.class);
        startActivity(iLoginScreen);
        finish();
    }

    @Override
    public boolean showProgress() {
        return true;
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