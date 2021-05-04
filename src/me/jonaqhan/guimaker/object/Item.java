package me.jonaqhan.guimaker.object;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Item {
	public ItemStack item;
	public List<String> commands;
	public int slot;
	public Boolean keepOpen;

	public Item(ItemStack item, List<String> commands, int slot, Boolean keepOpen, String permission,
			String accessDenied) {
		super();
		this.item = item;
		this.commands = commands;
		this.slot = slot;
		this.keepOpen = keepOpen;
		this.permission = permission;
		this.accessDenied = accessDenied;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public String permission;
	public String accessDenied;

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	public Boolean getKeepOpen() {
		return keepOpen;
	}

	public void setKeepOpen(Boolean keepOpen) {
		this.keepOpen = keepOpen;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getAccessDenied() {
		return accessDenied;
	}

	public void setAccessDenied(String accessDenied) {
		this.accessDenied = accessDenied;
	}

}
