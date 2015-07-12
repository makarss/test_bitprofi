package com.example.makarsamsonov.testbitprofi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;


public class PostActivity extends Activity {

    public static String userIdForPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button backBtn = (Button) this.findViewById(R.id.backBtn);
        Button sendPostBtn = (Button) this.findViewById(R.id.sendPostBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textInput = (TextView) PostActivity.this.findViewById(R.id.postTextView);
                VKWrapper.getInstance().wallPost(userIdForPost, textInput.getText().toString());
                textInput.setText("");
            }
        });
    }
}
