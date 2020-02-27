package unicon.uuc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

class Register {
    
    public Register() throws IOException{

        File f = new File("uuc.conf");
        if(!f.exists()){
            String nline = System.getProperty("line.separator");
            String pf = "addr=0.0.0.0" + nline
                    + "port=7331" + nline
                    + "maxclients=100" + nline
                    + "cmdpassword=PASSWORD_" + nline
                    + "name=UUC Сервер" + nline
                    + "welcome=Добро пожаловать на UUC Сервер.";
            OutputStreamWriter nFile = new OutputStreamWriter(new FileOutputStream("uuc.conf", true), "UTF-8");
            nFile.write(pf);
            nFile.close();
        }
        
        File f0 = new File("./plugins/");
        if(!f0.exists()){
            new File("./plugins/").mkdir(); // Создать папку для плагинов
        }
    }
}
