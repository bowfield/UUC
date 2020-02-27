package unicon.uuc.types;

import java.net.InetAddress;

public class Client {
    public String username = null;
    public InetAddress ipaddr = null;
    public int port = 0;
    public boolean admin = false;
    
    public Client(InetAddress ipaddr, int port){
        this.username = ipaddr.getHostAddress();
        this.ipaddr = ipaddr;
        this.port = port;
    }
}
