package net.simplyrin.bungeecommand;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by SimplyRin on 2020/06/10.
 *
 * Copyright 2020 SimplyRin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BungeeCommand implements Listener {

	public static void registerThis(Plugin plugin) {
		ProxyServer.getInstance().getPluginManager().registerCommand(plugin, new SendCommand());
		ProxyServer.getInstance().registerChannel("BungeeCommandR");
	}

	public static void unregisterThis() {
		ProxyServer.getInstance().unregisterChannel("BungeeCommandR");
	}

	public static void dispatchCommand(String server, String command) {
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeUTF(command);

		ProxiedPlayer player = null;
		for (ProxiedPlayer tPlayer : ProxyServer.getInstance().getPlayers()) {
			if (tPlayer.getServer().getInfo().getName().equalsIgnoreCase(server)) {
				player = tPlayer;
			}
		}

		if (player == null) {
			ProxyServer.getInstance().getConsole().sendMessage("§cThere is no player connected to the target server");
			return;
		}

		player.getServer().getInfo().sendData("BungeeCommandR", stream.toByteArray());
	}

	public static class SendCommand extends Command {

		public SendCommand() {
			super("sendcommand", "bungeecommandr.sudo");
		}

		@Override
		public void execute(CommandSender sender, String[] args) {
			if (args.length > 1) {
				String value = "";
				for (int i = 1; i < args.length; i++) {
					value = value + args[i] + " ";
				}
				value = value.trim();

				dispatchCommand(args[0], value);
				return;
			}

			sender.sendMessage("§cUsage: sendcommand <server> <command>");
		}

	}

}
