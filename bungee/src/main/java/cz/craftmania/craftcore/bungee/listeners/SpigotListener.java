package cz.craftmania.craftcore.bungee.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cz.craftmania.craftcore.bungee.Main;
import cz.craftmania.craftcore.bungee.entity.PlayerManager;
import cz.craftmania.craftcore.bungee.mojang.Skin;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class SpigotListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent ev) {
        if (!ev.getTag().equals(Main.CRAFTCORE_CHANNEL)) {
            return;
        }
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(ev.getData());
            DataInputStream data = new DataInputStream(stream);
            String sc = data.readUTF();
            switch(sc) {
                case "skin":
                    String player = data.readUTF();
                    String value = data.readUTF();
                    String signature = data.readUTF();
                    new PlayerManager(BungeeCord.getInstance().getPlayer(player))
                            .changeSkin(new Skin(value, signature));
                    break;
                case "cmd":
                    String cmd = data.readUTF();
                    BungeeCord.getInstance().getPluginManager().dispatchCommand(
                            BungeeCord.getInstance().getConsole(), cmd);
                    break;
                case "cmdsv":
                    String cmd1 = data.readUTF();
                    String sv1 = data.readUTF();
                    ByteArrayDataOutput out1 = ByteStreams.newDataOutput();
                    out1.writeUTF("cmdsv");
                    out1.writeUTF(cmd1);
                    BungeeCord.getInstance().getServerInfo(sv1)
                            .sendData(Main.CRAFTCORE_CHANNEL, out1.toByteArray());
                    break;
                case "playercmd":
                    String cmd2 = data.readUTF();
                    String player1 = data.readUTF();
                    BungeeCord.getInstance().getPluginManager().dispatchCommand(
                            BungeeCord.getInstance().getPlayer(player1), cmd2);
                    break;
                case "playercmdsv":
                    String cmd3 = data.readUTF();
                    String player2 = data.readUTF();
                    ByteArrayDataOutput out2 = ByteStreams.newDataOutput();
                    out2.writeUTF("playercmdsv");
                    out2.writeUTF(cmd3);
                    out2.writeUTF(player2);
                    BungeeCord.getInstance().getPlayer(player2).getServer()
                            .sendData(Main.CRAFTCORE_CHANNEL, out2.toByteArray());
                    break;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
