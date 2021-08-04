package kineticnetwork.net.chat.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kineticnetwork.net.chat.Chat;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Created by tjbur on 01/06/2020.
 */
public class config {
    private static File configFile;
    private static ConfigurationLoader<CommentedConfigurationNode> configManager;
    private static CommentedConfigurationNode config;


    public static void init(File rootDir) {
        configFile = new File(rootDir, "config.conf");
        configManager = HoconConfigurationLoader.builder().setPath(configFile.toPath()).build();

    }

    public static void load() {
        try {
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
                config = configManager.load();
                configManager.save(config);
                List list = new ArrayList<>();

                // check integrity
                list.add("bob:John");
                list.add("john:dave");

                config.getNode("Blocked").setComment("Blocked word's and reason format to follow is \n Penguin:is flipper boi");
                config.getNode("Blocked").setValue(list);

                config.getNode("URL").setComment("Link to appeal");
                config.getNode("URL").setValue("Url");
                save();
            }

            config = configManager.load();
        } catch (IOException e) {
            return;
        }
        String[] split = new String[0];
        try {
            @Nullable Object message = config.getNode("URL").getValue();
            Chat.URL = message.toString();// Website URL

            String input = String.valueOf(config.getNode("Blocked").getValue());
            Chat.getLogger().info(input);
            input = input.replace("[", "");
            input = input.replace("]", "");
            // input = input.replace(" ", "");
            split = input.split(",");
            for (int i = 0; i < split.length; i++) {//Take config split by , add into list
                String[] item = split[i].split(":");
                Chat.Blacklisted.put(item[0], item[1]);
                Chat.getLogger().info(Chat.getInstance().Prefix + " " + item[0] + "," + item[1]);
            }
        } catch (Exception e) {
            Chat.getLogger().info(Chat.Prefix + " Issue in config formatting \n defaulting to Original List ");
            e.printStackTrace();
            Chat.Blacklisted.put("fag","Homophobic Slur");
            Chat.Blacklisted.put("faggot","Homophobic Slur");
        }
    }

    public static void save() throws IOException {
            configManager.save(config);
    }

    public static CommentedConfigurationNode getNode(String... path) {
        return config.getNode((Object[]) path);
    }


}
