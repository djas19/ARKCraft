package com.uberverse.arkcraft.client.gui.engram;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.uberverse.arkcraft.ARKCraft;
import com.uberverse.arkcraft.client.gui.component.GuiTexturedButton;
import com.uberverse.arkcraft.client.gui.scrollable.GUIScrollable;
import com.uberverse.arkcraft.common.container.engram.ContainerEngramCrafting;
import com.uberverse.arkcraft.common.container.engram.ContainerEngramCrafting.EngramSlot;
import com.uberverse.arkcraft.common.engram.IEngramCrafter;
import com.uberverse.arkcraft.common.engram.EngramManager.Engram;
import com.uberverse.arkcraft.common.engram.EngramManager.EngramRecipe;
import com.uberverse.arkcraft.util.I18n;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class GUIEngramCrafting extends GUIScrollable
{
	public static final ResourceLocation buttons = new ResourceLocation(ARKCraft.MODID, "textures/gui/buttons.png");

	protected int buttonCounter = 0;
	private GuiButton craft, craftall;

	public GUIEngramCrafting(ContainerEngramCrafting container)
	{
		super(container);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		craft = new GuiTexturedButton(buttonCounter++, guiLeft + getC1ButtonX(), guiTop + getC1ButtonY(), getC1ButtonWidth(), getC1ButtonHeight(),
				getC1ButtonResource(), getC1ButtonU(), getC1ButtonV());
		craftall = new GuiTexturedButton(buttonCounter++, guiLeft + getCAButtonX(), guiTop + getCAButtonY(), getCAButtonWidth(), getCAButtonHeight(),
				getCAButtonResource(), getCAButtonU(), getCAButtonV());
		this.buttonList.add(craft);
		this.buttonList.add(craftall);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button == craft)
		{
			((ContainerEngramCrafting) inventorySlots).craftOne();
			mc.playerController.sendEnchantPacket(inventorySlots.windowId, 0);
		}
		else if (button == craftall)
		{
			((ContainerEngramCrafting) inventorySlots).craftAll();
			mc.playerController.sendEnchantPacket(inventorySlots.windowId, 1);
		}
		else super.actionPerformed(button);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		ContainerEngramCrafting c = (ContainerEngramCrafting) inventorySlots;
		for (Object o : inventorySlots.inventorySlots)
		{
			if (o instanceof EngramSlot)
			{
				EngramSlot e = (EngramSlot) o;
				IEngramCrafter ec = c.getCrafter();
				if (ec.isCrafting())
				{
					if (e.getEngram().getId() == ec.getCraftingQueue().peek().getEngram().getId())
					{
						double fraction = ec.getRelativeProgress();
						if (fraction == 0) { return; }
						int x = e.xDisplayPosition;
						int y = e.yDisplayPosition + 16;

						int red = (int) (255 * (1 - fraction));
						int green = (int) (255 * fraction);
						int blue = 0;
						int alpha = 96;

						int color = new Color(red, green, blue, alpha).getRGB();

						drawRect(x, (int) (y - (fraction * 16)), x + 16, y, color);
					}
				}
			}
		}

		// GlStateManager.enableDepth();
		// GlStateManager.scale(0.5, 0.5, 0.5);
		// ItemStack itemstack = new ItemStack(ARKCraftItems.amarBerry);
		// this.itemRender.renderItemAndEffectIntoGUI(itemstack, 100,100);
		// this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, 100, 100, "64");
		// GlStateManager.scale(2, 2, 2);
	}

	private Engram tooltipped;
	private List<EngramRecipe> recipes = new ArrayList<>();
	private int shown;
	private int ticker;

	@Override
	protected void drawHoveringText(List textLines, int x, int y, FontRenderer font)
	{
		for (Object o : inventorySlots.inventorySlots)
		{
			if (o instanceof EngramSlot)
			{
				EngramSlot e = (EngramSlot) o;
				if (isPointInRegion(e.xDisplayPosition, e.yDisplayPosition, 18, 18, x, y))
				{
					ticker++;
					if (tooltipped != e.getEngram())
					{
						tooltipped = e.getEngram();
						recipes.clear();
						recipes.addAll(tooltipped.getRecipes());
						shown = 0;
						ticker = 0;
					}
					if (ticker == 30)
					{
						ticker = 0;
						shown = (shown + 1 == recipes.size()) ? 0 : shown + 1;
					}
					textLines.clear();
					textLines.add(tooltipped.getTitle());
					EngramRecipe er = recipes.get(shown);
					for (Item i : er.getItems().keySet())
						textLines.add(EnumChatFormatting.GOLD + I18n.format("gui.engramcrafting.engram.tooltip.ingredient",
								StatCollector.translateToLocal(i.getUnlocalizedName() + ".name"), er.getItems().get(i)));
				}
			}
		}

		super.drawHoveringText(textLines, x, y, font);
	}

	public abstract int getC1ButtonX();

	public abstract int getC1ButtonY();

	public int getC1ButtonU()
	{
		return 0;
	}

	public int getC1ButtonV()
	{
		return 42;
	}

	public int getC1ButtonWidth()
	{
		return 32;
	}

	public int getC1ButtonHeight()
	{
		return 14;
	}

	public ResourceLocation getC1ButtonResource()
	{
		return buttons;
	}

	public abstract int getCAButtonX();

	public abstract int getCAButtonY();

	public int getCAButtonU()
	{
		return 0;
	}

	public int getCAButtonV()
	{
		return 0;
	}

	public int getCAButtonWidth()
	{
		return 47;
	}

	public int getCAButtonHeight()
	{
		return 14;
	}

	public ResourceLocation getCAButtonResource()
	{
		return buttons;
	}

}