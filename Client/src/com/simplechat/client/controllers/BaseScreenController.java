package com.simplechat.client.controllers;

import com.simplechat.client.screens.ScreenSystem;
import com.simplechat.client.screens.ScreenTypes;
import javafx.fxml.Initializable;

import java.io.IOException;


public abstract class BaseScreenController implements Initializable{ //Интерфейс Initializable может быть реализован классом контроллера для его автоматической инициализации с помощью реализации метода интерфейса

    protected void navigate(ScreenTypes screen) {
        //TODO: implement navigation routines here
        try {
            ScreenSystem.getInstance().switchScreen(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
