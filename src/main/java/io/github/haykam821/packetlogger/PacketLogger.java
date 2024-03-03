package io.github.haykam821.packetlogger;

import java.util.Locale;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.NetworkSide;
import net.minecraft.util.Identifier;

public class PacketLogger {
	private static final Logger LOGGER = LogManager.getLogger("Packet Logger");

	private static Identifier getChannel(Packet<?> packet) {
		if (packet instanceof CustomPayloadC2SPacket) {
			return ((CustomPayloadC2SPacket) packet).payload().id();
		} else if (packet instanceof CustomPayloadS2CPacket) {
			return ((CustomPayloadS2CPacket) packet).payload().id();
		}
		return null;
	}

	private static String getSideName(NetworkSide side) {
		if (side == NetworkSide.CLIENTBOUND) return "client";
		if (side == NetworkSide.SERVERBOUND) return "server";

		return side.name().toLowerCase(Locale.ROOT);
	}

	public static void logSentPacket(Packet<?> packet, NetworkSide side) {
		String sideName = PacketLogger.getSideName(side);

		Identifier channel = PacketLogger.getChannel(packet);
		if (channel != null) {
			LOGGER.info("Sending packet with channel '{}' ({})", channel, sideName);
			return;
		}

		LOGGER.info("Sending packet with name '{}' ({})", packet.getClass().getName(), sideName);
	}

	public static void logReceivedPacket(Packet<?> packet, NetworkSide side) {
		String sideName = PacketLogger.getSideName(side);

		Identifier channel = PacketLogger.getChannel(packet);
		if (channel != null) {
			LOGGER.info("Received packet with channel '{}' ({})", channel, sideName);
			return;
		}

		LOGGER.info("Received packet with name '{}' ({})", packet.getClass().getName(), sideName);
	}
}
