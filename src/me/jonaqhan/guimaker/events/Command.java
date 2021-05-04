package me.jonaqhan.guimaker.events;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.jonaqhan.guimaker.Main;
import me.jonaqhan.guimaker.gui.Maker;

public class Command implements Listener {

	public Main plugin;

	public Command(Main plugin) {
		this.plugin = plugin;

		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Maker maker = new Maker(plugin);
		String command = e.getMessage().replace("/", "");
		Map<String, String> listCommands = maker.loadCommands();
		Player p = e.getPlayer();

		for (Entry<String, String> entry : listCommands.entrySet()) {
			if (command.equalsIgnoreCase(entry.getKey())) {
				maker.onCommand(p, null, command, null);
				e.setCancelled(true);

			}

		}

	}

}
