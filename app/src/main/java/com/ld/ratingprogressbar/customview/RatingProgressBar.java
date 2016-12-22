package com.ld.ratingprogressbar.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ld.ratingprogressbar.R;


/**
 * 类似于评级的progress
 *
 * @author 梁栋 510655711@qq.com
 */
public class RatingProgressBar extends View {

    private Paint mPbItemPaint;

    // 半径
    private float mRadius;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;

    // 当前进度
    private int mProgress;

    private Bitmap bmCheck;
    private Bitmap bmUnCheck;
    private Matrix matrix;

    public RatingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //获取attrs中的属性并初始化一些数据
    private void init(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.rpb, 0, 0);

        //半径如果没设置就以宽高当中比较小的折半作为半径
        float mRadiusTemp = 0;
        if (getWidth() < getHeight()) {
            mRadiusTemp = getWidth() / 2;
        } else if (getWidth() >= getHeight()) {
            mRadiusTemp = getHeight() / 2;
        }
        mRadius = typeArray.getDimension(R.styleable.rpb_radius, mRadiusTemp);

        int checkItem = typeArray.getResourceId(R.styleable.rpb_checkItem, R.mipmap.ic_launcher);
        int unCheckItem = typeArray.getResourceId(R.styleable.rpb_unCheckItem, R.mipmap.ic_launcher);
        bmCheck = BitmapFactory.decodeResource(getResources(), checkItem);
        bmUnCheck = BitmapFactory.decodeResource(getResources(), unCheckItem);

        mPbItemPaint = new Paint();
        mPbItemPaint.setAntiAlias(true);
        mPbItemPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        for (int i = 0; i < 60; i++) {
            matrix.setRotate(6 * i);
            matrix.postTranslate(getItemX(i), getItemY(i));
            canvas.drawBitmap(bmUnCheck, matrix, mPbItemPaint);
        }

        if (mProgress > 0) {
            int progress = (mProgress * 60) / 100;
            for (int i = 0; i < progress; i++) {
                matrix.setRotate(6 * i);
                matrix.postTranslate(getItemX(i), getItemY(i));
                canvas.drawBitmap(bmCheck, matrix, mPbItemPaint);
            }
        }
    }

    private int getItemX(int progress) {
        int ao = progress * 6 - 90;
        Double x = mXCenter + mRadius * Math.cos(ao * Math.PI / 180);
        return x.intValue();
    }

    private int getItemY(int progress) {
        int ao = progress * 6 - 90;
        Double y = mYCenter + mRadius * Math.sin(ao * Math.PI / 180);
        return y.intValue();
    }

    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }
}