package com.addbean.aviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.addbean.autils.tools.ToolsUtils;
import com.addbean.aviews.R;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by AddBean on 2016/3/18.
 */
public class ATimeIconView extends View {
    private int mColorId = R.color.autils_color_blue;
    private Context mContext;
    private int DP = 1;
    private final double HOUR_ACCURACY = 12 * 60;//精确到分钟
    private final double MIN_ACCURACY = 60;//精确到分钟
    private int mMin = 0;
    private int mHour = 0;
    private boolean mAnimEnble = false;
    private int MIN = 0;
    private int HOUR = 0;
    private Calendar mCalendar;
    private final int SPEED=1;

    public ATimeIconView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public ATimeIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public ATimeIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        DP = ToolsUtils.dpConvertToPx(mContext, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawHour(canvas, mHour, mMin);
        drawMinute(canvas, mMin);
        super.onDraw(canvas);
    }
    private int mCount = 0;

    private void drawBg(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(mColorId));
        int lineWidth = 2 * DP;
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);
        int radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2 - lineWidth;
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius, paint);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius / 10, paint);
        if (mAnimEnble) {
            if ( mMin==MIN) {
                mAnimEnble = false;
            } else {
                mCount++;
                if(mCount>SPEED){
                    mCount=0;
                    mCalendar.add(Calendar.MINUTE, 1);
                    mMin = mCalendar.get(Calendar.MINUTE);
                }
                postInvalidate();
            }

        }
    }

    private void drawHour(Canvas canvas, int hour, int min) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(getResources().getColor(mColorId));
        int lineWidth = 4 * DP;
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);
        int radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2 - lineWidth;
        Point circlePoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        Point startPoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius / 10);
        Point endPoint = new Point(getMeasuredWidth() / 2, radius / 3);
        int radius1 = Math.abs(startPoint.y - circlePoint.y);
        int radius2 = Math.abs(endPoint.y - circlePoint.y);
        double time = hour * 60 + min;
        startPoint.x = (int) (radius1 * Math.cos(time * 2 * Math.PI / HOUR_ACCURACY - Math.PI / 2)) + circlePoint.x;
        startPoint.y = (int) (radius1 * Math.sin(time * 2 * Math.PI / HOUR_ACCURACY - Math.PI / 2)) + circlePoint.y;
        endPoint.x = (int) (radius2 * Math.cos(time * 2 * Math.PI / HOUR_ACCURACY - Math.PI / 2)) + circlePoint.x;
        endPoint.y = (int) (radius2 * Math.sin(time * 2 * Math.PI / HOUR_ACCURACY - Math.PI / 2)) + circlePoint.y;
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
    }

    private void drawMinute(Canvas canvas, int min) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(getResources().getColor(mColorId));
        int lineWidth = 4 * DP;
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);
        int radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2 - lineWidth;
        Point circlePoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        Point startPoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2 - radius / 10);
        Point endPoint = new Point(getMeasuredWidth() / 2, radius / 6);
        int radius1 = Math.abs(startPoint.y - circlePoint.y);
        int radius2 = Math.abs(endPoint.y - circlePoint.y);
        double time = min;
        startPoint.x = (int) (radius1 * Math.cos(time * 2 * Math.PI / MIN_ACCURACY - Math.PI / 2)) + circlePoint.x;
        startPoint.y = (int) (radius1 * Math.sin(time * 2 * Math.PI / MIN_ACCURACY - Math.PI / 2)) + circlePoint.y;
        endPoint.x = (int) (radius2 * Math.cos(time * 2 * Math.PI / MIN_ACCURACY - Math.PI / 2)) + circlePoint.x;
        endPoint.y = (int) (radius2 * Math.sin(time * 2 * Math.PI / MIN_ACCURACY - Math.PI / 2)) + circlePoint.y;
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
    }

    public void setTime(Date date, boolean animEnble) {
        this.mAnimEnble = animEnble;
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        HOUR = mCalendar.get(Calendar.HOUR);//12小时进制；
        MIN = mCalendar.get(Calendar.MINUTE);
        if (!animEnble) {
            mHour = HOUR;
            mMin = MIN;
            postInvalidate();
        } else {
            mMin=0;
            mHour=HOUR;
            mCalendar.set(Calendar.MINUTE,0);
            postInvalidate();
        }
    }

}
