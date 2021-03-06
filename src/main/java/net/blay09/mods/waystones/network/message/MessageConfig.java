package net.blay09.mods.waystones.network.message;

import io.netty.buffer.ByteBuf;
import net.blay09.mods.waystones.WaystoneConfig;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageConfig implements IMessage {

	private WaystoneConfig config;

	public MessageConfig() {
	}

	public MessageConfig(WaystoneConfig config) {
		this.config = config;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		config = WaystoneConfig.read(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		config.write(buf);
	}

	public WaystoneConfig getConfig() {
		return config;
	}
}
