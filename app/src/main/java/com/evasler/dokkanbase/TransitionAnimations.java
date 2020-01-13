package com.evasler.dokkanbase;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class TransitionAnimations {

    private View view;
    private Context context;

    private Animation animation;

    public TransitionAnimations(Context context) {
        this.context = context;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void fadeInAnimation(View view, Runnable runnable) {
        setView(view);
        startAnimation(R.anim.black_fade_in, runnable);
    }

    public void fadeOutAnimation(View view, Runnable runnable) {
        setView(view);
        startAnimation(R.anim.black_fade_out, runnable);
    }

    private void startAnimation(final int animationId, final Runnable runnable) {
        animation = AnimationUtils.loadAnimation(context, animationId);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onPreAnimation();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animationId == R.anim.black_fade_out) {
                    onPostAnimation();
                }
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    private void onPreAnimation() {
        view.setVisibility(View.VISIBLE);
    }

    private void onPostAnimation() {
        view.setVisibility(View.GONE);
    }

    public boolean isAnimationFinished() {
        return animation.hasEnded();
    }

    private void waitForAnimationToFinish() {
        while (!isAnimationFinished()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*public void executeOnAnimationFinished(final Runnable runnable) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                waitForAnimationToFinish();
                runnable.run();
            }
        }).start();
    }*/
}
