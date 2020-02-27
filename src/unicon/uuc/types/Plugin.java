package unicon.uuc.types;

import javax.script.ScriptEngine;
import unicon.uuc.plugin.API;

public class Plugin {
    public String plugname = "";
    public API api;
    public ScriptEngine engine;
    
    public Plugin(API _api, String _plugname, ScriptEngine _engine){
        this.api = _api;
        this.plugname = _plugname;
        this.engine = _engine;
    }
}
