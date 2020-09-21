package com.example.aplikasiaris;

import androidx.annotation.NonNull;

public class UsersId {
    String usersId;
    public <T extends UsersId>T withId(@NonNull final String id){
        this.usersId = id;
        return (T) this;
    }
}
