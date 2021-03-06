package com.kaze.jailbreakpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.core.content.res.ResourcesCompat;

public class GridView extends View {
    BoardView.Boundaries boundaries;
    int rows, cols;
    float sectionWidth;
    int tintDark;
    float gridItemSize = 0;
    Paint mPaint;

    public GridView(Context context) {
        super(context);
        this.setWillNotDraw(false);
        rows = (int) Math.ceil((float)Helper.getNumRows()/3);
        cols = Helper.getNumColumns();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tintDark = ResourcesCompat.getColor(getResources(), R.color.darkTint, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);

        final ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
        final GridView reference = this;
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    updateSize();
                    reference.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    public void updateSize(){
        this.sectionWidth = this.getWidth();
        gridItemSize = sectionWidth/cols;
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setColor(tintDark);
        mPaint.setStrokeWidth(gridItemSize / 20);
        // only do rows-1 because we don't want users to create bricks on top row.
        for (int i = 0; i < rows-1; ++i) {
            int y = (int) ((i+1) * gridItemSize);
            canvas.drawLine(0, y, sectionWidth, y, mPaint);
        }

        // draw the columns
        float height = this.getHeight() - gridItemSize;
        for (int i = 0; i < cols; ++i) {
            int x = (int) (i * gridItemSize);
            mPaint.setColor(tintDark);
            canvas.drawLine(x, gridItemSize, x, height+gridItemSize, mPaint);
        }
    }
}
