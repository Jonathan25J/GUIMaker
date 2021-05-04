package me.jonaqhan.guimaker.gui;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.jonaqhan.guimaker.Main;
import me.jonaqhan.guimaker.object.Item;
import me.jonaqhan.guimaker.object.Settings;
import me.jonaqhan.guimaker.utils.Color;
import me.jonaqhan.guimaker.utils.Placeholders;

public class Loader {

	public Main plugin;

	public Loader(Main plugin) {
		this.plugin = plugin;
	}

	public Settings loadSettings(String name) {

		File dir = new File(plugin.getDataFolder() + File.separator + "GUI's");
		File[] files = dir.listFiles();

		if (files != null) {

			for (File file : files) {
				if (file.getName().replace(".yml", "").equalsIgnoreCase(name)) {
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);

					Settings data = new Settings(config.getString("settings.name"), config.getInt("settings.rows"),
							config.getStringList("settings.commands"), config.getString("settings.message"));

					return data;
				}
			}

		}

		return null;

	}

	public List<Item> loadItems(String name, Player p) {

		File dir = new File(plugin.getDataFolder() + File.separator + "GUI's");
		File[] files = dir.listFiles();
		List<Item> total = new ArrayList<>();
		;

		if (files != null) {

			for (File file : files) {
				if (file.getName().replace(".yml", "").equalsIgnoreCase(name)) {
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					config.getKeys(false).forEach(key -> {
						if (!key.equalsIgnoreCase("settings")) {

							ItemStack item = getItem(key, config, file, p);

							int slot = getSlot(config, key);

							List<String> commands = config.getStringList(key + ".Commands");

							String[] settings = getSettings(config, key);

							Boolean keepOpen = Boolean.valueOf(settings[0]);

							String permission = settings[1];

							String accessDenied = settings[2];

							Item data = new Item(item, commands, slot, keepOpen, permission, accessDenied);

							total.add(data);
						}

					});

				}
			}
		}
		return total;

	}

	public int getSlot(FileConfiguration config, String key) {
		int x = config.getInt(key + ".X");
		int y = config.getInt(key + ".Y");

		int slot = 0;
		int[] one = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] two = { 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		int[] three = { 19, 20, 21, 22, 23, 24, 25, 26, 27 };
		int[] four = { 28, 29, 30, 31, 32, 33, 34, 35, 36 };
		int[] five = { 37, 38, 39, 40, 41, 42, 43, 44, 45 };
		int[] six = { 46, 47, 48, 49, 50, 51, 52, 53, 54 };

		if (y == 1)
			slot = one[x - 1] - 1;
		if (y == 2)
			slot = two[x - 1] - 1;
		if (y == 3)
			slot = three[x - 1] - 1;
		if (y == 4)
			slot = four[x - 1] - 1;
		if (y == 5)
			slot = five[x - 1] - 1;
		if (y == 6)
			slot = six[x - 1] - 1;

		return slot;

	}

	public Enchantment getEnchantment(String enchantment) {

		Map<String, Enchantment> ecm = new HashMap<>();

		ecm.put("power", Enchantment.ARROW_DAMAGE);
		ecm.put("flame", Enchantment.ARROW_FIRE);
		ecm.put("infinity", Enchantment.ARROW_INFINITE);
		ecm.put("punch", Enchantment.ARROW_KNOCKBACK);
		ecm.put("curse of binding", Enchantment.BINDING_CURSE);
		ecm.put("channeling", Enchantment.CHANNELING);
		ecm.put("sharpness", Enchantment.DAMAGE_ALL);
		ecm.put("bane of arthropods", Enchantment.DAMAGE_ARTHROPODS);
		ecm.put("smite", Enchantment.DAMAGE_UNDEAD);
		ecm.put("depth strider", Enchantment.DEPTH_STRIDER);
		ecm.put("efficiency", Enchantment.DIG_SPEED);
		ecm.put("unbreaking", Enchantment.DURABILITY);
		ecm.put("fire aspect", Enchantment.FIRE_ASPECT);
		ecm.put("frost walker", Enchantment.FROST_WALKER);
		ecm.put("impaling", Enchantment.IMPALING);
		ecm.put("knockback", Enchantment.KNOCKBACK);
		ecm.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
		ecm.put("looting", Enchantment.LOOT_BONUS_MOBS);
		ecm.put("loyalty", Enchantment.LOYALTY);
		ecm.put("luck", Enchantment.LUCK);
		ecm.put("lure", Enchantment.LURE);
		ecm.put("mending", Enchantment.MENDING);
		ecm.put("multishot", Enchantment.MULTISHOT);
		ecm.put("respiration", Enchantment.OXYGEN);
		ecm.put("piercing", Enchantment.PIERCING);
		ecm.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
		ecm.put("blast protection", Enchantment.PROTECTION_EXPLOSIONS);
		ecm.put("fall protection", Enchantment.PROTECTION_FALL);
		ecm.put("fire protection", Enchantment.PROTECTION_FIRE);
		ecm.put("projectile protection", Enchantment.PROTECTION_PROJECTILE);
		ecm.put("quick charge", Enchantment.QUICK_CHARGE);
		ecm.put("riptide", Enchantment.RIPTIDE);
		ecm.put("silk touch", Enchantment.SILK_TOUCH);
		ecm.put("soul speed", Enchantment.SOUL_SPEED);
		ecm.put("sweeping edge", Enchantment.SWEEPING_EDGE);
		ecm.put("thorns", Enchantment.THORNS);
		ecm.put("curse of vanishing", Enchantment.VANISHING_CURSE);
		ecm.put("aqua affinity", Enchantment.WATER_WORKER);

		for (Entry<String, Enchantment> entry : ecm.entrySet()) {

			if (enchantment.equalsIgnoreCase(entry.getKey())) {

				return entry.getValue();

			}

		}

		return null;
	}

	@SuppressWarnings("deprecation")
	public ItemStack getItem(String key, FileConfiguration config, File file, Player p) {

		String material = config.getString(key + ".Material").toUpperCase();

		ItemStack item = null;

		try {
			item = new ItemStack(Material.getMaterial(material));
		} catch (Exception e) {

			plugin.getServer().getConsoleSender().sendMessage(
					Color.text("&6GUIMaker: Material cannot be found! &c(" + file.getName() + ":" + key + ")"));
			item = new ItemStack(Material.STONE);
		} finally {

			if (item.getType() == Material.PLAYER_HEAD) {

				SkullMeta head = (SkullMeta) item.getItemMeta();

				if (config.getString(key + ".Head") != null) {

					if (config.getString(key + ".Head").equalsIgnoreCase("me")) {
						head.setOwner(p.getName());

					} else {
						head.setOwner(config.getString(key + ".Head"));
					}

					item.setItemMeta(head);
				}

			}

		}

		if (config.getString(key + ".Amount") != null) {
			item.setAmount(config.getInt(key + ".Amount"));

		}

		ItemMeta meta = item.getItemMeta();
		if (config.getString(key + ".Name") != null) {
			meta.setDisplayName(Placeholders.common(p, Color.text(config.getString(key + ".Name"))));
		}

		if (config.getStringList(key + ".Lore") != null) {

			List<String> lores = config.getStringList(key + ".Lore");
			List<String> cLores = new ArrayList<>();

			for (String lore : lores) {
				cLores.add(Placeholders.common(p, Color.text("&7" + lore)));
			}

			meta.setLore(cLores);
		}

		if (config.getStringList(key + ".Enchantments") != null) {
			List<String> enchantments = config.getStringList(key + ".Enchantments");

			for (String enchantment : enchantments) {
				String[] el = enchantment.split(",");
				int level = Integer.parseInt(el[1].replace(" ", ""));

				try {

					meta.addEnchant(getEnchantment(el[0]), level, true);
				} catch (Exception e) {
					plugin.getServer().getConsoleSender().sendMessage(Color
							.text("&6GUIMaker: Enchantment cannot be found! &c(" + file.getName() + ":" + key + ")"));
				}
			}

		}

		if (config.getString(key + ".Hide-enchantments") != null) {

			if (config.getBoolean(key + ".Hide-enchantments") == true) {
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

			}

		}
		item.setItemMeta(meta);

		if (config.getString(key + ".Durability") != null) {

			int number = Integer.valueOf(config.getString(key + ".Durability"));

			Damageable damage = (Damageable) item.getItemMeta();
			damage.setDamage(number);
			item.setItemMeta((ItemMeta) damage);

		}

		return item;

	}

	public String[] getSettings(FileConfiguration config, String key) {

		Boolean keepOpen = false;

		if (config.getString(key + ".Keep-open") != null) {
			keepOpen = config.getBoolean(key + ".Keep-open");

		}

		String permission = null;

		if (config.getString(key + ".Permission") != null) {
			permission = config.getString(key + ".Permission");

		}

		String accessDenied = null;

		if (config.getString(key + ".Access-denied") != null) {
			accessDenied = config.getString(key + ".access-denied");

		}

		return new String[] { keepOpen.toString(), permission, accessDenied };

	}

}
