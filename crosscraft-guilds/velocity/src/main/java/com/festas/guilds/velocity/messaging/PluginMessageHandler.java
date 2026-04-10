package com.festas.guilds.velocity.messaging;

import com.festas.guilds.velocity.GuildsVelocityPlugin;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Verarbeitet Plugin-Nachrichten über den crosscraft:guilds Kanal.
 * Backend-Server können über diesen Kanal mit dem Proxy kommunizieren.
 */
public class PluginMessageHandler {

    private final ProxyServer server;
    private final GuildsVelocityPlugin plugin;

    // Nachrichten-Typen
    public static final String MSG_GUILD_CHAT = "GUILD_CHAT";
    public static final String MSG_GUILD_INFO_REQUEST = "GUILD_INFO_REQUEST";
    public static final String MSG_PLAYER_GUILD_REQUEST = "PLAYER_GUILD_REQUEST";

    public PluginMessageHandler(ProxyServer server, GuildsVelocityPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(GuildsVelocityPlugin.CHANNEL)) return;

        // Nur Nachrichten von Backend-Servern akzeptieren
        if (!(event.getSource() instanceof ServerConnection)) return;
        event.setResult(PluginMessageEvent.ForwardResult.handled());

        try {
            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(event.getData()));
            String type = readString(in);

            switch (type) {
                case MSG_GUILD_CHAT -> handleGuildChatMessage(in);
                case MSG_GUILD_INFO_REQUEST -> handleGuildInfoRequest(in);
                case MSG_PLAYER_GUILD_REQUEST -> handlePlayerGuildRequest(in, event);
                default -> plugin.getLogger().warn("Unbekannter Plugin-Nachrichten-Typ: {}", type);
            }
        } catch (IOException e) {
            plugin.getLogger().error("Fehler beim Lesen der Plugin-Nachricht", e);
        }
    }

    private void handleGuildChatMessage(DataInputStream in) throws IOException {
        String guildId = readString(in);
        String playerName = readString(in);
        String message = readString(in);

        var chatHandler = plugin.getChatHandlerOrNull();
        if (chatHandler != null) {
            chatHandler.sendGuildChat(guildId, playerName, message);
        }
    }

    private void handleGuildInfoRequest(DataInputStream in) throws IOException {
        String guildName = readString(in);
        // Antwort würde hier über Redis oder direkte Plugin-Nachricht zurückgesendet
        plugin.getLogger().debug("Gildeninfo angefragt für: {}", guildName);
    }

    private void handlePlayerGuildRequest(DataInputStream in, PluginMessageEvent event) throws IOException {
        String playerUUID = readString(in);
        // Antwort würde hier via Plugin-Nachricht zurückgesendet
        plugin.getLogger().debug("Gilden-Mitgliedschaft angefragt für UUID: {}", playerUUID);
    }

    /**
     * Sendet eine Plugin-Nachricht an einen bestimmten Spieler.
     */
    public void sendToPlayer(Player player, String type, String... data) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);
            writeString(out, type);
            for (String d : data) {
                writeString(out, d);
            }
            player.sendPluginMessage(GuildsVelocityPlugin.CHANNEL, bytes.toByteArray());
        } catch (IOException e) {
            plugin.getLogger().error("Fehler beim Senden der Plugin-Nachricht", e);
        }
    }

    private String readString(DataInputStream in) throws IOException {
        int length = in.readShort();
        byte[] bytes = new byte[length];
        in.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private void writeString(DataOutputStream out, String str) throws IOException {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        out.writeShort(bytes.length);
        out.write(bytes);
    }
}
