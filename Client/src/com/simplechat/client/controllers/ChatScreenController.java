package com.simplechat.client.controllers;

import com.simplechat.client.screens.ScreenTypes;
import com.simplechat.client.threading.ClientThread;
import com.simplechat.client.utils.MyListCell;
import com.simplechat.client.utils.Options;
import com.simplechat.client.utils.User;
import com.simplechat.commons.messaging.*;
import com.simplechat.commons.utils.OnlineUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.List;
import java.util.ResourceBundle;

public class ChatScreenController <T extends Thread & IMessageSender> extends BaseScreenController implements MessageDispatcher<T> {
    private String recipientId;

    @FXML
    private ListView <OnlineUser>listUsers;
    @FXML
    private Button enterBtn;
    @FXML
    private TextArea inputMessage;
    @FXML
    private TextArea showMessage;

    private ObservableList<OnlineUser> users = FXCollections.observableArrayList(); //The elements of the ListView are contained within the items ObservableList. This ObservableList is automatically observed by the ListView, such that any changes that occur inside the ObservableList will be automatically shown in the ListView itself. If passying the ObservableList in to the ListView constructor is not feasible, the recommended approach for setting the items is to simply call:


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listUsers.setItems(users); // The end result of this is, as noted above, that the ListView will automatically refresh the view to represent the items in the list.
        listUsers.setCellFactory(new Callback<ListView<OnlineUser>, ListCell<OnlineUser>>() {
            @Override
            public ListCell<OnlineUser> call(ListView<OnlineUser> listView) {
                return new MyListCell();
            }
        });
        Options.getInstance().getClientThread().setMessageEventListener(this);
        Options.getInstance().getClientThread().start();
        inputMessage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    ChatScreenController.this.sendMessage();
                }
            }
        });
        enterBtn.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onSendMessageBtn);
        try {
            Options.getInstance().getClientThread().sendMessage(new IntroduceMessage(Options.getInstance().getUser().getUserName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listUsers.addEventHandler(MouseEvent.MOUSE_RELEASED, this::listViewReleased);
    }

    private void onSendMessageBtn(MouseEvent event) {
        sendMessage();
    }

    private void sendMessage() {
        try {
            Options.getInstance().getClientThread()
                    .sendMessage(new ClientMessage(inputMessage.getText(), recipientId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputMessage.clear();
    }

    /*private void setListUsers(){
        ObservableList<String> data = FXCollections.observableArrayList();
        data.add(Options.getInstance().getUser().getUserName());
        listUsers.setItems(data);
    }*/

    public void keyReleasedPropertyChat() {
        String inputMessageField = inputMessage.getText();
        if (!((inputMessageField.isEmpty()) || inputMessageField.trim().isEmpty()));
            enterBtn.setDisable(false);
    }


    @Override
    public void onMessageReceived(BaseMessage message) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (message.getMessageType()) {
                    case ONLINE_USERS_LIST_MESSAGE:
                        users.clear();
                        users.addAll(((OnlineUsersMessage) message).getUsers());
                        showMessage.appendText(((OnlineUsersMessage) message).getMessageBody());
                        break;
                    case CLIENT_MESSAGE:
                        showMessage.appendText(message.toString());
                        break;
                    case INTRODUCE_MESSAGE:
                        showMessage.appendText(message.toString());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void killMessageSender() {
        Options.getInstance().getClientThread().finish();
    }

    @FXML
    public void listViewReleased(MouseEvent event) {
        if (listUsers.getSelectionModel().getSelectedItem() != null) {
            recipientId = listUsers.getSelectionModel().getSelectedItem().getUserId();
        } else {
            recipientId = null;
        }
    }
}
