package main;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;


public class HTTP extends Thread {

    private String destination;

    private CloseableHttpClient client;
    private HttpGet request;

    public HTTP(String destination) {

        if(destination.startsWith("http://") || destination.startsWith("https://")) {

            this.destination = destination;

        }

        else {

            this.destination = destination.replaceAll(destination, "http://" + destination);

        }

        this.destination = "http://" + destination;

        client = HttpClients.createDefault();
        request = new HttpGet(this.destination);

    }

    public void run() {

        try {

            client.execute(request);

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
}
