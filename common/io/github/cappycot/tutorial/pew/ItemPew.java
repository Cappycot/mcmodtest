package io.github.cappycot.tutorial.pew;

import io.github.cappycot.tutorial.Tutorial;
import io.github.cappycot.tutorial.TutorialNames;
import io.github.cappycot.tutorial.TutorialSounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPew extends Item {

	public static final int COOLDOWN = 8;

	public ItemPew() {
		this.setMaxStackSize(1);
		this.setMaxDamage(256);
		this.setCreativeTab(CreativeTabs.COMBAT);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		boolean lefty = hand == EnumHand.OFF_HAND;
		fire(world, player, 2.0F, lefty ? 2.0F : 1.0F);
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + Tutorial.RESOURCE_PREFIX + TutorialNames.PEW;
	}

	private void fire(World world, EntityLivingBase player, float spd, float inacc) {
		if (!world.isRemote) {
			EntityPew proj = new EntityPew(world, player);
			proj.setAim(player, player.rotationPitch, player.rotationYaw, 0.0F, spd, inacc);
			world.spawnEntity(proj);
			world.playSound(null, player.posX, player.posY, player.posZ, TutorialSounds.pewfire, SoundCategory.PLAYERS,
					1.0F, 1.0F);
		}

	}

}
