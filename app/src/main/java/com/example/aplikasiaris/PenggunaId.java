package com.example.aplikasiaris;

import androidx.annotation.NonNull;

public class PenggunaId {
        String usersId;
        public <T extends PenggunaId>T withId(@NonNull final String id){
            this.usersId = id;
            return (T) this;
        }
}
