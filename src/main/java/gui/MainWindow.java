package gui;

import tearit.TearIT;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML // Use to reference this element regardless of visibility from FXML folder
    // FXML is for the view like root App of React
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    Label welcomeLabel;

    private TearIT tearIT;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Susano.jpeg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/TearIT.jpeg"));

    /**
     * Initializes the control elements on the stage when it first renders
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        welcomeLabel = new Label("Welcome to TearIT, enter anything to start...");
        welcomeLabel.setStyle("-fx-font-weight: bold;");
        dialogContainer.getChildren().add(welcomeLabel);
    }

    /** Injects the TearIT.TearIT instance */
    public void setTearIT(TearIT t) {
        tearIT = t;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        // Hide the welcome label after the first user input
        if (dialogContainer.getChildren().contains(welcomeLabel)) {
            dialogContainer.getChildren().remove(welcomeLabel);
        }

        String input = userInput.getText();
        String response = tearIT.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTearItDialog(response, dukeImage)
        );
        userInput.clear();
    }



}
