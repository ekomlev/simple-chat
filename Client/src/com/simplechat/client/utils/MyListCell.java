package com.simplechat.client.utils;

import com.simplechat.commons.utils.OnlineUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class MyListCell extends ListCell<OnlineUser> {
    private HBox hBox;
    private VBox vBox;
    private Label userName;


    public MyListCell() {
        try {

            //NOTE: loads ListCell.fxml and assign to hBox
            hBox = FXMLLoader.load(getClass().getResource("/views/ListCell.fxml"));

            //NOTE: assign to vBox and userName variable corresponding values via get(level of child) method
            vBox = (VBox) hBox.getChildren().get(1);
            userName = (Label) vBox.getChildren().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //NOTE: takes user
    @Override
    public void updateItem(OnlineUser user, boolean empty) {
        super.updateItem(user, empty);

        //NOTE:  if user exists then
        if (user != null && !empty) {

            //NOTE: then create in cell myListCell (hBox contains all components of ListCell.fxml)
            setGraphic(hBox);
            userName.setText(user.toString());
        } else {
            setGraphic(null);
            return;
        }
    }

}