package net.blay09.mods.waystones.network.handler;

import net.blay09.mods.waystones.PlayerWaystoneHelper;
import net.blay09.mods.waystones.WaystoneManager;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.network.NetworkHandler;
import net.blay09.mods.waystones.network.message.MessageWarpReturn;
import net.blay09.mods.waystones.util.WaystoneEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

public class HandlerFreeWarpReturn implements IMessageHandler<MessageWarpReturn, IMessage> {
	@Override
	@Nullable
	public IMessage onMessage(MessageWarpReturn message, final MessageContext ctx) {
		NetworkHandler.getThreadListener(ctx).addScheduledTask(new Runnable() {
			@Override
			public void run() {
				if(!Waystones.getConfig().teleportButton) {
					return;
				}
				EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
				if(!PlayerWaystoneHelper.canFreeWarp(entityPlayer)) {
					return;
				}
				WaystoneEntry lastWaystone = PlayerWaystoneHelper.getLastWaystone(entityPlayer);
				if(lastWaystone != null && WaystoneManager.teleportToWaystone(entityPlayer, lastWaystone)) {
					PlayerWaystoneHelper.setLastFreeWarp(entityPlayer, System.currentTimeMillis());
				}
				WaystoneManager.sendPlayerWaystones(entityPlayer);

			}
		});
		return null;
	}
}
