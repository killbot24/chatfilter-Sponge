package kineticnetwork.net.chat.Files;

import kineticnetwork.net.chat.Chat;
import org.spongepowered.api.entity.living.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FilePrep{

    public File getUserFile(String suspect) {
        File file = new File(Chat.rootDir + "/Warnings", suspect + ".yml");
        return file;
    }

    public File getMuteFile() {
        File file = new File(Chat.rootDir + "/Active-mutes.yml");
        return file;
    }

    public File getFlagFile() {
        File file = new File(Chat.rootDir + "/Flagged.yml");
        return file;
    }

    public List<String> getPlayerList() { // lists active mutes to staff on login
        return Arrays.asList(Chat.mutes);
    }

    public List<String> getFlagedlist() { // lists active mutes to staff on login
        return Arrays.asList(Chat.flaged);
    }

    public Boolean isPlayerFlaged(Player player) {
        return Arrays.asList(Chat.flaged).contains(player.getName());
    }
}
