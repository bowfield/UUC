package unicon.uuc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class main {

    public static void main(String[] args) throws UnsupportedEncodingException, IOException{
        String encoding = System.getProperty("console.encoding", "utf-8"); // Установка кодировки
        
        Register register = new Register(); // Регистрация стандартных файлов
        new Server(register).start();       // Запуск сервера
    }
    
}
