package r8.model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TextFieldValidator {
    public TextFieldValidator(){

    }

    public void setValidation(final TextField tf, String regex){
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validate(tf, regex);
            }
        });

        validate(tf, regex);
    }

    private void validate(TextField tf, String regex){
        if(!tf.getText().matches(regex)){
            tf.setStyle("-fx-border-color: red;");
        }else{
            tf.setStyle("-fx-border-color: null;");
        }
    }
}
