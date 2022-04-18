package r8.view.mainView.taskView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Comment;
import r8.model.appState.AppState;

import java.io.IOException;

public class ReplyInputComponentController extends GridPane {


    private CustomCommentComponentController commentController;
    private CommentReplyComponentController replyController;

    private boolean isReplyToReply;

    @FXML
    private TextArea contentField;
    @FXML
    private GridPane gridPane;

    private final Comment comment;

    public ReplyInputComponentController(Comment comment, CustomCommentComponentController controller) {
        this.commentController = controller;
        this.isReplyToReply = false;
        this.comment = comment;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/comment-reply-input-custom-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ReplyInputComponentController(Comment comment, CommentReplyComponentController controller) {
        this.replyController = controller;
        this.isReplyToReply = true;
        this.comment = comment;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/comment-reply-input-custom-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void reply(){
        IControllerMain controllerMain = new Controller();
        Comment reply;
        if(!isReplyToReply){
            reply = new Comment(comment, AppState.getInstance().getLoggedAccount(), contentField.getText());
        }else{
            reply = new Comment(comment.getParentComment(), AppState.getInstance().getLoggedAccount(),
                    "@" + comment.getAccount().getFirstName() + comment.getAccount().getLastName() + "\n" + contentField.getText());
        }
        controllerMain.createComment(reply);
        if(!isReplyToReply){
            commentController.retrieveComments();
        }else replyController.retrieveComments();
        cancel();

    }

    @FXML
    private void cancel(){
        if(isReplyToReply){
            replyController.hideReplyInput();
        }else {
            commentController.hideReplyInput();
        }
    }
}
