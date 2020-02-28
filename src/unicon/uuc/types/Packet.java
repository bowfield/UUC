package unicon.uuc.types;

public class Packet {
    public boolean UDP = false;
    public String addr;
    public int port;
    public byte[] data;
    
    public Packet(String _addr, int _port, boolean _UDP){
        this.addr =_addr;
        this.port = _port;
        this.UDP = _UDP;
    }
    
    public String getString(){
        return new String(this.data);
    }
    
    public void setString(String str){
        this.data = str.getBytes();
    }
}
