package sample;

public class HTTPThreadable {

    private Integer timeout;
    private String destination;
    private Integer threads;

    public HTTPThreadable(Integer timeoutms, String dest, Integer threadnum) {

        timeout = timeoutms;
        destination = dest;
        threads = threadnum;

    }

    public void startAttack() {

        for(int x=0;x<threads;x++) {

            HTTP http_conn = new HTTP(destination, timeout);
            http_conn.start();

        }


    }
}
