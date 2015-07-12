package com.example.makarsamsonov.testbitprofi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class FriendsActivity extends Activity {

    private ArrayList<UserInfo> items;
    private UsersInfoAdapter itemsAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        Button postBtn = (Button) this.findViewById(R.id.sendPostBtn);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostActivity.userIdForPost = VKWrapper.getInstance().getCurrentUserId();
                Intent intent = new Intent(FriendsActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.usersList);
        items = new ArrayList<UserInfo>();
        itemsAdapter = new UsersInfoAdapter(this, items);
        listView.setAdapter(itemsAdapter);

        itemsAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersInfoAdapter.ViewHolder holder = (UsersInfoAdapter.ViewHolder) v.getTag();
                PostActivity.userIdForPost = holder.id.getText().toString();
                Intent intent = new Intent(FriendsActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        VKWrapper.getInstance().getFriends(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    initUsersList(response.json.getJSONObject("response").getJSONArray("items"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VKError error) {
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            }
        });
    }

    private void initUsersList(JSONArray usersInfo)
    {
        items.clear();
        for(int i = 0; i < usersInfo.length(); i++)
        {
            try{
                String id = usersInfo.getJSONObject(i).getString("id");
                String name = usersInfo.getJSONObject(i).getString("first_name") + " " + usersInfo.getJSONObject(i).getString("last_name");
                String photoUrl = usersInfo.getJSONObject(i).getString("photo_100");
                items.add(new UserInfo(id, name, photoUrl));
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        itemsAdapter.notifyDataSetChanged();
    }
}
