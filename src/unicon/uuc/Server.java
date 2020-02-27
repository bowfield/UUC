package unicon.uuc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptException;
import unicon.uuc.plugin.API;
import unicon.uuc.types.Client;
import unicon.uuc.types.Plugin;

public class Server extends Thread{
    public Register register = null; // Регистратор
    public String addr = null;       // IP-адрес сервера
    public int port = 0;             // Порт сервера
    public SPrefs conf;              // Конфиг сервера
    public DatagramSocket dsocket;   // Сокет сервера
    public HashMap<String, Client> clients = new HashMap<String, Client>(); // Клиенты
    
    public Server(Register reg){
        this.register = reg;
    }
    
    public boolean running = false; // Булеан работы сервера
    @Override
    public void run(){
        running = true; // Сервер включен
        try{
            try{
                try{
                    this.conf = new SPrefs(); // Конфиги
                    this.port = (int) Double.parseDouble(this.conf.get("port"));
                    InetAddress in = InetAddress.getByName(this.conf.get("addr"));
                    
                    System.out.println("# Сервер запущен на порту " +
                            this.port + "\n -> " + this.conf.get("name"));
                    this.dsocket = new DatagramSocket(this.port); // Создание UDP сокета
                    
                    System.out.println("# Загрузка плагинов...");
                    API api = new API(this);                                // PluginAPI
                    PluginLoader loader = new PluginLoader();               // PluginLoader
                    ArrayList<Plugin> plugins = loader.loadPlugins(api);    // Загружаем плагины
                    
                    
                    while(true){
                        
                        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                        dsocket.receive(packet); // Принимаем пакет
                        
                        InetAddress address = packet.getAddress(); // Адрес клиента
                        int port = packet.getPort();               // Порт клиента
                        
                        byte[] b = packet.getData();
                        String received = new String(b, "UTF-8").trim();
                        
                        if(!this.clients.containsKey(address.getHostAddress())){
                            this.clients.put(address.getHostAddress(), new Client(
                                    address, port
                            ));
                            if(!api.blockOnJoin){
                                api.log(this.clients.get(address.getHostAddress()).username + " подлючился к серверу.");
                                api.sendString(dsocket, address, port, ": "+this.conf.get("welcome"));
                            }
                            
                            
                            for(int o = 0; o < plugins.size(); o++){
                                try {
                                    ((Invocable)plugins.get(o).engine).invokeFunction("onJoin", this.clients.get(address.getHostAddress()));
                                } catch (ScriptException ex) {
                                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (NoSuchMethodException ex) {
                                }
                            }
                        }
                        
                        String[] cmd = received.split(" "); // Команды и аругменты
                        
                        if(received.length() > 1)
                        if(received.charAt(0) == '%'){
                            boolean uses = false;
                            if(cmd[0].contains("%nick")){ // fix stupid java
                                this.clients.get(address.getHostAddress()).username = cmd[1];
                                api.log("Установлен ник " + received.split(" ")[1] + " для " + address.getHostAddress());
                                uses = true;
                            
                            }else if(cmd[0].contains("%find")){ // fix stupid java
                                api.sendString(dsocket, address, port, this.conf.get("name") + "\n" + this.conf.get("welcome") +
                                        "\n" + new Integer(this.clients.size()).toString() + "\n" + this.conf.get("maxclients"));
                                uses = true;
                            }
                            
                            for(int o = 0; o < plugins.size(); o++){
                                try {
                                    ((Invocable)plugins.get(o).engine).invokeFunction("onCommand", cmd, uses);
                                } catch (ScriptException ex) {
                                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (NoSuchMethodException ex) {}
                            }
                        }
                        
                        String msg = this.clients.get(address.getHostAddress()).username + " : " + received;
                        for(Map.Entry<String, Client> entry : clients.entrySet()) {
                            String key = entry.getKey();
                            Client value = entry.getValue();
                            
                            if(this.clients.get(address.getHostAddress()).username != value.username){ // fix stuid java
                                if(!api.blockOnMsg){
                                    api.sendString(dsocket, value.ipaddr, value.port, msg + "\n");
                                }
                            }
                            
                        }
                        
                        api.log(msg);
                        
                        for(int o = 0; o < plugins.size(); o++){
                            try {
                                ((Invocable)plugins.get(o).engine).invokeFunction("onMsg", this.clients.get(address.getHostAddress()), received);
                            } catch (ScriptException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NoSuchMethodException ex) {}
                        }
                        
                    }
                    
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
            } catch (SocketException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);}
    
    this.dsocket.close();
    }
}
