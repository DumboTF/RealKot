package com.ztf.realkot.mwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.ztf.realkot.R;

/**
 * @author ztf
 * @date 2019/9/10
 */
public class LineView extends View {
    int lineColor;
    float lineWidth;
    boolean isAlias;
    Paint linePaint;
    Path path;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineView);
        lineColor = array.getColor(R.styleable.LineView_line_color, Color.RED);
        lineWidth = array.getDimension(R.styleable.LineView_line_width, 5f);
        isAlias = array.getBoolean(R.styleable.LineView_is_alias, false);
        array.recycle();

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setAntiAlias(isAlias);
        linePaint.setTextSize(30f);
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
        drawArc(canvas);
        drawArc2(canvas);
    }

    private void drawRect(Canvas canvas) {
        path.moveTo(10, 10);
        path.lineTo(300, 10);
        path.lineTo(300, 300);
        path.lineTo(10, 300);
        path.lineTo(10, 20);
        canvas.drawPath(path, linePaint);
    }

    private void drawArc(Canvas canvas) {
        RectF rectF = new RectF(350, 10, 600, 310);
        path.reset();
        path.arcTo(rectF, 0, 180);
//        path.close();
        canvas.drawPath(path, linePaint);
        canvas.drawTextOnPath("画的什么",path,-10,-10,linePaint);
    }
    private void drawArc2(Canvas canvas) {
        RectF rectF = new RectF(650,100,900,310);
        path.reset();
        path.moveTo(620,100);
        path.arcTo(rectF,0,180,false);
        path.close();
        canvas.drawPath(path,linePaint);
    }
}
