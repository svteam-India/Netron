package com.svteam.netron.mixin;

import io.netty.channel.Channel;
import com.svteam.netron.network.NetronPipelineOptimizer;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ConnectionMixin {
    @Shadow
    private Channel channel;

    @Inject(method = "configurePacketHandler", at = @At("TAIL"))
    private void onConfigurePacketHandler(CallbackInfo ci) {
        if (this.channel != null && this.channel.isOpen()) {
            if (this.channel.pipeline().get("netron_optimizer") == null) {
                this.channel.pipeline().addBefore("packet_handler", "netron_optimizer", new NetronPipelineOptimizer());
            }
        }
    }
}