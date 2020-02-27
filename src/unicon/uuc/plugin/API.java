package unicon.uuc.plugin;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import unicon.uuc.Server;
import unicon.uuc.types.Client;

public class API {
    public Server server;
    public boolean blockOnMsg = false;
    public boolean blockOnJoin = false;
    public String encoding;
    
    public API(Server _s){
        this.server = _s;
        this.encoding = System.getProperty("console.encoding", "utf-8");
    }
    public void log(String text){
        System.out.println(' '+text);
    }
    
    public void error(String text){
        System.err.println(" Error:\n  "+text+'\n');
    }
    
    public void sendString(DatagramSocket dsocket, InetAddress ip0, int port0, String strmsg) throws IOException{
        byte[] buf = strmsg.getBytes();
        DatagramPacket packet
          = new DatagramPacket(buf, buf.length, ip0, port0);
        dsocket.send(packet);
    }
    
    public void sendMsg(Client c, String m) throws SocketException, IOException{
        DatagramSocket sock = new DatagramSocket();
        this.sendString(sock, c.ipaddr, c.port, m);
    }
    
    public void sendStringToIp(String _ip, int _port, String msg) throws IOException{
        DatagramSocket sock = new DatagramSocket();
        this.sendString(sock, InetAddress.getByName(_ip), _port, msg);
    }
    
    public void resetBlocks(){
        this.blockOnMsg = false;
        this.blockOnJoin = false;
    }
}
