package com.example.com.day4_zihui2_demo1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 老赵的拯救者 on 2018/5/11.
 */

public class MyView extends View implements View.OnClickListener,View.OnTouchListener{

    private Bitmap qQswitch;
    private Bitmap qQbutton;
    private Paint paint;
    boolean ischeck = false;
    boolean isdrop;
    private int num = 0;
    private int newLeft;

    //起始位置
    int first;
    //终点位置
    int last;
    private int myx;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
       qQswitch = BitmapFactory.decodeResource(getResources(),R.drawable.switchh);
       qQbutton = BitmapFactory.decodeResource(getResources(),R.drawable.button);
        //画笔
        paint = new Paint();
        setOnClickListener(this);
        setOnTouchListener(this);
        newLeft = qQswitch.getWidth() - qQbutton.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(qQswitch.getWidth(),qQswitch.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制两张图
        canvas.drawBitmap(qQswitch,0,0,paint);
        canvas.drawBitmap(qQbutton,num,0,paint);

    }

    @Override
    public void onClick(View view) {
            if(ischeck){
            num = 0;
            }else{
             num = newLeft;
            }
            ischeck=!ischeck;

        invalidate();
    }



    private void flushView() {

        if(num<0){
            num = 0;
        }else if(num>newLeft){
            num = newLeft;
        }
        invalidate();
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                first = last = (int) motionEvent.getX();
                // isdrop = false;
                break;
            case MotionEvent.ACTION_MOVE:
                myx = (int) motionEvent.getX();
                int newX = myx - last;
                num = num + newX;
                last = num;
                break;
            case MotionEvent.ACTION_UP:

                int butwid = qQbutton.getWidth();
                int swhwid = qQswitch.getWidth();
                int i = num + butwid / 2;
                if(i <= swhwid / 2){
                    num = 0;
                }else if(i > swhwid / 2){
                    num = newLeft;
                }

                if((first - motionEvent.getX())>1){
                    if(i <= swhwid / 2){
                        num = 0;
                    }else if(i > swhwid / 2){
                        num = newLeft;
                    }
                    invalidate();
                    return true;
                }

                break;
        }
        flushView();
        return false;
    }
}
