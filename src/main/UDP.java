package main;

import java.io.IOException;
import java.net.*;

public class UDP extends Thread {

    private Integer port;
    private String destination;
    private Integer timeout;
    private String packet_message;

    private InetAddress address;

    public UDP(Integer portnum, String dest, Integer timeoutms, String packetmsg) {

        port = portnum;
        timeout = timeoutms;
        packet_message = packetmsg;
        destination = dest;

    }

    public void run() {

        try {

            address = InetAddress.getByName(destination);

        } catch (UnknownHostException e) {

            e.printStackTrace();

        }

        destination = null;

        DatagramPacket packet = new DatagramPacket(packet_message.getBytes(),
                packet_message.getBytes().length,
                address,
                port);

        try {

            DatagramSocket udp_socket = new DatagramSocket(port);
            udp_socket.setSoTimeout(timeout);

            while(true) {

                udp_socket.send(packet);

            }

        } catch (SocketException e) {

            e.printStackTrace();

        } catch (IOException e) {
            
            e.printStackTrace();
            
        }

        destination = null;

    }
}
