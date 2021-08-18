package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff;

import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.ItemAttackMetadata;
import org.bukkit.Location;

import java.util.Random;

public interface StaffAttackHandler {
	static final Random random = new Random();

	void handle(ItemAttackMetadata attackMeta, NBTItem nbt, double attackDamage, double range);

	default Location getGround(Location loc) {
		for (int j = 0; j < 20; j++) {
			if (loc.getBlock().getType().isSolid())
				return loc;
			loc.add(0, -1, 0);
		}
		return loc;
	}
}
