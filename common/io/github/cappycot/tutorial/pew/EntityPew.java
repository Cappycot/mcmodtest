package io.github.cappycot.tutorial.pew;

import java.util.Random;

import io.github.cappycot.tutorial.TutorialSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPew extends EntityArrow {

	public static final int MAX_BOUNCE = 16;
	public static final float SLOW_FACTOR = 2;
	public static final int TIME_TO_LIVE = 64;
	public static final Random random = new Random();
	private Entity owner = null;
	private int bounces = 0;
	private boolean slow = false;

	public EntityPew(World worldIn) {
		super(worldIn);
		pewInit();
	}

	public EntityPew(World worldIn, EntityLivingBase entity) {
		super(worldIn, entity);
		pewInit();
		owner = entity;
	}

	public EntityPew(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
		pewInit();
	}

	public void pewInit() {
		this.setSize(0.1F, 0.1F);
		this.setNoGravity(true);
	}

	@Override
	public boolean isBurning() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted > TIME_TO_LIVE) {
			setDead();
			for (int i = 0; i < 4; i++) {
				double x = (double) (rand.nextInt(10) - 5) / 12.0D;
				double y = (double) (rand.nextInt(10) - 5) / 12.0D;
				double z = (double) (rand.nextInt(10) - 5) / 12.0D;
				getEntityWorld().spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY, posZ, x, y, z);
			}
			// System.out.println("proj timed out");
		} else
			for (int i = 0; i < 4; i++) {
				double x = (double) (rand.nextInt(10) - 5) / 8.0D;
				double y = (double) (rand.nextInt(10) - 5) / 8.0D;
				double z = (double) (rand.nextInt(10) - 5) / 8.0D;
				getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, x, y, z);
			}
	}

	private BlockPos last = null;
	public static final boolean KILL_OWNER = false;

	@Override
	protected void onHit(RayTraceResult result) {
		// System.out.println("proj hit");
		Entity victim = result.entityHit;
		if (victim != null && (!victim.isEntityEqual(owner) || (bounces > 0 && KILL_OWNER))
				&& !(victim instanceof EntityPew)) {
			DamageSource damagesource = victim instanceof EntityEnderman
					? DamageSource.causePlayerDamage((EntityPlayer) owner)
					: DamageSource.causeArrowDamage(this, owner != null ? owner : this);
			victim.attackEntityFrom(damagesource, 420F);
			if (!victim.isEntityAlive() || victim instanceof EntityFallingBlock)
				victim.addVelocity(0, 1, 0);
		} else if (victim == null) {
			BlockPos hit = result.getBlockPos();
			IBlockState state = world.getBlockState(hit);
			if (last == null || !hit.equals(last)) {
				Material fate = state.getMaterial();
				Block block = state.getBlock();
				boolean bounce = true;
				block.onEntityCollidedWithBlock(this.world, hit, state, this);
				if (block instanceof BlockColored || block instanceof BlockStainedGlass
						|| block instanceof BlockStainedGlassPane || block instanceof BlockCarpet) {
					// BlockColored cblock = (BlockColored) block;
					// state.getProperties().replace(BlockColored.COLOR,
					// EnumDyeColor.byDyeDamage(0));
					if (!world.isRemote) {
						int asdf = random.nextInt(16);
						world.setBlockState(hit,
								state.withProperty(BlockColored.COLOR, EnumDyeColor.byDyeDamage(asdf)));
					}
				} else if (fate == Material.CAKE || fate == Material.CRAFTED_SNOW || fate == Material.GLASS
						|| fate == Material.ICE || fate == Material.LEAVES || fate == Material.SNOW) {
					world.destroyBlock(hit, false);
					bounce = false;
				} else if (fate == Material.CARPET || fate == Material.CIRCUITS) {
					world.destroyBlock(hit, true);
					bounce = false;
				} else if (fate == Material.PACKED_ICE) {
					world.destroyBlock(hit, false);
				} else if (fate == Material.CACTUS) {
					world.destroyBlock(hit, true);
				} else if (block instanceof BlockDoor) {
					BlockDoor door = (BlockDoor) block;
					door.toggleDoor(world, hit, !door.isOpen(world, hit));
				} else if (block instanceof BlockFalling) {
					((BlockFalling) block).updateTick(world, hit, state, null);
				}
				if (bounce) {
					switch (result.sideHit) {
					case UP:
					case DOWN:
						motionY *= -1;
						// addVelocity(0, motionY * -2, 0);
						break;
					case EAST:
					case WEST:
						motionX *= -1;
						// addVelocity(motionX * -2, 0, 0);
						break;
					case NORTH:
					case SOUTH:
						motionZ *= -1;
						// addVelocity(0, 0, motionZ * -2);
						break;
					default:
						break;
					}
					isAirBorne = true;
					last = hit;
					bounces++;
					if (bounces > MAX_BOUNCE)
						setDead();
				}
				ticksExisted = 0;
			}
		}
		getEntityWorld().playSound(null, posX, posY, posZ, TutorialSounds.pewhit, SoundCategory.PLAYERS, 1.0F, 1.0F);
		for (int i = 0; i < 4; i++) {
			double x = (double) (rand.nextInt(10) - 5) / 12.0D;
			double y = (double) (rand.nextInt(10) - 5) / 12.0D;
			double z = (double) (rand.nextInt(10) - 5) / 12.0D;
			getEntityWorld().spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY, posZ, x, y, z);
		}
		if (!slow) {
			motionX /= SLOW_FACTOR;
			motionY /= SLOW_FACTOR;
			motionZ /= SLOW_FACTOR;
			slow = true;
		}
	}

	@Override
	protected ItemStack getArrowStack() {
		return null;
	}
}
