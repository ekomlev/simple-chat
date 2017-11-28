package com.simplechat.client.controllers;

import com.simplechat.client.screens.ScreenSystem;
import com.simplechat.client.screens.ScreenTypes;
import javafx.fxml.Initializable;

import java.io.IOException;

/*NOTE: Interface Initializable is implemented by Controller class
for automatic initialisation using interface method*/
public abstract class BaseScreenController implements Initializable{

    protected void navigate(ScreenTypes screen) {
        try {
            ScreenSystem.getInstance().switchScreen(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
