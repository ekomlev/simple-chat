package com.simplechat.client;

import com.simplechat.client.screens.ScreenSystem;
import com.simplechat.client.screens.ScreenTypes;
import com.simplechat.client.utils.Options;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application { //  класс Application является главным классом приложения, именно в его потоке вы можете использовать компоненты JavaFX. расширяя класс Application наш класс ClientApp становиться JavaFx приложением

    public static void main(String[] args) {
        launch();  //является точкой входа в FX приложение
    }

    @Override
    public void start(Stage primaryStage) throws Exception { //Метод start() вызывается при создании потока приложения, в его параметрах можно увидеть объект класса Scene. Этот класс связан с экземпляром окна, которое будет видеть пользователь.
        ScreenSystem.getInstance().setWindow(primaryStage);
        ScreenSystem.getInstance().switchScreen(ScreenTypes.LOGIN_SCREEN);  // Здесь мы создали объект сцену и положили в HashMap ключ LOGIN_SCREEN и значение = объект сцена. Поскольку LOGIN_SCREEN доступен статически то обращаемся к нем через класс где он описан
        primaryStage.setTitle(ScreenSystem.APP_TITLE);
    }

    @Override
    public void stop() throws Exception {
        Options.getInstance().getClientThread().finish();
    }
}
