package me.jonaqhan.guimaker.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.jonaqhan.guimaker.Main;

public class Placeholders {
	public static Main plugin;

	@SuppressWarnings("static-access")
	public Placeholders(Main plugin) {
		this.plugin = plugin;
	}

	public static String common(Player p, String s) {

		Map<String, String> placeHolders = new HashMap<>();
		placeHolders.put("<player>", p.getName());
		placeHolders.put("<world>", p.getWorld().getName());
		placeHolders.put("<online>", String.valueOf(Bukkit.getOnlinePlayers().size()));
		placeHolders.put("<level>", String.valueOf(p.getLevel()));
		placeHolders.put("<exp>", String.valueOf(p.getTotalExperience()));
		placeHolders.put("<ip>",
				p.getAddress().getAddress().getHostAddress() + ":" + String.valueOf(plugin.getServer().getPort()));
		placeHolders.put("<max_players>", String.valueOf(Bukkit.getMaxPlayers()));
		placeHolders.put("<uuid>", p.getUniqueId().toString());
		placeHolders.put("<x>", String.valueOf((int) p.getLocation().getX()));
		placeHolders.put("<y>", String.valueOf((int) (p.getLocation().getY())));
		placeHolders.put("<z>", String.valueOf((int) p.getLocation().getZ()));

		for (Entry<String, String> entry : placeHolders.entrySet()) {
			s = s.replace(entry.getKey(), entry.getValue());
		}

		return s;

	}
}
