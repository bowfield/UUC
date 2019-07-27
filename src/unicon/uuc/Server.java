package unicon.uuc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread{

    private final ArrayList<String> users = new ArrayList();
    private final ArrayList<Integer> ports = new ArrayList();
    public HashMap<String , String> usernames = new HashMap<String ,  String>();
    private DatagramSocket datagram;
    public String ip ;
    public int port ;
    public SPrefs sp ;
    public boolean cmd = false;
    public char pk = 0x215;
    
    @Override
  public void run(){
        try {
            sp = new SPrefs();
            port = (int) Double.parseDouble(sp.get("port"));
            InetAddress in = InetAddress.getByName(sp.get("ip"));
            try {
                datagram = new DatagramSocket(port,in);
                
                
                System.out.println("Сервер запущен на порту " + port);
                log(":start " + port);
                
                
                
                
                while(true){
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    datagram.receive(packet);
                    byte[] b = packet.getData();
                    String out = new String(b).trim();
                    
                    if (!users.contains(packet.getAddress().toString())) {
                        users.add(packet.getAddress().toString());
                        ports.add(packet.getPort());
			String ip = packet.getAddress().toString().replace("/","");
                        usernames.put(packet.getAddress().toString() , packet.getAddress().toString());
                        System.out.println(ip+" connected");
                        log(ip+" connected");
                        for (int i=0; i < users.size(); i++) {
                            String cl = users.get(i);
                            Integer cr = ports.get(i);
                            
                            send(datagram,InetAddress.getByName(cl.replace("/", "")),cr,"%connected%" + pk + ip );}
                    }
                    
                        String username = usernames.get(packet.getAddress().toString()).replace("/","").trim();
			log(username+" > "+out);		
                        
                        String[] spl = out.split(""+pk);
                        
                            switch(spl[0]){
                        case "%username%":
                        usernames.put(packet.getAddress().toString() , spl[1]);
                        
                        break;
    
                        case "%message%":
                        
                    System.out.println(out.trim());
             
                    for (int i=0; i < users.size(); i++) {
                        String cl = users.get(i);
                        Integer cr = ports.get(i);
                        
			send(datagram,InetAddress.getByName(cl.replace("/", "")),cr,"%message%"+pk+username+pk+spl[1] );}
                break;
                }   
                }
                
                
                
                
                
                
                
                
                
                
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
            while(true){
                
                
                
                
            }} catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
   
    public void send(DatagramSocket sender , InetAddress ip,int port , String message) throws SocketException, IOException{
    byte[] data = message.getBytes() ;
  
    DatagramPacket pack = new DatagramPacket(data, data.length, ip, port);
    sender.send(pack);
    }
    
    
    
    
    
    public void log(String l) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        String nline = System.getProperty("line.separator");
        OutputStreamWriter nFile = new OutputStreamWriter(new FileOutputStream("uuc.log", true), "UTF-8");
	nFile.write(nline + l);
	nFile.close();
        
    }
    
}
