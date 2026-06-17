package com.svteam.netron.forge;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("netron")
public class NetronForge {
    private static final Logger LOGGER = LogManager.getLogger("Netron");

    public NetronForge() {
        LOGGER.info("[Netron] Initializing Netron network optimizations for Forge!");
    }
}