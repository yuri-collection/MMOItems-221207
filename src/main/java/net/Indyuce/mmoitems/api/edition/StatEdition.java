package net.Indyuce.mmoitems.api.edition;

import org.bukkit.ChatColor;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class StatEdition extends ChatEditionBase {

	/*
	 * saves the data about the edited data so the plugin can edit the
	 * corresponding stat. some stats have complex chat formats, so the object
	 * array allow to save more complex edition info
	 */
	private ItemStat stat;
	private Object[] info;

	public StatEdition(EditionInventory inv, ItemStat stat, Object... info) {
		super(inv);
		this.stat = stat;
		this.info = info;
	}

	public ItemStat getStat() {
		return stat;
	}

	public Object[] getData() {
		return info;
	}

	@Override
	public void enable(String... messages) {
		getPlayer().closeInventory();

		getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
		for (String message : messages)
			getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', message));
		getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Type 'cancel' to abort editing.");

		/*
		 * anvil text input feature. enables players to use an anvil to input
		 * text if they are having conflicts with their chat management plugins.
		 */
		if (MMOItems.plugin.getConfig().getBoolean("anvil-text-input") && MMOItems.plugin.getVersion().isBelowOrEqual(1, 13)) {
			new AnvilGUI().open(this);
			return;
		}

		/*
		 * default chat edition feature
		 */
		new ChatEdition().open(this);
		MMOItems.plugin.getNMS().sendTitle(getPlayer(), ChatColor.GOLD + "" + ChatColor.BOLD + "Item Edition", "See chat.", 10, 40, 10);
	}

	@Override
	public void output(String output) {
		if (output.equals("cancel"))
			inv.open();
		else
			stat.whenInput((EditionInventory)inv, ((EditionInventory) inv).getItemType().getConfigFile(), output, info);
	}
}
