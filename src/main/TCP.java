package main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class TCP extends Thread {

    private Integer port;
    private String destination;
    private Integer timeout;
    private String packet_message;

    public TCP(Integer portnum, String dest, Integer timeoutms, String packetmsg) {

        port = portnum;
        destination = dest;
        timeout = timeoutms;
        packet_message = packetmsg;

    }

    public void run() {

        Socket socket = null;

        try {

            socket = new Socket(destination, port);

        } catch (IOException e) {

            e.printStackTrace();

        }

        try {

            socket.setSoTimeout(timeout);

        } catch (SocketException e) {

            e.printStackTrace();

        }

        OutputStream output = null;

        try {

            output = socket.getOutputStream();

        } catch (IOException e) {

            e.printStackTrace();

        }
        PrintWriter writer = new PrintWriter(output, true);

        while (true) {

            writer.println(packet_message);

        }


    }


}