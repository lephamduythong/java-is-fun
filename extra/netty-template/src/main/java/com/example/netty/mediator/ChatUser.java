package com.example.netty.mediator;

public class ChatUser extends User {

    public ChatUser(IChatMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String message) {
        System.out.println(this.name + " sent: " + message);
        mediator.sendMessage(message, this);
    }

    @Override
    public void receive(String message, String from) {
        System.out.println(this.name + " recieved from " + from + ": " + message);
    }
}

