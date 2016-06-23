package com.addbean.aviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by AddBean on 2016/2/13.
 */
public class AWaveLayout extends LinearLayout {
    private Context mContext;
    private Paint mPaint = new Paint();
    private float mCircleCenterX = 0;
    private float mCircleCenterY = 0;
    private int mCurrentRadius = 0;
    private ETouchState mState = ETouchState.TOUCH_END;

    private enum ETouchState {
        TOUCH_DOWN,
        TOUCH_MOVE,
        TOUCH_UP,
        TOUCH_END
    }

    private static class MConfig {
        public final static int COLOR_DEF = 0xeefeeeee;
        public final static int COLOR_PRESSED = 0xefff0000;

        public final static int SPEED_PRESSING = 5;
        public final static int SPEED_RELEAST = 50;
    }

    public AWaveLayout(Context context) {
        super(context);
        this.mContext = context;
        initCircle();
    }

    public AWaveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initCircle();
    }

    public AWaveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initCircle();
    }


    private void initCircle() {
        mCircleCenterX = 0;
        mCircleCenterY = 0;
        mState = ETouchState.TOUCH_END;
        mCurrentRadius = 0;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawBackground(canvas);
        super.dispatchDraw(canvas);
    }

    private void drawBackground(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(MConfig.COLOR_DEF);
        canvas.drawCircle(mCircleCenterX, mCircleCenterY, mCurrentRadius, mPaint);
        if(mState== ETouchState.TOUCH_UP){
            mCurrentRadius = mCurrentRadius + MConfig.SPEED_RELEAST;
        }else{
            mCurrentRadius = mCurrentRadius + MConfig.SPEED_PRESSING;
        }
        if (mState != ETouchState.TOUCH_END) {
            invalidate();
        }
        int maxLenght = (getMeasuredHeight() > getMeasuredWidth()) ? getMeasuredHeight() : getMeasuredWidth();
        int maxRadius=0;
        if(getMeasuredHeight() > getMeasuredWidth()){
            maxRadius=(int)((maxLenght/2>mCircleCenterY)?(maxLenght-mCircleCenterY):mCircleCenterY);
        }else{
            maxRadius=(int)((maxLenght/2>mCircleCenterX)?(maxLenght-mCircleCenterX):mCircleCenterX);
        }
        if (mCurrentRadius > maxRadius) {
            initCircle();
            canvas.drawColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initCircle();
                mState = ETouchState.TOUCH_DOWN;
                mCircleCenterX = event.getX();
                mCircleCenterY = event.getY();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                mState = ETouchState.TOUCH_MOVE;
                break;
            case MotionEvent.ACTION_UP:
                mState = ETouchState.TOUCH_UP;
                break;
        }
        return super.onTouchEvent(event);
    }



}
