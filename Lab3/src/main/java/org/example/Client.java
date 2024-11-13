package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", 1234);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println((String) in.readObject());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a number: ");
            int n = scanner.nextInt();
            out.writeObject(n);

            System.out.println((String) in.readObject());
            for (int i = 0; i < n; i++) {
                System.out.println("Enter message content: ");
                String content = scanner.next();
                Message message = new Message(i, content);
                message.setNumber(i);
                message.setContent(content);
                out.writeObject(message);
            }

            System.out.println((String) in.readObject());
        }
    }
}