package net.Indyuce.mmoitems.api;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.Indyuce.mmoitems.api.player.PlayerStats.CachedStats;
import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import net.mmogroup.mmolib.MMOLib;
import net.mmogroup.mmolib.api.item.NBTItem;

public class ProjectileData {
	private final NBTItem sourceItem;
	private final CachedStats playerStats;
	private final boolean customWeapon;

	public ProjectileData(NBTItem sourceItem, CachedStats playerStats, boolean customWeapon) {
		this.playerStats = playerStats;
		this.sourceItem = sourceItem;
		this.customWeapon = customWeapon;
	}

	public NBTItem getSourceItem() {
		return sourceItem;
	}

	public CachedStats getPlayerStats() {
		return playerStats;
	}

	/*
	 * if the item is an item from MMOItems, apply on-hit effects like critical
	 * strikes, pvp/pve damage and elemental damage
	 */
	public boolean isCustomWeapon() {
		return customWeapon;
	}

	public void applyEffects(LivingEntity target) {
		if(!sourceItem.hasTag("MMOITEMS_ARROW_POTION_EFFECTS")) return;
			
		for(JsonElement entry : MMOLib.plugin.getJson().parse(sourceItem.getString("MMOITEMS_ARROW_POTION_EFFECTS"), JsonArray.class)) {
			if(!entry.isJsonObject()) continue;
			JsonObject object = entry.getAsJsonObject();
			target.addPotionEffect(new PotionEffectData(PotionEffectType.getByName(object.get("type").getAsString()),
					object.get("duration").getAsDouble(), object.get("level").getAsInt()).toEffect());
		}
	}
}
