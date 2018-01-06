package com.jaja.gaussian;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.jaja.gaussian.anim.Animate;
import com.jaja.gaussian.view.GaussianBlur;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView ivRawImage;
    private ImageView ivBlurredImage;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinosaur_fragment);
        ivRawImage = (ImageView) findViewById(R.id.raw_image);
        ivBlurredImage = (ImageView) findViewById(R.id.blurred_image);
        ivRawImage.setImageResource(R.mipmap.allosaurus);
        ivBlurredImage.setOnTouchListener(this);
        applyBlur();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_MOVE)
            runDetectUnblurRequested(motionEvent.getAction());
        return true;
    }

    private void runDetectUnblurRequested(final int action) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }

        switch (action) {
            case MotionEvent.ACTION_CANCEL:
                ivBlurredImage.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_DOWN:
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animate.with(ivBlurredImage).toggleVisibility();
                    }
                }, 200);
                break;
            case MotionEvent.ACTION_UP:
                Animate.with(ivBlurredImage).toggleVisibility();
                break;
            default:
                break;
        }
    }

    private void applyBlur() {
        GaussianBlur.with(this)
                .size(getActualMaxSize())
                .radius(getActualRadius())
                .put(R.mipmap.allosaurus, ivBlurredImage);
    }


    public int getActualRadius() {
        return 20;
    }

    public int getActualMaxSize() {
        return 660;
    }

}
