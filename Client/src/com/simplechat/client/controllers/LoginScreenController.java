package com.simplechat.client.controllers;

import com.simplechat.client.screens.ScreenSystem;
import com.simplechat.client.screens.ScreenTypes;
import com.simplechat.client.threading.ClientThread;
import com.simplechat.client.utils.Options;
import com.simplechat.client.utils.User;
import com.simplechat.commons.messaging.IntroduceMessage;
import com.simplechat.commons.messaging.MessageType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreenController extends BaseScreenController {

    @FXML
    private TextField loginField;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private Button connectButton;
    @FXML
    private Label errorField;

    //Validation of 2 filled in fields
    public void keyReleasedPropertyLogin() {
        String loginFieldText = loginField.getText();
        String hostFieldText = hostField.getText();
        String portFieldText = portField.getText();
        if (!((loginFieldText.isEmpty() || loginFieldText.trim().isEmpty()) || (hostFieldText.isEmpty() || hostFieldText.trim().isEmpty()) || (portFieldText.isEmpty() || portFieldText.trim().isEmpty())))
        connectButton.setDisable(false);
    }

    //Connecting to the chart + IP validation
    public void onClick(MouseEvent event) {
        String loginFieldText = loginField.getText();
        String hostFieldText = hostField.getText();
        int portFieldNumber = Integer.parseInt(portField.getText());
        try {
            Options.getInstance().setUser(new User(loginFieldText, hostFieldText, portFieldNumber));  // Создаем юзера класса User с тремя полями (userName, hostIp, port) через Options класс который реализует шаблон singleton
            //Options.getInstance().getUser();
            Options.getInstance().setClientThread(new ClientThread(Options.getInstance().getUser()));
            navigate(ScreenTypes.CHAT_SCREEN);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            errorField.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onClick);
    }


}