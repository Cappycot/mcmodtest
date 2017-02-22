package io.github.cappycot.tutorial;

import io.github.cappycot.tutorial.pew.EntityPew;
import io.github.cappycot.tutorial.pew.RenderPewFactory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		RenderingRegistry.registerEntityRenderingHandler(EntityPew.class, RenderPewFactory.INSTANCE);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		TutorialItems.initClient(Minecraft.getMinecraft().getRenderItem().getItemModelMesher());

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
