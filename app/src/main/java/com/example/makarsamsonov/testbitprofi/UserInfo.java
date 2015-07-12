package com.example.makarsamsonov.testbitprofi;

/**
 * Created by makarsamsonov on 09.07.15.
 */
public class UserInfo {

    private String id;
    private String name;
    private String photoUrl;

    public UserInfo(String id, String name, String photoUrl)
    {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPhotoUrl()
    {
        return photoUrl;
    }
}
