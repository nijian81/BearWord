package com.prolificinteractive.materialcalendarview.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

/**
 * Span to draw a dot centered under a section of text
 */
public class DotSpan implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 3;
    private RectF rectF;
    private final float radius;
    private final int color;

    /**
     * @see #DotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public DotSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color = 0;
    }

    /**
     * @see #DotSpan(float, int)
     * @see #DEFAULT_RADIUS
     */
    public DotSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color = color;
    }

    /**
     * @see #DotSpan(float, int)
     */
    public DotSpan(float radius) {
        this.radius = radius;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *
     * @param radius radius for the dot
     * @param color color of the dot
     */
    public DotSpan(float radius, int color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        if(color != 0) {
            paint.setColor(color);
        }
        canvas.drawCircle((left + right) / 2, (top + bottom) / 2, radius, paint);
        paint.setColor(oldColor);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.rgb(220, 220, 220));
        //设置画笔为空心
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(17);
        canvas.drawCircle((left + right) / 2, (top + bottom) / 2, radius + 8, paint2);
        //画弧形购买日用品
        Paint paintArc = new Paint();
        paintArc.setAntiAlias(true);
        paintArc.setColor(Color.rgb(32, 126, 92));
        //设置画笔为空心
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setStrokeWidth(17);
        //初始化弧线的参数
        int leftX = (left + right) / 2 - 53;
        int rightX = (left + right) / 2 + 53;
        int upY = (top + bottom) / 2 - 53;
        int downY = (top + bottom) / 2 + 53;
        rectF = new RectF(leftX, upY, rightX, downY);
        canvas.drawArc(rectF, 270, 180, false, paintArc);
    }
}
