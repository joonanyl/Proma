package r8.view.mainView.taskView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import r8.model.Account;
import r8.model.Comment;
import r8.model.appState.AppState;
import r8.model.task.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomCommentComponentController extends GridPane {

    private TaskViewController controller;

    @FXML
    private Label authorLabel;
    @FXML
    private Text contentField;
    @FXML
    private GridPane gridPane;
    @FXML
    private ButtonBar buttonBar;

    //TODO change button names to support localization
    private Button showReplies = new Button("Show replies...");
    private Button hideReplies = new Button("Hide replies...");
    private VBox repliesContainer = new VBox();
    private final Comment comment;
    private ReplyInputComponentController replyInput;

    public CustomCommentComponentController(Comment comment, TaskViewController controller) {
        this.controller = controller;
        this.comment = comment;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/comment-custom-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(comment != null){
            authorLabel.setText(comment.getAccount().getFirstName() + " " + comment.getAccount().getLastName());
            contentField.wrappingWidthProperty().bind(gridPane.widthProperty());
            contentField.setText(comment.getContent());
            GridPane.setMargin(showReplies, new Insets(0,0,0,25));
            GridPane.setMargin(hideReplies, new Insets(0,0,0,25));
            gridPane.add(showReplies,0, 3);
            gridPane.add(repliesContainer, 0 , 3);
            repliesContainer.setVisible(false);
            repliesContainer.setManaged(false);
            replyInput = new ReplyInputComponentController(comment, this);
            gridPane.add(replyInput, 0 , 2);
            replyInput.setVisible(false);
            replyInput.setManaged(false);
            setListeners();
        }
    }

    private void setListeners(){
        showReplies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showReplies.setVisible(false);
                showReplies.setManaged(false);
                repliesContainer.getChildren().clear();
                Set<Comment> temp = comment.getChildComments();
                temp.forEach(reply ->{
                    repliesContainer.getChildren().add(new CommentReplyComponentController(reply, controller));
                });
                //test reply
                repliesContainer.getChildren().add(new CommentReplyComponentController(new Comment(AppState.getInstance().getAccount(), "This is a test", 123), controller));
                repliesContainer.getChildren().add(hideReplies);
                for(Node child : repliesContainer.getChildren()){
                    VBox.setVgrow(child, Priority.ALWAYS);
                }
                repliesContainer.setVisible(true);
                repliesContainer.setManaged(true);
            }
        });
        hideReplies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                repliesContainer.setVisible(false);
                repliesContainer.setManaged(false);
                showReplies.setVisible(true);
                showReplies.setManaged(true);
            }
        });
    }

    @FXML
    private void reply(){
        controller.openReplyToComment(this);
        buttonBar.setVisible(false);
        buttonBar.setManaged(false);
        replyInput.setVisible(true);
        replyInput.setManaged(true);
    }

    void hideReplyInput(){
        replyInput.setVisible(false);
        replyInput.setManaged(false);
        buttonBar.setVisible(true);
        buttonBar.setManaged(true);
    }
}
