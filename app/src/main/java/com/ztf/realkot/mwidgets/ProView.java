package com.ztf.realkot.mwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.ztf.realkot.R;
import com.ztf.realkot.app.App;

/**
 * @author ztf
 * @date 2019/9/9
 */
public class ProView extends View {
    int innerColor;
    int outerColor;
    float spaceWidth;
    String text;
    float textSize;
    Paint paintIn;
    //
    Paint paintOut;
    SweepGradient sweepGradient;
    Matrix sweepMatrix;
    //
    Paint paintText;
    LinearGradient gradient;
    Matrix textMatrix;
    RectF rectF;
    float customSize;

    //
    public ProView(Context context) {
        super(context);
        init(context, null);
    }

    public ProView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ProView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProView);
        innerColor = array.getColor(R.styleable.ProView_inner_color, Color.RED);
        outerColor = array.getColor(R.styleable.ProView_outer_color, Color.RED);
        spaceWidth = array.getDimension(R.styleable.ProView_space_width, 5);
        text = array.getString(R.styleable.ProView_text);
        textSize = array.getDimension(R.styleable.ProView_text_size, 10);
        array.recycle();
        paintIn = new Paint();
        paintIn.setColor(innerColor);
        paintIn.setStyle(Paint.Style.FILL);

        paintOut = new Paint();
        paintOut.setColor(outerColor);
        paintOut.setStyle(Paint.Style.STROKE);
        paintOut.setStrokeWidth(spaceWidth);
        sweepGradient = new SweepGradient(0, 0, gradientColor, new float[]{0, 0.5f, 1});
        paintOut.setShader(sweepGradient);
        sweepMatrix = new Matrix();
    }

    int from = 0;
    int to = 5;
    int[] gradientColor = new int[]{Color.RED, Color.GREEN, Color.BLUE};
    float textWidth;
    float mTranslate;
    float distance;
    float baseline;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (customSize == 0) {
            float wid = getMeasuredWidth();
            float hei = getMeasuredHeight();
            customSize = Math.min(wid, hei);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cx = customSize / 2;
        float radiusWidth = cx - 2 * spaceWidth;
        canvas.drawCircle(cx, cx, radiusWidth, paintIn);
        if (to >-1) {
            to += 2;
            float angle= to%368f;
            sweepMatrix.setTranslate(cx,cx);
            sweepGradient.setLocalMatrix(sweepMatrix);
//            canvas.rotate(-90,cx,cx);
            canvas.drawArc(rectF, 0, angle, false, paintOut);
        }

        baseline = rectF.centerY() + distance;
        if (paintText == null) {
            paintText = new Paint();
            paintText.setTextSize(textSize);
            Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
            distance = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
            textWidth = paintText.measureText(text);
            gradient = new LinearGradient(0, 0, textWidth, 0, gradientColor, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
            paintText.setShader(gradient);
            paintText.setStyle(Paint.Style.FILL);
            paintText.setTextAlign(Paint.Align.CENTER);
            textMatrix = new Matrix();

        }
        canvas.drawText(text, rectF.centerX(), baseline, paintText);
        if (textMatrix != null) {
            mTranslate += textWidth / 10;
            if (mTranslate > 2 * textWidth) {
                mTranslate = -textWidth;
            }
            textMatrix.setTranslate(mTranslate, 0);
            gradient.setLocalMatrix(textMatrix);
            postInvalidateDelayed(100);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        rectF = new RectF(50, 50, customSize - 50, customSize - 50);
    }

    long start;
    long curTime;
    boolean pressed;

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressed = true;
                start = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                pressed = true;
                curTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                pressed = false;
                curTime = System.currentTimeMillis();
                if (curTime - start < 500) {
                    clickListener.onClick(this);
                } else {
                    longClickListener.onLongClick(this);
                }
                performClick();
                break;
        }

        return true;
    }

    OnClickListener clickListener;
    OnLongClickListener longClickListener;

    public void setListener(OnClickListener clickListener, OnLongClickListener longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }
}
