package com.bwei.zsj.flowlayoutdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.jiangbikuan.flowlayoutdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] mVals = new String[]{"Java", "Android", "iOS", "Python",
            "Mac OS", "PHP", "JavaScript", "Objective-C",
            "Groovy", "Pascal", "Ruby", "Go", "Swift"};
    private LayoutInflater mInflater;
    private FlawLayout mFlowlayout;
    private SharedPreferences mPref;
    public SearchView searchView;


    public static final String EXTRA_KEY_TYPE = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private String mType;
    private List<String> mHistoryKeywords;
    private LinearLayout mSearchHistoryLl;
    private ListView listView;
    private Button clear_history_btn;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFlowView();
        initHistoryView();
    }

    private void initHistoryView() {
        searchView = findViewById(R.id.searchView);

        //获取SharedPreferences
        mPref = getSharedPreferences("input", MODE_PRIVATE);
        mType = getIntent().getStringExtra(EXTRA_KEY_TYPE);
        String keyword = getIntent().getStringExtra(EXTRA_KEY_KEYWORD);
        if (!TextUtils.isEmpty(keyword)) {
            searchView.setInputText(keyword);
        }
        //记录文本
        mHistoryKeywords = new ArrayList<String>();
        searchView.setOnSearchViewListener(new SearchView.OnSearchViewListener() {
            @Override
            public void onClickSearch(View view) {
                String keywords = searchView.getInputText();
                if(!TextUtils.isEmpty(keywords)){
                    Toast.makeText(MainActivity.this, keywords + "添加成功", Toast.LENGTH_LONG).show();
                    save();
                }else{
                    Toast.makeText(MainActivity.this, "请输入搜索信息", Toast.LENGTH_LONG).show();
                }
                save();
            }

            @Override
            public void onTextChange(CharSequence s) {
                if (s.length() == 0) {
                    if (mHistoryKeywords.size() > 0) {
                        mSearchHistoryLl.setVisibility(View.VISIBLE);
                    } else {
                        mSearchHistoryLl.setVisibility(View.GONE);
                    }
                } else {
                    mSearchHistoryLl.setVisibility(View.GONE);
                }
            }
        });
        initSeachHistory();
    }

    /*
        初始化搜索历史记录
    */
    private void initSeachHistory() {
        mSearchHistoryLl = findViewById(R.id.search_history_ll);
        listView = findViewById(R.id.listView);
        clear_history_btn = findViewById(R.id.clear_history_btn);
        clear_history_btn.setOnClickListener(this);

        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                list.add((String) o);
            }
            mHistoryKeywords = list;
        }
        if(mHistoryKeywords.size() > 0){
            mSearchHistoryLl.setVisibility(View.VISIBLE);
        }else{
            mSearchHistoryLl.setVisibility(View.GONE);
        }
        //适配器
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_search_history, mHistoryKeywords);
        listView.setAdapter(arrayAdapter);
        //条目点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setInputText(mHistoryKeywords.get(position));
                mSearchHistoryLl.setVisibility(View.GONE);
            }
        });
        //刷新适配器
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear_history_btn:
               clearHistory();
                break;
        }
    }
    //清空历史记录
    private void clearHistory() {
        mPref = getSharedPreferences("input",MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(KEY_SEARCH_HISTORY_KEYWORD).commit();
        //清空集合数据
        mHistoryKeywords.clear();
        //刷新适配器
        arrayAdapter.notifyDataSetChanged();
        mSearchHistoryLl.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, "清楚搜索历史成功", Toast.LENGTH_LONG).show();
    }
    //储存历史记录
    private void save() {
        String text = searchView.getInputText();
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if(!TextUtils.isEmpty(text) && !(oldText.contains(text))){
            //判断集合的长度，最多保存条目
            if(mHistoryKeywords.size() > 5){
                return;
            }else{
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString(KEY_SEARCH_HISTORY_KEYWORD, text + "-->" + oldText);
                editor.commit();
                mHistoryKeywords.add(0,text);
            }
        }
        //刷新适配器
        arrayAdapter.notifyDataSetChanged();
    }

    /*
        initFlowView和initData与流式布局相关
    */
    private void initFlowView() {
        mInflater = LayoutInflater.from(this);
        mFlowlayout = findViewById(R.id.flowlayout);
        //初始化数据
        initData();
    }

    private void initData() {
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.search_lable_tv, mFlowlayout, false);
            tv.setText(mVals[i]);
            final String str = tv.getText().toString();
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setInputText(str);
                }
            });
            mFlowlayout.addView(tv);
        }
    }


}
