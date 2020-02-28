package unicon.uuc.plugin.NET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import unicon.uuc.types.Packet;

public class UDPSocket {
    public DatagramSocket dsock;

    public int port = 0;
    
    public UDPSocket(int _port) throws IOException{
        this.port = _port;
        this.dsock = new DatagramSocket(_port);
    }
    
    public void send(String _addr, int _port, String _msg) throws IOException{
        InetAddress address = InetAddress.getByName(_addr);
        byte[] buffer = new byte[512];
 
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
        this.dsock.send(request);
    }
    
    public Packet read() throws IOException{
        byte[] buffer = new byte[512];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        this.dsock.receive(response);
        
        Packet p = new Packet(response.getAddress().getHostAddress(), response.getPort(), true);
        p.setString(new String(buffer, 0, response.getLength()));
        return p;
    }
    
    public void close() throws IOException{
        this.dsock.close();
    }
}
