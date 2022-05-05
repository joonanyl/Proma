package r8.model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * Used to apply validation styling Change listeners to text input controls.
 * @author Teemu Tallskog
 */
public class TextFieldValidator {
    /**
     * Used to Change border color on a TextField based on given regex
     * @param textInputControl The text input control you want to apply the styling to
     * @param regex The pattern you want your TextField to react to
     */
    public static void setValidation(final TextInputControl textInputControl, String regex){
        textInputControl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validate(textInputControl, regex);
            }
        });

        validate(textInputControl, regex);
    }

    private static void validate(TextInputControl tf, String regex){
        if(!tf.getText().matches(regex)){
            tf.setStyle("-fx-border-color: red;");
        }else{
            tf.setStyle("-fx-border-color: null;");
        }
    }
}
