package unicon.uuc.plugin.NET;

import java.io.IOException;

public class NETClass {
    public TCPClient makeTCPClient(String _addr, int _port) throws IOException{
        return new TCPClient(_addr, _port);
    }
    
    public TCPServer makeTCPServer(int _port) throws IOException{
        return new TCPServer(_port);
    }
    
    public UDPSocket makeUDPSocket(int _port) throws IOException{
        return new UDPSocket(_port);
    }
}
