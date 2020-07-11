package sample;

public class UDPThreadable{

    private Integer threads;
    private String packet_message;
    private Integer port;
    private Integer timeout;
    private String destination;

    public UDPThreadable(Integer threadnum, String packetmsg, Integer portnum, Integer timeoutms, String dest) {

        // TODO: add UDP socket timeout in UDP class

        threads = threadnum;
        packet_message = packetmsg;
        port = portnum;
        timeout = timeoutms;
        destination = dest;
    }

    public void startAttack() {

        for (int counter = 0; counter < threads; counter++) {

            UDP udp_thread = new UDP(port, destination, timeout, packet_message);
            udp_thread.start();

        }
    }
}
