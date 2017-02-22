package io.github.cappycot.tutorial;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TutorialSounds {

	public static SoundEvent pewfire;
	public static SoundEvent pewhit;
	
	public static void registerSounds() {
		pewfire = registerSound("pewfire");
		pewhit = registerSound("pewhit");
	}
	
	private static SoundEvent registerSound(String soundName) {
		ResourceLocation id = new ResourceLocation(Tutorial.MOD_ID, soundName);
		return GameRegistry.register(new SoundEvent(id).setRegistryName(id));
	}
}
