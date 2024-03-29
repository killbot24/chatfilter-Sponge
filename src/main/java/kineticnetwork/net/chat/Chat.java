package kineticnetwork.net.chat;

import com.google.inject.Inject;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import kineticnetwork.net.chat.Commands.*;
import kineticnetwork.net.chat.Files.FileEditor;
import kineticnetwork.net.chat.config.ConfigManager;
import kineticnetwork.net.chat.config.GetItems;
import kineticnetwork.net.chat.config.config;
import kineticnetwork.net.chat.listener.onAnvilUse;
import kineticnetwork.net.chat.listener.onPlayerChat;
import kineticnetwork.net.chat.listener.onSignUse;
import ninja.leaping.configurate.ConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.SleepingEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.config.ConfigDir;


@Plugin(
        id = "chat-filter",
        name = "Chat-filter",
        description = "Chat filter",
        authors = {
                "Chickon98"
        }
)
public class Chat {
    public static Chat plugin;
    public static File rootDir;
    public static Map<String, String> Blacklisted = new HashMap<String, String>();
    public static String[] mutes;
    public static String[] flaged;
    public static  Object URL;
    public static String Prefix= "[Chatfilter]";
    @Inject
    public Logger logger;
    @Inject
    @ConfigDir(sharedRoot = true)
    private File defaultConfigDir;
    @Listener
    public void onInit(GameInitializationEvent event)
    {
        plugin = this;
        this.logger.info(Prefix+" Starting");
        rootDir = new File(defaultConfigDir, "chat-filter");
        this.logger.info( Prefix+" Registering event's");
        Sponge.getEventManager().registerListeners(this, new onPlayerChat());
        Sponge.getEventManager().registerListeners(this, new onAnvilUse());
        Sponge.getEventManager().registerListeners(this, new onSignUse());
        config.init(rootDir);
        this.logger.info( Prefix+" Done!");
    }
    @Listener
    public void onServerStart(GameStartedServerEvent event) throws IOException {

        config.load();// loads config
        RegisterCommands register =new RegisterCommands();
        GetItems words =new GetItems();
        //Get blocked items + Url
        // words.getItems();
        //Register Commands
        register.registerCommands();
        FileEditor files=new FileEditor();
        files.reloadMutes();
        files.getFlaged();
    }

    public static Chat getInstance()
    {
        return plugin;
    }
    public static Logger getLogger()
    {
        return getInstance().logger;
    }


    public File getfile() {
        return rootDir;}}

