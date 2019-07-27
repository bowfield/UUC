package unicon.uuc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class Register {

public Register() throws IOException{

    File f = new File("prefs.ini");
    if(!f.exists()){
        String nline = System.getProperty("line.separator");
        String pf = "ip=0.0.0.0" + nline + "port=7331";

		OutputStreamWriter nFile = new OutputStreamWriter(new FileOutputStream("prefs.ini", true), "UTF-8");
		nFile.write(pf);
		nFile.close();

    }


    
}
    
}
