package com.uberverse.arkcraft.common.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.uberverse.arkcraft.ARKCraft;

public class ItemARKArmor extends ItemArmor
{

	public String texName;
	public boolean golden;

	public ItemARKArmor(ArmorMaterial mat, String texName, EntityEquipmentSlot type, boolean golden)
	{
		super(mat, 0, type);
		this.setCreativeTab(ARKCraft.tabARK);
		this.golden = golden;
		this.texName = texName;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {//TODO: check
		return ARKCraft.MODID + ":textures/armor/" + this.texName + "_" + (this.armorType == EntityEquipmentSlot.FEET ? "2" : "1") + ".png";
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		if (golden) { return ChatFormatting.GOLD + super.getItemStackDisplayName(stack); }
		return super.getItemStackDisplayName(stack);
	}
}
