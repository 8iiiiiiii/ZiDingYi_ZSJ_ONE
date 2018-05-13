package com.example.com.zdykaiguan_demo1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 老赵的拯救者 on 2018/5/13.
 */

public class MyView extends View implements View.OnClickListener, View.OnTouchListener {

    private Bitmap background;
    private Bitmap button;
    private Paint paint;
    boolean isdrop;
    boolean ischeck;
    int num = 0;
    private int leftwidth;
    //起始跟终点的位置
    int first; int last;

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
        background = BitmapFactory.decodeResource(getResources(), R.drawable.switchh);
        button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        paint = new Paint();
        leftwidth = background.getWidth() - button.getWidth();
        setOnClickListener(this);
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(background.getWidth(),background.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制两张图
        canvas.drawBitmap(background,0,0,paint);
        canvas.drawBitmap(button,num,0,paint);

    }

    //点击移动开关
    @Override
    public void onClick(View view) {
        if(!isdrop){
            if(ischeck){
                num = 0;
            }else{
                num = leftwidth;
            }
            ischeck = !ischeck;
        }

     //重新绘制的方法
     invalidate();
    }
    //滑动移动开关
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                //得到按下的坐标
               first = last = (int) motionEvent.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //得到移动的坐标
                int movex = (int) motionEvent.getX();
                //得到移动多少的距离
                int i = movex - last;
                num = num + i;
                last = num;
                break;
            case MotionEvent.ACTION_UP:
                int butwid = button.getWidth();
                int swhwid = background.getWidth();
                int newWidth = num + butwid / 2;
                if(newWidth <= swhwid / 2){
                    num = 0;
                }else if(newWidth > swhwid / 2){
                    num = leftwidth;
                }


                if((first - motionEvent.getX())>1){
                    if(newWidth <= swhwid / 2){
                        num = 0;
                    }else if(newWidth > swhwid / 2){
                        num = leftwidth;
                    }
                    invalidate();
                    return true;
                }
                break;

        }
        flush();
        return false;
    }

    private void flush() {
        //限制移动的坐标
        if(num < 0 ){
            num = 0;
        }else if(num >= leftwidth){
            num = leftwidth;
        }
        invalidate();
    }

    private void changestatus() {
        if(ischeck){
            num = leftwidth;
        }else{
            num = 0;
        }
    }
}
