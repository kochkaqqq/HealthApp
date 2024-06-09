package com.example.health.webclient.client;

import com.example.health.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserListCallback {
    void onUserListReceived(List<User> users);

    void onFailure(IOException e);
}
