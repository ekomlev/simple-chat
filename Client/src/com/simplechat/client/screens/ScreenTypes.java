package com.simplechat.client.screens;

public enum ScreenTypes {

    /*NOTE: Java enum type is a special kind of Java class. An enum can contain constants, methods etc.
    ScreenTypes is a class of enum type from java.lang.Enum
    LOGIN_SCREEN и CHAT_SCREEN - 2 constants of ScreenType so they override getName() method. Objects this of constants
    are in the single instance and available statically: ScreenTypes screen = ScreenTypes.LOGIN_SCREEN
    Generics in enum is disabled. So the following example won't compiler: enum Type<T> {}
    */
    LOGIN_SCREEN {
        @Override
        public String getName() {
            return "LoginScreen";
        }
    },
    CHAT_SCREEN {
        @Override
        public String getName() {
            return "ChatScreen";
        }
    };

    public abstract String getName();
}
