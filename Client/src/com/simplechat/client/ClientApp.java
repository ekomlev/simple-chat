package com.simplechat.client;

import com.simplechat.client.screens.ScreenSystem;
import com.simplechat.client.screens.ScreenTypes;
import com.simplechat.client.utils.Options;
import javafx.application.Application;
import javafx.stage.Stage;

/*NOTE: Application class is the main client application class. We can use JavaFx components in its thread.
ClientApp class becomes JavaFX application with extending Application class by ClientApp class*/
public class ClientApp extends Application {

    public static void main(String[] args) {

        //NOTE: JavaFX application entry point
        launch();
    }

    @Override

    /*NOTE: start() is called when creating thread. Its arguments is object of Scene class.
    This class is linked with window instance, that user should see*/
    public void start(Stage primaryStage) throws Exception {
        ScreenSystem.getInstance().setWindow(primaryStage);

        /*NOTE: creating scene object. Put  key = LOGIN_SCREEN and value = scene object in HashMap.
        As LOGIN_SCREEN is static we can access to it via class*/
        ScreenSystem.getInstance().switchScreen(ScreenTypes.LOGIN_SCREEN);
        primaryStage.setTitle(ScreenSystem.APP_TITLE);
    }

    @Override
    public void stop() throws Exception {
        Options.getInstance().getClientThread().finish();
    }
}
