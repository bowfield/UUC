package unicon.uuc.plugin;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import unicon.uuc.Server;
import unicon.uuc.types.Client;

public class API {
    public int APIV = 5;                // Версия PluginAPI
    
    public Server server;               // Класс сервера
    public boolean blockOnMsg = false;  // Блокировка стандартной обработки сообщений
    public boolean blockOnJoin = false; // Блокировка стандартной обработки входа
    public String encoding;             // Кодировка вывода в консоль
    
    public API(Server _s){
        this.server = _s;
        this.encoding = System.getProperty("console.encoding", "utf-8"); // UTF-8 
    }
    
    // Вывести текст в консоль
    public void log(String text){
        System.out.println(' '+text);
    }
    
    // Вывести ошибку
    public void error(String text){
        System.err.println("\n Ошибка:  "+text+'\n');
    }
    
    // Отправить сообщение через сокет на адрес и порт
    public void sendString(DatagramSocket dsocket, InetAddress ip0, int port0, String strmsg) throws IOException{
        byte[] buf = strmsg.getBytes();
        DatagramPacket packet
          = new DatagramPacket(buf, buf.length, ip0, port0);
        dsocket.send(packet);
    }
    
    // Отправить сообщение клиенту
    public void sendMsg(Client c, String m) throws SocketException, IOException{
        DatagramSocket sock = new DatagramSocket();
        this.sendString(sock, c.ipaddr, c.port, m);
    }
    
    // Отправляет сообщение на указаный IP и порт
    public void sendStringToIp(String _ip, int _port, String msg) throws IOException{
        DatagramSocket sock = new DatagramSocket();
        this.sendString(sock, InetAddress.getByName(_ip), _port, msg);
    }
    
    // Сбрашивает все блокировки
    public void resetBlocks(){
        this.blockOnMsg = false;
        this.blockOnJoin = false;
    }
    
    // Возращает версию PluginAPI
    public int getVersion(){
        return this.APIV;
    }
}
