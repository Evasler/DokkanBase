package com.evasler.dokkanbase;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;

public class LimitedScrollView extends ScrollView {

    public static int WITHOUT_MAX_HEIGHT_VALUE = -1;

    private int childHeight = WITHOUT_MAX_HEIGHT_VALUE;

    private boolean scrollable = true;

    public LimitedScrollView(Context context) {
        super(context);
    }

    public LimitedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LimitedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getTag() == null) {
            setChildHeight(getChildAt(0).getMeasuredHeight());
            try {
                int heightSize = MeasureSpec.getSize(heightMeasureSpec);
                if (heightSize > childHeight && childHeight > 0) {
                    CardProfile.extraSpace += heightSize - childHeight;
                    heightSize = childHeight;
                } else if (childHeight > heightSize && CardProfile.extraSpace > 0) {
                    if (childHeight - heightSize >= CardProfile.extraSpace) {
                        heightSize += CardProfile.extraSpace;
                        heightSize = Math.min(childHeight, heightSize);
                        CardProfile.extraSpace = 0;
                    } else {
                        CardProfile.extraSpace -= (childHeight - heightSize);
                        heightSize = childHeight;
                    }
                }
                if (childHeight > 0) {
                    setTag("Adjusted");
                    CardProfile.updateScrollStatus(getId());
                }
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.getMode(heightMeasureSpec));
                ViewGroup.LayoutParams test = getLayoutParams();
                test.height = heightSize;
                setLayoutParams(test);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setChildHeight(int childHeight) {
        this.childHeight = childHeight;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
        System.out.println(scrollable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollable) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (scrollable) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}