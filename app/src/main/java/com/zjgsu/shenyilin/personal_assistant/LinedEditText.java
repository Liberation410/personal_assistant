package com.zjgsu.shenyilin.personal_assistant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by apple on 2017/5/20.
 */

public class LinedEditText extends EditText{
    private Rect mRect;
    private Paint mPaint;
    public LinedEditText(Context context, AttributeSet attrs) {
        super(context,attrs);
        mPaint=new Paint();
        mPaint.setColor(Color.BLUE);
    }
    @Override
    public void onDraw(Canvas canvas){
        int count=getLineCount();
        Rect r=mRect;
        Paint paint=mPaint;
        for (int i=0;i<count;i++){
            int baseline=getLineBounds(i,r);
            canvas.drawLine(r.left,baseline+5,r.right,baseline+5,paint);
        }
    }
}
