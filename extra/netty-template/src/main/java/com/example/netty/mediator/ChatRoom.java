package com.example.netty.mediator;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements IChatMediator {

    private List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            // không gửi lại cho chính mình
            if (user != sender) {
                user.receive(message, sender.getName());
            }
        }
    }
}

