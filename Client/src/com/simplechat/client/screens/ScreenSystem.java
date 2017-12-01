package com.simplechat.client.screens;

import com.simplechat.client.utils.Options;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScreenSystem {
    public static int SCREEN_WIDTH = 1024;
    public static int SCREEN_HEIGHT = 600;
    public static String APP_TITLE = "SimpleChat";


    /*NOTE:The JavaFX Stage class is the top level JavaFX container. The primary Stage is
    constructed by the platform. Additional Stage objects may be constructed by the application.
    Stage objects must be constructed and modified on the JavaFX Application Thread.*/
    private Stage window;

    //NOTE:Creating variable with type Map <key type ScreenTypes, value type Scene>
    private Map<ScreenTypes, Scene> screens;

    private static volatile ScreenSystem instance = null;

    //NOTE:Creating HashMap - unsorted set
    private ScreenSystem() {
        screens = new HashMap<>();
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public static ScreenSystem getInstance() {
        if (instance == null) {
            synchronized (ScreenSystem.class) {
                if (instance == null) {
                    instance = new ScreenSystem();
                }
            }
        }

        return instance;
    }

    //NOTE: at first screen = LOGIN_SCREEN, then screen = CHAT_SCREEN
    public void switchScreen(ScreenTypes screen) throws IOException {
        if (screen == ScreenTypes.LOGIN_SCREEN) {
            window.setResizable(false);
        }
        if (screen == ScreenTypes.CHAT_SCREEN) {
            window.setResizable(true);
            window.setTitle(ScreenSystem.APP_TITLE + " " + Options.getInstance().getUser().getUserName());
        }
        /*NOTE: screens is HashMap. If HashMap contains LOGIN_SCREEN (CHAT_SCREEN) key
        (assign scene with access method of screens)*/
        Scene scene  = screens.get(screen) != null

                /*NOTE: if the first statement is true then this action returns value
                by key = LOGIN_SCREEN (CHAT_SCREEN)*/
                ? screens.get(screen)

                /*NOTE: if false then method cacheScene calls with arguments (createScreen(LOGIN_SCREEN), LOGIN_SCREEN)),
                or (createScreen(CHAT_SCREEN), CHAT_SCREEN))*/
                : cacheScene(createScreen(screen), screen);
        window.setScene(scene);
        if (screens.size() == 1) {
            window.show();

        }
    }

    //NOTE: creation of scene. At first screen = LOGIN_SCREEN, then screen = CHAT_SCREEN
    private Scene createScreen(ScreenTypes screen) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(
                String.format("/views/%sView.fxml", screen.getName())
        ));

        //NOTE: creation of Scene object with overloaded constructor of Scene
        return new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    //NOTE: scene = object Scene that is created with overloaded constructor, screen = LOGIN_SCREEN (then CHAT_SCREEN)
    private Scene cacheScene(Scene scene, ScreenTypes screen) {

        //NOTE: if HashMap doesn't contain key (LOGIN_SCREEN | CHAT_SCREEN)
        if (screens.get(screen) == null) {
            //then put this key and scene
            screens.put(screen, scene);
        }
        return scene;
    }
}
