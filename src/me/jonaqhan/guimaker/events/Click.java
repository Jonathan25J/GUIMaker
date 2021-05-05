package me.jonaqhan.guimaker.events;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import me.jonaqhan.guimaker.Main;
import me.jonaqhan.guimaker.gui.Maker;
import me.jonaqhan.guimaker.utils.Color;
import me.jonaqhan.guimaker.utils.Placeholders;

public class Click implements Listener {

	public Main plugin;

	public Click(Main plugin) {
		this.plugin = plugin;

		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getCurrentItem() == null)
			return;

		File file = getFile(e);

		if (file == null)
			return;

		e.setCancelled(true);

		if (e.getClickedInventory().getType() == InventoryType.PLAYER)
			return;

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		int[] coords = findCoords(e.getSlot());
		int x = coords[0];
		int y = coords[1];

		config.getKeys(false).forEach(key -> {
			if (!key.equalsIgnoreCase("settings")) {

				if (config.getInt(key + ".X") == x && config.getInt(key + ".Y") == y) {

					if (config.getString(key + ".Permission") != null)
						if (!checkPermission(config, key, p))
							return;

					if (config.getString(key + ".Required-items") != null) {
						boolean allowed = getRequiredItems(p, config, key);

						if (!allowed)
							if (!checkRequiredItems(config, key, p))
								return;

					}

					if (config.getStringList(key + ".Commands") != null)
						executeCommands(config, key, p);

					if (config.getString(key + ".Keep-open") != null)
						if (config.getBoolean(key + ".Keep-open") == true)
							return;

					p.closeInventory();
					p.updateInventory();
					return;

				}

			}

		});

	}

	public int[] findCoords(int slot) {

		int y = (int) Math.floor(slot / (double) 9 + 1);

		int x = 5;
		double x_ = 5.5;

		if (y == 1) {
			x_ = (slot + 1) / (double) 9 * 10;

		}

		if (y > 1) {
			x_ = ((slot + 1) / (double) 9 - (y - 1)) * 10;

		}
		if (x_ - Math.floor(x_) == 0) {
			y = (int) (slot / (double) 9);
			x = 9;

		}

		x = (int) x_;
		if (x == 10) {
			x = 9;
			y += 1;

		}
		return new int[] { x, y };
	}

	public File getFile(InventoryClickEvent e) {

		Map<String, String> titles = new HashMap<>();

		File[] files = new File(plugin.getDataFolder() + File.separator + "GUI's").listFiles();

		if (files != null) {

			for (File file : files) {
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				String title = config.getString("settings.name");

				titles.put(title, file.getName());

			}
		}

		for (Entry<String, String> entry : titles.entrySet()) {

			if (e.getView().getTitle().equals(Color.text(entry.getKey()))) {

				for (File file : files) {

					if (file.getName().equals(entry.getValue())) {

						return file;

					}

				}

			}

		}
		return null;

	}

	public Boolean getRequiredItems(Player p, FileConfiguration config, String key) {

		List<String> items = config.getStringList(key + ".Required-items");
		boolean status = true;

		while (status == true) {

			for (String item : items) {
				String material = item.split(",")[0].trim().toUpperCase();
				int amount = Integer.valueOf(item.split(",")[1].trim());

				ItemStack is = new ItemStack(Material.getMaterial(material), amount);

				status = p.getInventory().contains(is);

				if (item == items.get(items.size() - 1)) {
					return status;
				}
			}
		}

		return status;

	}

	public boolean checkPermission(FileConfiguration config, String key, Player p) {

		boolean status = true;

		if (!p.hasPermission(config.getString(key + ".Permission"))) {

			if (config.getString(key + ".Access-denied") != null) {
				p.sendMessage(Color.text(config.getString(key + ".Access-denied")));
				status = false;
				return status;

			}
			p.sendMessage(
					Color.text("&cYou don't have the&6 " + config.getString(key + ".Permission") + "&c permission!"));
			status = false;
			return status;
		}

		return status;
	}

	public boolean checkRequiredItems(FileConfiguration config, String key, Player p) {
		boolean status = true;
		if (config.getString(key + ".Required-denied") != null) {

			p.sendMessage(Color.text(config.getString(key + ".Required-denied")));
			status = false;
			return status;

		} else {
			List<String> items = config.getStringList(key + ".Required-items");

			for (String item : items) {
				p.sendMessage(Color.text("&bYou need to have&a " + item.split(",")[1].trim() + "&3 "
						+ item.split(",")[0].trim().replace("_", " ") + "&b to do this"));

			}
			status = false;
			return status;
		}

	}

	public void executeCommands(FileConfiguration config, String key, Player p) {

		List<String> commands = config.getStringList(key + ".Commands");

		for (String command : commands) {
			if (!command.contains(",")) {
				p.performCommand(command);

			} else {
				String[] args = command.split(",");

				if (args[0].trim().equalsIgnoreCase("console")) {
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					Bukkit.dispatchCommand(console, Placeholders.common(p, args[1].substring(1)));
				}

				if (args[0].trim().equalsIgnoreCase("op")) {

					if (p.isOp()) {
						p.performCommand(Placeholders.common(p, args[1].substring(1)));

					} else {
						p.setOp(true);
						p.performCommand(Placeholders.common(p, args[1].substring(1)));
						p.setOp(false);
					}
				}

				if (args[0].trim().equalsIgnoreCase("open")) {
					Maker maker = new Maker(plugin);
					File[] files = new File(plugin.getDataFolder() + File.separator + "GUI's").listFiles();

					for (File fileS : files) {

						if (fileS.getName().equalsIgnoreCase(args[1].substring(1).trim())) {
							FileConfiguration configS = YamlConfiguration.loadConfiguration(fileS);
							List<String> cmds = configS.getStringList("settings.commands");

							maker.onCommand(p, null, cmds.get(0), null);
						}

					}

				}

				if (args[0].trim().equalsIgnoreCase("give")) {
					String[] content = args[1].substring(1).split(" ");
					String material = content[0].toUpperCase();
					int amount = Integer.valueOf(content[1]);

					ItemStack item = new ItemStack(Material.getMaterial(material), amount);

					p.getInventory().addItem(item);
				}

				if (args[0].trim().equalsIgnoreCase("tell")) {
					String message = Placeholders.common(p, Color.text(args[1].substring(1)));
					p.sendMessage(message);
				}

			}

		}

	}

}
