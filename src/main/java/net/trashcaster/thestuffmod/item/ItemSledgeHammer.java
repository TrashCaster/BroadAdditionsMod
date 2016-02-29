package net.trashcaster.thestuffmod.item;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.trashcaster.bam.BroadAdditionsMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSledgeHammer extends ItemTool {

	private final String name = "sledge_hammer";
	private int count = 0;
	
	public ItemSledgeHammer() {
		super(6f, ToolMaterial.IRON, new HashSet<Block>());
		GameRegistry.registerItem(this, name);
		GameRegistry.addRecipe(new ItemStack(this), " I "," SI", "S  ", 'I', Items.iron_ingot, 'S', Items.stick);
		setUnlocalizedName(BroadAdditionsMod.MODID+"."+name);
		setCreativeTab(CreativeTabs.tabTools);
        setMaxDamage(ToolMaterial.IRON.getMaxUses()/2);
	}
	
	public String getName() {
		return name;
	}

    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    	//playerIn.addStat(BroadAdditionsMod.MAKE_HAMMER, 1);
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
    	NBTTagCompound nbt = stack.getTagCompound();
        if ((double)blockIn.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(1, playerIn);
        }
    	int meta = nbt.getInteger("LastHitMeta");
    	//List<Smashable> smashable = TSMMain.getSmashablesByBlock(blockIn, meta);
//        if (smashable != null && !smashable.isEmpty()) {
//        	//int items = 1+new Random().nextInt(RecipeRubble.REQUIRED_RUBBLE)+new Random().nextInt(1+EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
//			//playerIn.playSound(TSMMain.MODID+":hammer.swing", 0.05f, 1.0f+new Random().nextFloat()*0.5f);
//			if (blockIn.getMaterial().equals(Material.wood)) {
//			    playerIn.playSound("mob.zombie.woodbreak", 0.25f, 1.5f+new Random().nextFloat()*0.5f);
//			}
//			if (blockIn.getMaterial().equals(Material.rock)) {
//				playerIn.playSound("mob.zombie.remedy", 0.25f, 1.5f+new Random().nextFloat()*0.5f);
//			}
//			for (int i=0; i<items; i++) {
//			    Block.spawnAsEntity(worldIn, pos, smashable.get(new Random().nextInt(smashable.size())).createSmashedRemnant(1));
//			}
//			if (worldIn.isRemote) {
//			    blockIn.addDestroyEffects(worldIn, pos, Minecraft.getMinecraft().effectRenderer);
//			}
//			worldIn.setBlockToAir(pos);
//		}
        return true;
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
    	if (!entityLiving.isSwingInProgress) {
    		MovingObjectPosition mop = entityLiving.rayTrace(4.5d, 1f);
    		boolean attemptPlay = true;
    		if (mop != null) {
    			if (mop.entityHit != null) {
    				attemptPlay = false;
    				//entityLiving.playSound(TSMMain.MODID+":hammer.swing", 0.25f, 1.0f+new Random().nextFloat()*0.5f);
    			} else {
    				if (mop.getBlockPos() != null) {
    					BlockPos pos = mop.getBlockPos();
    					Block block = entityLiving.getEntityWorld().getBlockState(pos).getBlock();
    		        	NBTTagCompound nbt = stack.getTagCompound();
    		        	if (nbt == null) {
    		        		nbt = new NBTTagCompound();
    		        	}
    		        	nbt.setInteger("LastHitMeta", block.getMetaFromState(entityLiving.getEntityWorld().getBlockState(pos)));
    		        	stack.setTagCompound(nbt);
    					if (block.getMaterial().equals(Material.rock)) {
    	    				attemptPlay = false;
    	    				//entityLiving.playSound(TSMMain.MODID+":hammer.swing", 0.05f, 1.0f+new Random().nextFloat()*0.5f);
    	    				//entityLiving.playSound(TSMMain.MODID+":hammer.stone", 0.25f, 1.0f+new Random().nextFloat()*0.5f);
    					} else if (block.getMaterial().equals(Material.wood)) {
    	    				attemptPlay = false;
    	    				//entityLiving.playSound(TSMMain.MODID+":hammer.swing", 0.05f, 1.0f+new Random().nextFloat()*0.5f);
    	    				//entityLiving.playSound("mob.zombie.wood", 0.25f, 1.5f+new Random().nextFloat()*0.5f);
    					} else if (block.getMaterial().equals(Material.grass) || block.getMaterial().equals(Material.ground) || block.getMaterial().equals(Material.sand)) {
    	    				attemptPlay = false;
    	    				//entityLiving.playSound(TSMMain.MODID+":hammer.swing", 0.05f, 1.0f+new Random().nextFloat()*0.5f);
    	    				//entityLiving.playSound("dig.grass", 2f, 1.5f+new Random().nextFloat()*0.5f);
    					} else if (block.getMaterial().equals(Material.iron) || block.getMaterial().equals(Material.anvil)) {
    	    				attemptPlay = false;
    	    				//entityLiving.playSound(TSMMain.MODID+":hammer.swing", 0.05f, 1.0f+new Random().nextFloat()*0.5f);
    	    				//entityLiving.playSound("mob.zombie.metal", 0.25f, 1.5f+new Random().nextFloat()*0.5f);
    					}
    				}
    			}
    		}
    		if (attemptPlay) {
    		    //entityLiving.playSound(TSMMain.MODID+":hammer.swing", 0.25f, 1.0f+new Random().nextFloat()*0.5f);
    		}
    	}
    	if (entityLiving.isSwingInProgress) {
    		return true;
    	}
    	return false;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block)
    {
        if (block.getMaterial().equals(Material.rock))
        {
            return 15.0f-(block.getBlockHardness((World)null, (BlockPos)null));
        } else if (block.getMaterial().equals(Material.wood)) {
            return 15.0f-(block.getBlockHardness((World)null, (BlockPos)null))/2f;
    } else if (block.getMaterial().equals(Material.cloth)) {
        return 15.0f-(block.getBlockHardness((World)null, (BlockPos)null))/4f;
    } else if (block.getMaterial().equals(Material.cactus)) {
        return 15.0f-(block.getBlockHardness((World)null, (BlockPos)null))/16f;
    } else {
            Material material = block.getMaterial();
            return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	if (target instanceof EntitySkeleton) {
    	    //target.playSound(TSMMain.MODID+":hammer.hitbone", 1f, 0.75f+new Random().nextFloat()*0.5f);
    	} else if (target instanceof EntitySpider) {
    	    //target.playSound(TSMMain.MODID+":hammer.hit", 1f, 0.75f+new Random().nextFloat()*0.5f);
    	   // target.playSound(TSMMain.MODID+":hammer.hitbone", 1f, 0.75f+new Random().nextFloat()*0.5f);
    	} else {
    	    //target.playSound(TSMMain.MODID+":hammer.hit", 1f, 0.75f+new Random().nextFloat()*0.5f);
    	    //target.playSound(TSMMain.MODID+":hammer.hitbone", 0.5f, 0.75f+new Random().nextFloat()*0.5f);
    	}
        stack.damageItem(8+new Random().nextInt(16), attacker);
        return true;
    }
    
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
    	return -1;
    }

    @Override
    public float getDigSpeed(ItemStack stack, net.minecraft.block.state.IBlockState state)
    {
    	if (state.getBlock().getMaterial().equals(Material.glass)) {
    		return 15f;
    	}
    	if (state.getBlock().equals(Blocks.obsidian) || state.getBlock().equals(Blocks.enchanting_table) || state.getBlock().equals(Blocks.ender_chest)) {
    		return 0f;
    	}
        return getStrVsBlock(stack, state.getBlock())/8;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        return true;
    }
}
