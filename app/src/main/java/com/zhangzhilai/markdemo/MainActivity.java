package com.zhangzhilai.markdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener{

    private Button mMarkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
    }

    private void initViews() {
        mMarkButton = (Button)findViewById(R.id.mark_button);

    }

    private void initListeners() {
        mMarkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mark_button:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MarkEditActivity.class);
                startActivity(intent);
                break;

        }
    }
}
