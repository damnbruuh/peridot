package main;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private String ip;
    private String url;
    private Integer timeout;
    private String packet_message;
    private Integer port;
    private Integer threads;
    private String method;
    private Boolean useIP;

    @FXML
    private Button lockButton;

    @FXML
    private ComboBox methodBox;

    @FXML
    private Label threadsActive;

    @FXML
    private Button fireButton;

    @FXML
    private TextField urlBox;

    @FXML
    private TextField ipBox;

    @FXML
    private TextField portBox;

    @FXML
    private TextField threadBox;

    @FXML
    private TextField timeoutBox;

    @FXML
    private TextField messageBox;

    @FXML
    private Button stopFiring;

    @FXML
    public Label lockedOnto;

    @FXML
    private Button openLogButton;

    private LoggerController logger;

    private Stage mainStage;

    public MainController() throws IOException {

        mainStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        loader.setController(this);

        mainStage.setScene(new Scene(loader.load(), 682, 580));
        mainStage.setTitle("Peridot");
        mainStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/peridot.png")));
        mainStage.setResizable(false);

        LoggerController logger = new LoggerController(this);

        this.logger = logger;
        this.mainStage = mainStage;

    }

    public void checkTargetUsage() {

        if(url == null && ip == null) {

            useIP = null;

        }
        else if(url != null && ip != null) {

            useIP = null;

        }

        else if(url == null && ip != null){

            useIP = true;

        }

        else if(url != null && ip == null) {

            useIP = false;

        }
    }

    @FXML
    public void lockClicked() throws IOException {

        threadsActive.setText("");

        lockButton.arm();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.10));

        lockButton.setTranslateX(2);
        lockButton.setTranslateY(2);

        pause.setOnFinished(e -> {

            lockButton.setTranslateY(0);
            lockButton.setTranslateX(0);
            lockButton.disarm();
        });

        pause.play();

        if (url == null && ip == null) {

            lockedOnto.setText("Error! No target selected.");
            logger.writeToLogArea("Error! No target selected!");
            useIP = null;

        } else if (url != null && ip != null) {

            lockedOnto.setText("Error! You can only have one target!");
            logger.writeToLogArea("Error! You can only have one target!");
            useIP = null;

        } else if (url == null && ip != null) {

            try {

                boolean ping = InetAddress.getByName(ip).isReachable(2000);

                if (ping == true) {

                    useIP = true;
                    lockedOnto.setText("Target locked on IP. Ping succeeded.");
                    threadsActive.setText("");
                    logger.writeToLogArea("Target locked onto IP. Ping succeeded.");

                } else {

                    useIP = null;
                    lockedOnto.setText("Ping failed. Unable to lock onto IP.");
                    logger.writeToLogArea("Ping failed. Unable to lock onto IP.");

                }
            } catch (UnknownHostException e) {

                lockedOnto.setText("Error! Target does not exist! (UnknownHostException)");
                logger.writeToLogArea("Error! Target does not exist! (UnknownHostException)");
                useIP = null;

            }


        } else if (url != null && ip == null) {

            try {

                boolean ping = InetAddress.getByName(url).isReachable(2000);

                if (ping == true) {

                    useIP = false;
                    lockedOnto.setText("Target locked on URL. Ping succeeded.");
                    logger.writeToLogArea("Target locked on URL. Ping succeeded.");


                } else {
                    useIP = null;
                    lockedOnto.setText("Ping failed. Unable to lock onto URL.");
                    logger.writeToLogArea("Ping failed. Unable to lock onto URL.");
                }
            }
            catch(UnknownHostException e) {

                lockedOnto.setText("Error! Target does not exist! (UnknownHostException)");
                logger.writeToLogArea("Error! Target does not exist! (UnknownHostException)");
                useIP = null;

            }
        }
    }
    @FXML
    public void exitClicked() { System.exit(0); }

    @FXML
    public void fireAction() {

        threadsActive.setText("");

        fireButton.arm();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.10));

        fireButton.setTranslateX(2);
        fireButton.setTranslateY(2);

        pause.setOnFinished(e -> {

            fireButton.setTranslateX(0);
            fireButton.setTranslateY(0);
            lockButton.disarm();
        });

        pause.play();

        if(method == null || port == null || timeout == null || threads == null) {

            lockedOnto.setText("Error! Please enter all options!");
            logger.writeToLogArea("Error! The options required have not been entered.");

            if(url == null && ip == null) {

                threadsActive.setText("Error! Lock onto a target first!");
                logger.writeToLogArea("Error! Lock onto a target first! (URL or IP)");

            }

        }

        else {

            if(url == null && ip == null) {

                threadsActive.setText("Error! Lock onto a target first!");
                logger.writeToLogArea("Error! Lock onto a target first! (URL or IP)");

            }

            else {

                if(useIP == null) {

                    lockedOnto.setText("Error! Ping failed or target unsuccessful.");
                    logger.writeToLogArea("Error! Ping failed or target unsuccessful.");
                    threadsActive.setText("");

                }

                else if(useIP == true) {

                    if(method == "TCP flood") {

                        TCPThreadable tcp = new TCPThreadable(threads, packet_message, port, timeout, ip);
                        logger.writeToLogArea("Started TCP flood. Click on Exit Peridot or Stop Firing to terminate the attack.");
                        logger.writeToLogArea("Using IP: " + ip);
                        logger.writeToLogArea("Using threads: " + threads);
                        logger.writeToLogArea("Using port: " + port);
                        logger.writeToLogArea("Using packet message: " + packet_message);
                        logger.writeToLogArea("Using timeout: " + timeout);
                        tcp.startAttack();

                    }

                    else if(method == "HTTP GET flood") {

                        HTTPThreadable http = new HTTPThreadable(ip, threads);
                        logger.writeToLogArea("Started HTTP GET flood. Click on Exit Peridot or Stop Firing to terminate the attack.");
                        logger.writeToLogArea("Using IP: " + ip);
                        logger.writeToLogArea("Using threads: " + threads);
                        http.startAttack();

                    }

                    else if(method == "UDP flood") {

                        UDPThreadable udp = new UDPThreadable(threads, packet_message, port, timeout, ip);
                        logger.writeToLogArea("Started UDP flood. Click on Exit Peridot or Stop Firing to terminate the attack.");
                        logger.writeToLogArea("Using IP: " + ip);
                        logger.writeToLogArea("Using threads: " + threads);
                        logger.writeToLogArea("Using port: " + port);
                        logger.writeToLogArea("Using packet message: " + packet_message);
                        logger.writeToLogArea("Using timeout: " + timeout);
                        udp.startAttack();

                    }

                }

                else if(useIP == false) {

                    if(method == "TCP flood") {

                        TCPThreadable tcp = new TCPThreadable(threads, packet_message, port, timeout, url);
                        logger.writeToLogArea("Started TCP flood. Click on Exit Peridot or Stop Firing to terminate the attack.");
                        logger.writeToLogArea("Using URL: " + url);
                        logger.writeToLogArea("Using threads: " + threads);
                        logger.writeToLogArea("Using port: " + port);
                        logger.writeToLogArea("Using packet message: " + packet_message);
                        logger.writeToLogArea("Using timeout: " + timeout);
                        tcp.startAttack();

                    }

                    else if(method == "HTTP GET flood") {

                        HTTPThreadable http = new HTTPThreadable(url, threads);
                        logger.writeToLogArea("Started HTTP GET flood. Click on Exit Peridot or Stop Firing to terminate the attack.");
                        logger.writeToLogArea("Using URL: " + url);
                        logger.writeToLogArea("Using threads: " + threads);
                        http.startAttack();


                    }

                    else if(method == "UDP flood") {

                        UDPThreadable udp = new UDPThreadable(threads, packet_message, port, timeout, url);
                        logger.writeToLogArea("Started UDP flood. Click on Exit Peridot or Stop Firing to terminate the attack.");
                        logger.writeToLogArea("Using URL: " + url);
                        logger.writeToLogArea("Using threads: " + threads);
                        logger.writeToLogArea("Using port: " + port);
                        logger.writeToLogArea("Using packet message: " + packet_message);
                        logger.writeToLogArea("Using timeout: " + timeout);
                        udp.startAttack();


                    }
                }
            }
        }
    }
    @FXML
    public void urlChanged() {

        url = urlBox.getText();

        if(url.equals("")) {

            url = null;

        }

        checkTargetUsage();

    }

    @FXML
    public void ipChanged() {

        ip = ipBox.getText();

        if(ip.equals("")) {

            ip = null;

        }

        checkTargetUsage();

    }

    @FXML
    public void portChanged() {

        try {

            port = Integer.parseInt(portBox.getText());

        } catch(NumberFormatException e){

            lockedOnto.setText("Port must be an integer!");

        }

    }

    @FXML
    public void messageBoxChanged() {

        packet_message = messageBox.getText();

    }

    @FXML
    public void threadsChanged() {

        try {

            threads = Integer.valueOf(threadBox.getText());

        } catch (NumberFormatException e) {

            lockedOnto.setText("Threads must be an integer!");



        }
    }

    @FXML
    public void timeoutChanged() {

        try {

            timeout = Integer.valueOf(timeoutBox.getText());

        } catch (NumberFormatException e) {

            lockedOnto.setText("Timeout must be an integer in milliseconds!");

        }

    }

    @FXML
    public void stopFiringClicked() {

        stopFiring.arm();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.10));

        stopFiring.setTranslateX(2);
        stopFiring.setTranslateY(2);

        pause.setOnFinished(e -> {

            stopFiring.setTranslateX(0);
            stopFiring.setTranslateY(0);
            stopFiring.disarm();
        });

        pause.play();

        System.exit(0);

    }

    @FXML
    public void methodBoxActivated() {

        method = (String) methodBox.getValue();

    }

    @FXML
    public void openLogger() {

        displayLoggerStage();

        openLogButton.arm();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.10));

        openLogButton.setTranslateX(2);
        openLogButton.setTranslateY(2);

        pause.setOnFinished(e -> {

            openLogButton.setTranslateX(0);
            openLogButton.setTranslateY(0);
            openLogButton.disarm();
        });

    }

    public void displayLoggerStage() {

        logger.displayStage();

    }

    public void showStage() {

        mainStage.show();

        Alert alert = new Alert(Alert.AlertType.WARNING, "Peridot is a network stress tester. You are responsible for your actions!");
        alert.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        methodBox.setItems(FXCollections.observableArrayList("TCP flood","UDP flood","HTTP GET flood"));

    }
}
