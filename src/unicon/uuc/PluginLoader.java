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
import java.lang.Thread;

public class PluginLoader {
    public String plugindir = "./plugins/";
    ScriptEngineManager manager;
    ScriptEngine engine;
    
    public void loadPlugins(ArrayList<Plugin> list, final API api) throws IOException{
        Files.list(new File(plugindir).toPath())
                .limit(10)
                .forEach(path -> {
                    manager = new ScriptEngineManager();
                    engine = manager.getEngineByName("JavaScript");
                    
                    String filen = path.getFileName().toString();
                    Invocable inv;
                    
                    NETClass netclass = new NETClass();
                    
                    engine.put("API", api);
                    engine.put("JSEngine", this.engine);
                    engine.put("NET", netclass);
                    engine.put("_PID", list.size());
                    
                    
            try {
                engine.eval(Files.newBufferedReader(Paths.get(path.toString()), StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ScriptException ex) {
                Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
                    inv = (Invocable) engine;
                    
                    
            
            list.add(new Plugin(api, filen, engine));    
            try {
                inv.invokeFunction("onLoad");
            } catch (ScriptException ex) {
                Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {}
            
            
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
        });
    }
}
