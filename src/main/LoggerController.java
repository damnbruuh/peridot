package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerController {

    public LoggerController(MainController controller) throws IOException {

        this.main = controller;

        Stage logStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Logger.fxml"));
        loader.setController(this);

        logStage.setScene(new Scene(loader.load()));
        logStage.setTitle("Peridot Logger");
        logStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/peridot.png")));
        logStage.setResizable(false);

        this.logStage = logStage;

    }

    @FXML
    private TextArea logArea;

    @FXML
    private Label lblDirectory;

    @FXML
    private Button dumpLogsButton;

    private final MainController main;

    private Stage logStage;

    private String log;

    private String directoryPath;

    private File logDirectory;

    public void writeToLogArea(String text) { this.logArea.appendText(text + "\n"); }

    @FXML
    private void dumpClicked() {

        this.log = logArea.getText();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a folder to store the log file");
        this.logDirectory = directoryChooser.showDialog(logStage);

        directoryPath = logDirectory.getPath();
        lblDirectory.setText(directoryPath);

        try {

            File logFile = new File(directoryPath + "\\peridot-log-file.txt");

            if(logFile.createNewFile() == true) {

                writeToLogArea("Succesfully created log file.");

            }

            else {

                writeToLogArea("Log file already exists. Overwriting it.");

            }

            FileWriter writer = new FileWriter(logFile.getPath());
            writer.write(log);
            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
            writeToLogArea("Error! IOException has occurred while trying to create the file.");

        }

    }

    @FXML
    private void exitClicked() { System.exit(0); }

    public void displayStage() {

        logStage.show();

    }
}
