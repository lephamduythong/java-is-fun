package com.example.netty.mediator;

public class MediatorDemoMain {

    public static void main(String[] args) {
        IChatMediator chatRoom = new ChatRoom();

        User userAn = new ChatUser(chatRoom, "An");
        User userBinh = new ChatUser(chatRoom, "Binh");
        User userCuong = new ChatUser(chatRoom, "Cuong");

        chatRoom.addUser(userAn);
        chatRoom.addUser(userBinh);
        chatRoom.addUser(userCuong);

        userAn.send("Hello everyone!");
        userBinh.send("Hello An!");
    }
}
