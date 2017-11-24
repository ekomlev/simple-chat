package com.simplechat.client.screens;

public enum ScreenTypes {
    LOGIN_SCREEN {  // enum Типа ScreenTypes. Обращение ScreenTypes.LOGIN_SCREEN  ... пример: enum Season { WINTER, SPRING, SUMMER, AUTUMN }. Пример обращения: Season season = Season.SPRING; System.out.println(season);
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
 /*2 производных класса LOGIN_SCREEN и CHAT_SCREEN с полиморфным методом getName().
 ScreenTypes - класс производный от java.lang.Enum
 LOGIN_SCREEN и CHAT_SCREEN - 2 производных класса от ScreenTypes, поэтому у себя они переопределяют метод getName()
 При этом объекты классов LOGIN_SCREEN и CHAT_SCREEN существуют в единственном экземпляре и доступны статически
 В Java использование генериков в enum запрещено. Так следующий пример не скомпилируется: enum Type<T> {}
 */
    public abstract String getName();
}
