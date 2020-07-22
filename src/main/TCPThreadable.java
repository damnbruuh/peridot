package main;

public class TCPThreadable {

    private Integer threads;
    private String packet_message;
    private Integer port;
    private Integer timeout;
    private String destination;

    public TCPThreadable(Integer threadnum, String packetmsg, Integer portnum, Integer timeoutms, String dest) {

        threads = threadnum;
        packet_message = packetmsg;
        port = portnum;
        timeout = timeoutms;
        destination = dest;

    }

    public void startAttack() {

        for (int counter=0; counter<threads; counter++) {

            TCP tcp_thread = new TCP(port, destination, timeout, packet_message);
            tcp_thread.start();

        }

    }

}

