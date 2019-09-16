package com.ztf.realkot.mwidgets;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * @author ztf
 * @date 2019/9/10
 */
public class RainView extends View {
    int viewWidth;
    int viewHeight;
    Path path = new Path();
    RectF rectF;
    Paint paintCloud;

    public RainView(Context context) {
        super(context);
    }

    public RainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public RainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paintCloud = new Paint();
        paintCloud.setColor(Color.GRAY);
        paintCloud.setStyle(Paint.Style.FILL);
        paintCloud.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        rectF = new RectF(viewWidth / 4, viewHeight * 3 / 9, viewWidth * 2/ 3, viewHeight * 1 / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = measure(widthMeasureSpec);
        viewHeight = measure(heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.addRoundRect(rectF, viewHeight / 9, viewHeight / 9, Path.Direction.CW);
        path.addCircle((float) (viewWidth * 3.2 / 8), viewHeight * 5/ 15, viewHeight * 1 / 11, Path.Direction.CW);
        path.addCircle((float) (viewWidth * 4.5 / 9), viewHeight * 5 / 15, viewHeight * 1 / 9, Path.Direction.CW);
        canvas.drawPath(path, paintCloud);
    }

    private int measure(int measureSpec) {
        int width;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = 300;
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(width, size);
            }
        }
        return width;


    }
}
