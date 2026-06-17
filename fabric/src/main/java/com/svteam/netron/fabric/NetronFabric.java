package com.svteam.netron.fabric;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetronFabric implements ModInitializer {
    private static final Logger LOGGER = LogManager.getLogger("Netron");

    @Override
    public void onInitialize() {
        LOGGER.info("[Netron] Initializing Netron network optimizations for Fabric!");
    }
}