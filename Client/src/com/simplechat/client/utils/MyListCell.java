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
    // все элементы моего fxml
    private HBox hBox;
    private VBox vBox;
    private Label userName;
/*    private ImageView imgOnline;
    private Label textAnimal;*/
    /**/

    public MyListCell() {
        try {
            // нужно их найти!
            // hbox - это самый главный элемент моего fxml, все остальные элементы внутри его, его я и подгружу
            // через FXMLLoader
            hBox = FXMLLoader.load(getClass().getResource("/views/ListCell.fxml"));
            // остальные просто найду внутри
            vBox = (VBox) hBox.getChildren().get(1);
            userName = (Label) vBox.getChildren().get(0);
           /* vBox = (VBox) hBox.getChildren().get(1);
            imgOnline = (ImageView) hBox.getChildren().get(1);
            textAnimal = (Label) vBox.getChildren().get(1);
            imgAnimal = (ImageView) hBox.getChildren().get(1);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // это наш ключевой метод, именно он на входе будет принимать нашего юзера
    // и тут мы будем заполнять поля fxml
    @Override
    public void updateItem(OnlineUser user, boolean empty) {
        super.updateItem(user, empty);

        // этот блок if-else важен, если юзер удалён, при апдейте вернём пустую ячейку
        if (user != null && !empty) {
            // рисуем в ячейке наш myListCell, уже говорил что hbox содержит в себе все остальные компоненты
            setGraphic(hBox);
            userName.setText(user.toString());
            /*// всё остальное моя песня
            userName.setText(user.toString());
            textAnimal.setText("anonymous " + user.getTotem().toString());
            try {
                imgAnimal.setImage(getAnimalImage(user.getTotem().toString()));
            } catch (IOException e) {
                System.out.println("Can not find a picture!");
                e.printStackTrace();
            }*/
        } else {
            setGraphic(null);
            return;
        }
    }

/*
    // это тоже чисто мой метод
    private Image getAnimalImage(String animal) throws IOException {
        return new Image(String.format("/img/animals/%s.png", animal));
    }
*/

}