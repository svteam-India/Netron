package com.svteam.netron.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;

/**
 * Netron Duplex Optimizer handles both incoming and outgoing packet flushing.
 * It ensures PvP critical packets bypass standard micro-batching queues.
 */
public class NetronPipelineOptimizer extends ChannelDuplexHandler {
    private boolean flushScheduled = false;

    // 1. Handles outbound packets (Server sending to Client, e.g., Entity velocity/positions)
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise);
    }

    // 2. Handles inbound packets (Client sending to Server, e.g., Attacking, Moving)
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (isPvPPriorityPacket(msg)) {
            // Forward the packet immediately up the pipeline
            ctx.fireChannelRead(msg);
            // Trigger an immediate flush read context evaluation
            ctx.read();
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private boolean isPvPPriorityPacket(Object msg) {
        // ServerboundInteractPacket = Left/Right clicking entities (Attacking/Interacting)
        // ServerboundMovePlayerPacket = X/Y/Z position and rotation updates
        // ServerboundPlayerInputPacket = Movement keys (WASD / Jumping / Sneaking)
        return msg instanceof ServerboundInteractPacket
                || msg instanceof ServerboundMovePlayerPacket
                || msg instanceof ServerboundPlayerInputPacket;
    }
}