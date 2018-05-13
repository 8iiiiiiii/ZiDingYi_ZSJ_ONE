package com.bwei.zsj.flowlayoutdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bwei.jiangbikuan.flowlayoutdemo.R;

public class SearchView extends LinearLayout{

    private EditText input;

    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    //设置监听
    public interface OnSearchViewListener{
        void onClickSearch(View view);

        void onTextChange(CharSequence s);
    }
    //声明监听
    private OnSearchViewListener listener;
    //提供监听方法
    public void setOnSearchViewListener(OnSearchViewListener listener){
        this.listener = listener;
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.search_title,this);
        //找到控件
        input = view.findViewById(R.id.et_input);
        Button btn_search = view.findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClickSearch(v);
                }
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(listener != null){
                    listener.onTextChange(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //提供取值的方法
    public String getInputText(){
        return input.getText().toString();
    }
    //提供赋值的方法
    public void setInputText(String string){
        input.setText(string);
    }

}
