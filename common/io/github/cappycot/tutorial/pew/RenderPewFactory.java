package io.github.cappycot.tutorial.pew;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderPewFactory implements IRenderFactory<EntityPew> {

	public static final RenderPewFactory INSTANCE = new RenderPewFactory();
	
	@Override
	public Render<? super EntityPew> createRenderFor(RenderManager manager) {
		return new RenderPew(manager);
	}

}
