package io.github.cappycot.tutorial;

import io.github.cappycot.tutorial.pew.ItemPew;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class TutorialItems {
	
	public static ItemPew pewpew;

	public static final void init() {

		pewpew = new ItemPew();
		pewpew.setRegistryName(new ResourceLocation(Tutorial.MOD_ID, TutorialNames.PEW));
		GameRegistry.register(pewpew);

	}
	
	@SideOnly(Side.CLIENT)
	public static void initClient(ItemModelMesher mesher) {
		
		ModelResourceLocation model = new ModelResourceLocation(Tutorial.RESOURCE_PREFIX + TutorialNames.PEW, "inventory");
		ModelLoader.registerItemVariants(pewpew, model);
		mesher.register(pewpew, 0, model);
		
	}

}
