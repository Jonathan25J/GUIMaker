package me.jonaqhan.guimaker;

import org.bukkit.plugin.java.JavaPlugin;

import me.jonaqhan.guimaker.events.Click;
import me.jonaqhan.guimaker.events.Command;
import me.jonaqhan.guimaker.gui.Loader;
import me.jonaqhan.guimaker.gui.Maker;
import me.jonaqhan.guimaker.utils.Placeholders;

public class Main extends JavaPlugin {

	public void onEnable() {

		new Maker(this);
		new Loader(this);
		new Command(this);
		new Click(this);
		new Placeholders(this);

	}

	public void onDisable() {

	}

}