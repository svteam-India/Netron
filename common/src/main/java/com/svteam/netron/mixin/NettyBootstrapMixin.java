package com.svteam.netron.mixin;

import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class NettyBootstrapMixin {

    @Inject(method = "configurePacketHandler", at = @At("HEAD"))
    private void onConfigurePacketHandlerHead(CallbackInfo ci) {
        try {
            java.lang.reflect.Field channelField = this.getClass().getDeclaredField("channel");
            channelField.setAccessible(true);
            Object channelInstance = channelField.get(this);

            if (channelInstance != null) {
                java.lang.reflect.Method configMethod = channelInstance.getClass().getMethod("config");
                Object channelConfig = configMethod.invoke(channelInstance);

                if (channelConfig != null) {
                    Class<?> pooledAllocatorClass = Class.forName("io.netty.buffer.PooledByteBufAllocator");
                    java.lang.reflect.Field defaultField = pooledAllocatorClass.getDeclaredField("DEFAULT");
                    Object pooledAllocatorInstance = defaultField.get(null);

                    Class<?> allocatorClass = Class.forName("io.netty.buffer.ByteBufAllocator");
                    java.lang.reflect.Method setAllocatorMethod = channelConfig.getClass().getMethod("setAllocator", allocatorClass);

                    setAllocatorMethod.invoke(channelConfig, pooledAllocatorInstance);
                }
            }
        } catch (Exception ignored) {}
    }
}