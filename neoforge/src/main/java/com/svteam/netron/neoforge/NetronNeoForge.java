package com.svteam.netron.neoforge;

import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("netron")
public class NetronNeoForge {
    private static final Logger LOGGER = LogManager.getLogger("Netron");

    public NetronNeoForge() {
        LOGGER.info("[Netron] Initializing Netron network optimizations for NeoForge!");
    }
}