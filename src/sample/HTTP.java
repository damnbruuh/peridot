package sample;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;


public class HTTP extends Thread {

    private String destination;

    private CloseableHttpClient client;
    private HttpGet request;

    public HTTP(String destination, Integer timeout) {

        if(destination.startsWith("http://") || destination.startsWith("https://")) {

            this.destination = destination;

        }

        else {

            this.destination = destination.replaceAll(destination, "http://" + destination);

        }

        this.destination = "http://" + destination;

        client = HttpClients.createDefault();
        request = new HttpGet(this.destination);

        // TODO: maybe add incomplete headers?

        // TODO: org.apache.hc.client5.http.ClientProtocolException:
        // TODO: fix exception, find out way to force http:// into string destination
        // ^^ fixed for now

    }

    public void run() {

        try {

            client.execute(request);

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
}
