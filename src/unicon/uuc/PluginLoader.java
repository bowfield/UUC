package unicon.uuc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import unicon.uuc.types.Plugin;
import unicon.uuc.plugin.API;
import unicon.uuc.plugin.NET.NETClass;

public class PluginLoader {
    public String plugindir = "./plugins/";
    public ScriptEngineManager manager = new ScriptEngineManager();
    public ScriptEngine engine = manager.getEngineByName("JavaScript");
    
    public ArrayList<Plugin> loadPlugins(final API api) throws IOException{
        ArrayList<Plugin> list = new ArrayList<Plugin>();
        
        Files.list(new File(plugindir).toPath())
                .limit(10)
                .forEach(path -> {
                    String filen = path.getFileName().toString();
                    //System.out.println(path);
                    Invocable inv;
                    
                    engine.put("API", api);
                    engine.put("JSEngine", this.engine);
                    
                    NETClass netclass = new NETClass();
                    engine.put("NET", netclass);
                    
                    
            try {
                engine.eval(Files.newBufferedReader(Paths.get(path.toString()), StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ScriptException ex) {
                Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
                    inv = (Invocable) engine;
                    
                    
                    
            try {
                inv.invokeFunction("onLoad");
            } catch (ScriptException ex) {
                Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                //Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            new Thread(() -> {
                boolean thrun = true;
                while(thrun){
                    try {
                        inv.invokeFunction("threadLoop", thrun);
                    } catch (ScriptException ex) {
                        Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchMethodException ex) { }
                }
            }).start();
           list.add(new Plugin(api, filen, engine));
        });
        
        
        return list;
    }
}
