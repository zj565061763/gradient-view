package com.sd.lib.gradientview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class FGradientView extends View
{
    private final Paint mPaint;
    private LinearGradient mGradient;
    private Orientation mOrientation = Orientation.Horizontal;

    private int mColorProgressStart;
    private int mColorProgressEnd;

    private int mColorNormalStart;
    private int mColorNormalEnd;

    private float mProgress = 0.0f;

    private Integer mColorEval;

    public FGradientView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mPaint = new Paint();
    }

    /**
     * 设置正常颜色
     *
     * @param start
     * @param end
     */
    public void setColorNormal(int start, int end)
    {
        if (mColorNormalStart != start || mColorNormalEnd != end)
        {
            mColorNormalStart = start;
            mColorNormalEnd = end;
            calculateColorEval();
            invalidate();
        }
    }

    /**
     * 设置进度颜色
     *
     * @param start
     * @param end
     */
    public void setColorProgress(int start, int end)
    {
        if (mColorProgressStart != start || mColorProgressEnd != end)
        {
            mColorProgressStart = start;
            mColorProgressEnd = end;
            calculateColorEval();
            invalidate();
        }
    }

    /**
     * 设置进度
     *
     * @param progress [0-1]
     */
    public void setProgress(float progress)
    {
        if (progress < 0)
            progress = 0;

        if (progress > 1.0f)
            progress = 1.0f;

        if (mProgress != progress)
        {
            mProgress = progress;
            calculateColorEval();
            invalidate();
        }
    }

    /**
     * 设置渐变方向{@link Orientation}
     *
     * @param orientation
     */
    public void setOrientation(Orientation orientation)
    {
        if (orientation == null)
            throw new IllegalArgumentException("orientation is null");

        if (mOrientation != orientation)
        {
            mOrientation = orientation;
            invalidate();
        }
    }

    /**
     * 返回当前进度
     *
     * @return
     */
    public float getProgress()
    {
        return mProgress;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        updateLinearGradient();
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    private void updateLinearGradient()
    {
        if (mColorEval == null)
            calculateColorEval();

        final int width = mOrientation == Orientation.Horizontal ? getWidth() : 0;
        final int height = mOrientation == Orientation.Vertical ? getHeight() : 0;

        mGradient = new LinearGradient(0, 0, width, height,
                new int[]{mColorProgressStart, mColorEval, mColorNormalEnd},
                new float[]{0.0f, mProgress, 1.0f}, Shader.TileMode.CLAMP);

        mPaint.setShader(mGradient);
    }

    private void calculateColorEval()
    {
        final float progress = mProgress;

        final int colorNormal = eval(mColorNormalStart, mColorNormalEnd, progress);
        final int colorProgress = eval(mColorProgressStart, mColorProgressEnd, progress);
        final int colorEval = eval(colorNormal, colorProgress, progress);

        mColorEval = colorEval;
    }

    private static int eval(int startValue, int endValue, float fraction)
    {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        int currentA = (startA + (int) (fraction * (endA - startA))) << 24;
        int currentR = (startR + (int) (fraction * (endR - startR))) << 16;
        int currentG = (startG + (int) (fraction * (endG - startG))) << 8;
        int currentB = startB + (int) (fraction * (endB - startB));

        return currentA | currentR | currentG | currentB;
    }

    public enum Orientation
    {
        /**
         * 水平方向
         */
        Horizontal,
        /**
         * 竖直方向
         */
        Vertical
    }
}
