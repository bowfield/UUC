package unicon.uuc.plugin.NET;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import unicon.uuc.types.Packet;

public class TCPClient {
    public Socket sock;
    public String addr;
    public int port = 0;
    
    public TCPClient(String _addr, int _port) throws IOException{
        this.sock = new Socket(_addr, _port);
        this.addr = _addr;
        this.port = _port;
    }
    
    public void send(String _addr, int _port, String _msg) throws IOException{
        OutputStream out = this.sock.getOutputStream();
        out.write(_msg.getBytes());
    }
    
    public Packet read() throws IOException{
        InputStream in = this.sock.getInputStream();
        String input = "";
        
        int c;
        while((c = in.read()) != -1) {
            input += ((char) c);
        }
        
        Packet p = new Packet(this.sock.getInetAddress().getHostAddress(), this.sock.getPort(), false);
        p.setString(input);
        return p;
    }
    
    public void close() throws IOException{
        this.sock.close();
    }
}
