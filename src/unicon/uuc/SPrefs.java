package unicon.uuc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class SPrefs {
    Properties p = new Properties();
    public String get(String id) throws FileNotFoundException, IOException{
        FileInputStream input = new FileInputStream(new File("uuc.conf"));
        p.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        return p.getProperty(id);
    }  
}