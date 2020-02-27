package unicon.uuc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class main {

    public static void main(String[] args) throws UnsupportedEncodingException, IOException{
        String encoding = System.getProperty("console.encoding", "utf-8");
        
        Register register = new Register();
        new Server(register).start();
    }
    
}
