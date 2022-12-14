package r8.view.mainView.taskView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import r8.model.Comment;
import r8.model.task.Task;
import r8.util.lang.ResourceHandler;

import java.io.IOException;
import java.text.DateFormat;

/**
 * Controller for {@link Comment} reply component
 * @author Teemu Tallskog
 */
public class CommentReplyComponentController extends GridPane {
    private TaskViewController controller;

    @FXML
    private Label authorLabel;
    @FXML
    private Text contentField;
    @FXML
    private GridPane gridPane;
    @FXML
    private ButtonBar buttonBar;
    @FXML
    private Label date;

    private Comment comment;

    private ReplyInputComponentController replyInput;

    public CommentReplyComponentController(Comment comment, TaskViewController controller) {
        this.controller = controller;
        this.comment = comment;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/comment-reply-custom-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setResources(ResourceHandler.getInstance().getBundle());
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(comment != null){
            //Gets timestamp from comment and formats it according to the selected locale
            date.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT, ResourceHandler.getInstance().getBundle().getLocale()).format(comment.getTimeStamp()));
            authorLabel.setText(comment.getAccount().getFirstName() + " " + comment.getAccount().getLastName());
            contentField.wrappingWidthProperty().bind(gridPane.widthProperty());
            contentField.setText(comment.getContent());
            replyInput = new ReplyInputComponentController(comment, this);
            gridPane.add(replyInput, 0 , 2);
            replyInput.setVisible(false);
            replyInput.setManaged(false);
        }
    }

    @FXML
    private void reply(){
        controller.openReplyToReply(this);
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

    void retrieveComments(){
        controller.retrieveComments();
    }
}
