package com.uberverse.arkcraft.rework.container;

import com.uberverse.arkcraft.rework.engram.EngramManager.EngramType;
import com.uberverse.arkcraft.rework.tileentity.TileEntitySmithy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSmithy extends ContainerEngramCrafting
{
	public ContainerSmithy(EntityPlayer player, TileEntitySmithy tileEntity)
	{
		super(EngramType.SMITHY, player, tileEntity);
	}

	@Override
	public int getScrollableSlotsWidth()
	{
		return 3;
	}

	@Override
	public int getScrollableSlotsHeight()
	{
		return 5;
	}

	@Override
	public int getScrollableSlotsX()
	{
		return 98;
	}

	@Override
	public int getScrollableSlotsY()
	{
		return 18;
	}

	@Override
	public int getPlayerInventorySlotsX()
	{
		return 8;
	}

	@Override
	public int getPlayerInventorySlotsY()
	{
		return 140;
	}

	@Override
	public int getPlayerHotbarSlotsX()
	{
		return 8;
	}

	@Override
	public int getPlayerHotbarSlotsY()
	{
		return 198;
	}

	@Override
	public int getInventorySlotsX()
	{
		return 8;
	}

	@Override
	public int getInventorySlotsY()
	{
		return 18;
	}

	@Override
	public int getInventorySlotsWidth()
	{
		return 4;
	}

	@Override
	public int getInventorySlotsHeight()
	{
		return 6;
	}
}
