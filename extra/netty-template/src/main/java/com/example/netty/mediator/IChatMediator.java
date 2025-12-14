package com.example.netty.mediator;

public interface IChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}
