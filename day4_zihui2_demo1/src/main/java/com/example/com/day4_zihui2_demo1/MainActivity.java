package com.example.com.day4_zihui2_demo1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Canvas canvas;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(new DrawView(this));
        setContentView(new MyView(this));

    }

    class DrawView extends View{
        public DrawView(Context context) {
            this(context,null);
        }

        public DrawView(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs,0);
        }

        public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initCavus();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(150,100);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.RED);
            paint.setColor(Color.GREEN);
            //消除锯齿
            paint.setAntiAlias(true);
            canvas.drawCircle(30,50,30,paint);

        }
    }

    private void initCavus() {
        paint = new Paint();
    }
}
