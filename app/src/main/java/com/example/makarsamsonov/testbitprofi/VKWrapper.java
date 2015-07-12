package com.example.makarsamsonov.testbitprofi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.dialogs.VKCaptchaDialog;

import org.json.JSONException;

/**
 * Created by makarsamsonov on 12.07.15.
 */
public class VKWrapper {

    private static String[] sMyScope = new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.NOHTTPS};
    private static VKWrapper instance;
    private static boolean isInited;

    private static String currentUserId;

    public VKWrapper()
    {
        isInited = false;
    }

    public static VKWrapper getInstance()
    {
        if (instance == null)
            instance = new VKWrapper();

        return instance;
    }

    public void init(Context context, VKSdkListener listener)
    {
        isInited = true;

        VKAccessToken access_token = VKAccessToken.tokenFromSharedPreferences(context, "VK_ACCESS_TOKEN");
        VKSdk.initialize(listener, "4986278", access_token);
        authorize();
    }

    public void authorize()
    {
        VKSdk.authorize(sMyScope);
    }

    public void getFriends(VKRequest.VKRequestListener listener)
    {
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "photo_100"));
        request.executeWithListener(listener);
        request.start();
    }

    public void wallPost(String ownerId, String message)
    {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, ownerId, VKApiConst.MESSAGE, message));
        post.start();
    }

    public void setCurrentUserId(String id)
    {
        currentUserId = id;
    }

    public String getCurrentUserId()
    {
        return currentUserId;
    }
}
