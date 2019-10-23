package net.Indyuce.mmoitems.api.edition;

import org.bukkit.ChatColor;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.process.AnvilGUI;
import net.Indyuce.mmoitems.api.edition.process.ChatEdition;
import net.Indyuce.mmoitems.gui.PluginInventory;
import net.Indyuce.mmoitems.gui.edition.BlockEdition.ConfigOptions;

public class BlockChatEdition implements Edition {

	/*
	 * saves the data about the edited data so the plugin can edit the
	 * corresponding stat. some stats have complex chat formats, so the object
	 * array allow to save more complex edition info
	 */
	private final PluginInventory inv;
	private final ConfigOptions option;
	private final int blockId;

	public BlockChatEdition(PluginInventory inv, ConfigOptions co, int id) {
		this.inv = inv;
		this.option = co;
		this.blockId = id;
	}

	public ConfigOptions getConfigOption() {
		return option;
	}

	public void enable(String... message) {
		inv.getPlayer().closeInventory();

		inv.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
		for (String line : message)
			inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', line));
		inv.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Type 'cancel' to abort editing.");

		/*
		 * anvil text input feature. enables players to use an anvil to input
		 * text if they are having conflicts with their chat management plugins.
		 */
		if (MMOItems.plugin.getConfig().getBoolean("anvil-text-input") && MMOItems.plugin.getVersion().isBelowOrEqual(1, 13)) {
			new AnvilGUI(inv, this);
			return;
		}

		/*
		 * default chat edition feature
		 */
		new ChatEdition(inv, this);
		MMOItems.plugin.getNMS().sendTitle(inv.getPlayer(), ChatColor.GOLD + "" + ChatColor.BOLD + "Block Edition", "See chat.", 10, 40, 10);
	}

	@Override
	public boolean output(String input) {
		return input.equals("cancel") || option.whenInput(inv, input, blockId + "." + option.getConfigPath());
	}

	@Override
	public boolean shouldGoBack() {
		return true;
	}
}