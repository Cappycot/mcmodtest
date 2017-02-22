package io.github.cappycot.tutorial;

import java.util.Random;

import io.github.cappycot.tutorial.pew.EntityPew;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = Tutorial.MOD_ID, name = Tutorial.MOD_NAME, version = Tutorial.VERSION, dependencies = Tutorial.DEPENDENCIES)
public class Tutorial {

	// Constants for state of mod...
	public static final String MOD_ID = "tutorial";
	public static final String MOD_NAME = "A Random Mod lol";
	public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES = "required-after:forge@[13.19.1.2189,)";
	public static final String RESOURCE_PREFIX = MOD_ID + ':';

	// Examples of mod side vars...
	public static Random random = new Random();

	@Instance(MOD_ID)
	public static Tutorial instance;

	@SidedProxy(clientSide = "io.github.cappycot.tutorial.ClientProxy", serverSide = "io.github.cappycot.tutorial.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		ResourceLocation loc = new ResourceLocation(Tutorial.RESOURCE_PREFIX + "pew");
		EntityRegistry.registerModEntity(loc, EntityPew.class, "pew", 0, this, 64, 10, true);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}
