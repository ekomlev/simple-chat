package com.simplechat.client.screens;

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


    private Stage window;  //The JavaFX Stage class is the top level JavaFX container. The primary Stage is constructed by the platform. Additional Stage objects may be constructed by the application. Stage objects must be constructed and modified on the JavaFX Application Thread.
    private Map<ScreenTypes, Scene> screens; //создание переменной типа Map <тип ключа ScreenTypes, тип значения Scene>

    private static volatile ScreenSystem instance = null;

    private ScreenSystem() {
        screens = new HashMap<>();
    } //создание  мапы HashMap - несортированный набор

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

    public void switchScreen(ScreenTypes screen) throws IOException { // на вход метода приходит параметр LOGIN_SCREEN
        if (screen == ScreenTypes.LOGIN_SCREEN) {
            window.setResizable(false);
        }
        if (screen == ScreenTypes.CHAT_SCREEN) {
            window.setResizable(true);
        }
        Scene scene  = screens.get(screen) != null   //screens это HashMap. Проверка: есть ли в HashMap ключ LOGIN_SCREEN (и присваеваем его в переменную scene типа Scene)
                ? screens.get(screen) //если первое выражение верно то выполняется это действие: у screens (это HashMap) вызывается метод get c параметром screen (это LOGIN_SCREEN), который возвращает значение по заданному ключу, либо null, если значения такого значения нет
                : cacheScene(createScreen(screen), screen); // если же первое выражение false (у HashMap нет ключа LOGIN_SCREEN) то вызывается метод cacheScene с параметрами (createScreen(LOGIN_SCREEN), LOGIN_SCREEN), получается что вначале вызовется еще метод createScreen(LOGIN_SCREEN).
        window.setScene(scene);
        if (screens.size() == 1) {
            window.show();

        }
    }

    private Scene createScreen(ScreenTypes screen) throws IOException { // создание сцены. на вход метода приходит параметр LOGIN_SCREEN
        Parent root = FXMLLoader.load(getClass().getResource(
                String.format("/views/%sView.fxml", screen.getName())
        ));
        return new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT); //создание объекта перегруженным конструктором Scene
    }

    private Scene cacheScene(Scene scene, ScreenTypes screen) {  //scene это созданный объект перегруженным конструктором Scene, screen это ключ LOGIN_SCREEN
        if (screens.get(screen) == null) { //если ключ LOGIN_SCREEN отстутствует в HashMap, то
            screens.put(screen, scene);  //кладем туда ключ LOGIN_SCREEN и значение = созданный объект сцену
        }
        return scene;
    }
}
