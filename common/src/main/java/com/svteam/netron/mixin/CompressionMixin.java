package com.svteam.netron.mixin;

import com.svteam.netron.network.NetronCompressionTweaker;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class CompressionMixin {

    @Inject(method = "setupCompression", at = @At("HEAD"))
    private void onSetupCompressionHead(int threshold, boolean validateDecompression, CallbackInfo ci) {
        NetronCompressionTweaker.tweakCompression((Connection) (Object) this);
    }
}