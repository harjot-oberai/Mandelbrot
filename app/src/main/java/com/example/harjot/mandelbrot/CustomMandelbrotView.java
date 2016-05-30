package com.example.harjot.mandelbrot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Harjot on 31-May-16.
 */
public class CustomMandelbrotView extends View {

    double xmin = -1;
    double ymin = -1;
    int scale = 25;
    int x;
    int y;
    int i;
    double xt;
    double cx;
    double cy;
    String color;
    Paint drawPaint;

    public CustomMandelbrotView(Context context) {
        super(context);
        init();
    }

    public CustomMandelbrotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomMandelbrotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        drawPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int midx = canvas.getWidth() / 2;
        int midy = canvas.getHeight() / 2;
        midx -= 400;
        midy -= 400;
        for (x = 0; x < 100; x++) {
            for (y = 0; y < 100; y++) {
                i = 0;
                cx = xmin + ((float) x / (float) scale);
                cy = ymin + ((float) y / (float) scale);
                double zx = 0;
                double zy = 0;
                do {
                    xt = zx * zy;
                    zx = zx * zx - zy * zy + cx;
                    zy = 2 * xt + cy;
                    i++;
                }
                while (i < 255 && (zx * zx + zy * zy) < 4);
                color = Integer.toHexString(i);
                String tmp;
                if (color.length() < 2) {
                    tmp = "#" + color + color + color + color + color + color;
                } else {
                    tmp = "#" + color + color + color;
                }
                drawPaint.setColor(Color.parseColor(tmp));
                canvas.drawRect(midx + (x * 8), midy + (y * 8), midx + (x * 8) + 8, midy + (y * 8) + 8, drawPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("TOUCH", "DOWN");
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            zoom(event.getX(), event.getY());
            Log.d("TOUCH", event.getX() + " UP " + event.getY());
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void zoom(float x, float y) {
        xmin = xmin + ((float) Math.floor(((float) x / (float) 8)) / (float) scale);
        ymin = (-1 * ((float) Math.floor(((float) y / (float) 8)) / (float) scale)) + ((float) 100 / (float) scale) + ymin;
        scale = scale * 2;
        Log.d("SCALE", scale + ":");
        invalidate();
    }

}
