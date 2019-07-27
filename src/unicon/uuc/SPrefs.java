package unicon.uuc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SPrefs {
Properties p = new Properties();
public String get(String id) throws FileNotFoundException, IOException{
p.load(new FileInputStream("prefs.ini"));
return p.getProperty(id);
}  
}
