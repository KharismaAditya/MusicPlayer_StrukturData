package UI;

import javafx.scene.control.Button;

public class interactionComp {
    public Button primary1(String text){
     Button but = new Button(text);
     but.setMinSize(60,20);
     onStyle(but);
     return but;
    }

    public Button primary2(String text){
        Button but = new Button(text);
        but.setMinSize(20,20);
        onStyle(but);
        return but;
    }


    public void onStyle(Button btn){
        btn.setStyle("-fx-background-color: #FF3636;-fx-font-size: 8px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #842323; -fx-font-size: 8px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #FF3636;-fx-font-size: 8px;"));
    }
}
