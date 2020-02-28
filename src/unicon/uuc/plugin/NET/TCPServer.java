package unicon.uuc.plugin.NET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import unicon.uuc.types.Packet;

public class TCPServer {
    public Socket sock;
    ServerSocket serv_sock;
    public boolean lock = false;

    public int port = 0;
    
    public TCPServer(int _port) throws IOException{
        this.serv_sock = new ServerSocket(_port);
        this.port = _port;
    }
    
    public void accept() throws IOException{
        this.sock = this.serv_sock.accept();
        this.lock = true;
    }
    
    public void send(String _msg) throws IOException{
        OutputStream out = this.sock.getOutputStream();
        out.write(_msg.getBytes());
    }
    
    public Packet read() throws IOException{
        InputStream input = this.sock.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        
        Packet p = new Packet(this.sock.getInetAddress().getHostAddress(), this.sock.getPort(), false);
        p.setString(line);
        return p;
    }
    
    public void close() throws IOException{
        this.sock.close();
        this.lock = false;
    }
}
