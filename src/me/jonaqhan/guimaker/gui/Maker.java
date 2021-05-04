package me.jonaqhan.guimaker.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.jonaqhan.guimaker.Main;
import me.jonaqhan.guimaker.object.Item;
import me.jonaqhan.guimaker.object.Settings;
import me.jonaqhan.guimaker.utils.Color;

public class Maker {

	public Main plugin;

	public Maker(Main plugin) {
		this.plugin = plugin;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {

		Map<String, String> commands = loadCommands();
		Player p = (Player) sender;

		for (Entry<String, String> entry : commands.entrySet()) {
			if (command.equalsIgnoreCase(entry.getKey())) {
				File[] files = new File(plugin.getDataFolder() + File.separator + "GUI's").listFiles();

				for (File file : files) {

					if (file.getName().equalsIgnoreCase(entry.getValue())) {
						String name = file.getName().replace(".yml", "");
						Loader loader = new Loader(plugin);

						Settings settings = loader.loadSettings(name);
						List<Item> items = loader.loadItems(name, p);
						Inventory gui = createGUI(settings, items, p);
						p.openInventory(gui);
						p.sendMessage(Color.text(settings.getMessage()));

					}

				}

			}

		}

		return false;
	}

	public Map<String, String> loadCommands() {

		File dir = new File(plugin.getDataFolder() + File.separator + "GUI's");
		File[] files = dir.listFiles();
		if (!dir.exists()) {
			dir.mkdir();
			createExample();

		}
		Map<String, String> commands = new HashMap<>();

		if (files != null) {

			for (File file : files) {
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				List<String> strings = config.getStringList("settings.commands");
				for (String s : strings) {
					commands.put(s, file.getName());

				}

			}

		}

		return commands;

	}

	public Inventory createGUI(Settings settings, List<Item> items, Player p) {
		Inventory inv = Bukkit.createInventory(null, settings.getRows() * 9, Color.text(settings.title));

		for (Item item : items) {
			inv.setItem(item.slot, item.item);

		}

		return inv;

	}

	public void createExample() {
		try {
			InputStream stream = plugin.getResource("example.yml");
			FileUtils.copyInputStreamToFile(stream,
					new File(plugin.getDataFolder() + File.separator + "GUI's", "example.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
