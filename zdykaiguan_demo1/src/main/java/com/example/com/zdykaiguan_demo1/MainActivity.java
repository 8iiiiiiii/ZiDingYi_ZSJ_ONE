package com.example.com.zdykaiguan_demo1;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textContent = editText.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(MainActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                editText.setText("");
                Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, null);
               imageView.setImageBitmap(mBitmap);
            }
        });
    }
}
