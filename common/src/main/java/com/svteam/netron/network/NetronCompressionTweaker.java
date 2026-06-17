package com.svteam.netron.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import net.minecraft.network.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetronCompressionTweaker {
    private static final Logger LOGGER = LogManager.getLogger("Netron");

    // Default vanilla is 256. Raising this to 512 or 1024 ensures we don't
    // waste CPU cycles compressing medium-sized player movement data packets.
    private static final int OPTIMAL_THRESHOLD = 512;

    public static void tweakCompression(Connection connection) {
        try {
            // We can inject logic here if we want to dynamically adjust
            // compression thresholds when a player connects.
            if (connection.isMemoryConnection()) {
                // Singleplayer/LAN integrated server connections don't need compression at all!
                connection.setupCompression(-1, false);
                LOGGER.info("[Netron] Disabled compression for local memory connection.");
            }
        } catch (Exception e) {
            LOGGER.error("[Netron] Failed to optimize compression: ", e);
        }
    }
}