package com.duarte.morewood.client;

import com.duarte.morewood.util.ObjectType;
import com.duarte.morewood.util.Variables;
import com.duarte.morewood.util.WoodType;
import com.duarte.morewood.util.Woodwood;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;



public class MyBarrelTileEntity extends BarrelTileEntity implements Woodwood {
    private final TileEntityType<BarrelTileEntity> type;
    private final WoodType woodType;
    private final TranslationTextComponent defaultName;
    private NonNullList<ItemStack> barrelContents = NonNullList.withSize(27, ItemStack.EMPTY);
    private int numPlayersUsing;


    public MyBarrelTileEntity(final WoodType woodType, TileEntityType<BarrelTileEntity> type) {
        this.woodType = woodType;
        this.type = type;
        this.defaultName = new TranslationTextComponent(
                StringUtils.joinWith(".", "container", Variables.MOD_ID,
                        this.getWoodType().toString() + "_" + ObjectType.BARREL.toString()));

    }

    @Override
    public WoodType getWoodType() {
        return this.woodType;
    }


    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.barrelContents);
        }

        return compound;
    }

    public void read(BlockState state, CompoundNBT nbt)
    {
        super.read(state, nbt);
        this.barrelContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(nbt))
        {
            ItemStackHelper.loadAllItems(nbt, this.barrelContents);
        }
    }

    public int getSizeInventory()
    {
        return 27;
    }

    protected NonNullList<ItemStack> getItems()
    {
        return this.barrelContents;
    }

    protected void setItems(NonNullList<ItemStack> itemsIn)
    {
        this.barrelContents = itemsIn;
    }

    protected ITextComponent getDefaultName()
    {
        return this.defaultName;
    }


    protected Container createMenu(int id, PlayerInventory player)
    {
        return ChestContainer.createGeneric9X3(id, player, this);
    }

    public void openInventory(PlayerEntity player)
    {
        if (!player.isSpectator())
        {
            if (this.numPlayersUsing < 0)
            {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            BlockState blockstate = this.getBlockState();
            boolean flag = blockstate.get(BarrelBlock.PROPERTY_OPEN);

            if (!flag)
            {
                this.playSound(blockstate, SoundEvents.BLOCK_BARREL_OPEN);
                this.setOpenProperty(blockstate, true);
            }

            this.scheduleTick();
        }
    }

    private void scheduleTick() {
        this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
    }

    public void barrelTick() {
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        this.numPlayersUsing = ChestTileEntity.calculatePlayersUsing(this.world, this, x, y, z);

        if (this.numPlayersUsing > 0) {
            this.scheduleTick();
        }
        else {
            final BlockState blockstate = this.getBlockState();

            if (!(blockstate.getBlock() instanceof com.duarte.morewood.blocks.BarrelBlock)) {
                this.remove();
                return;
            }

            final boolean open = blockstate.get(BarrelBlock.PROPERTY_OPEN);

            if (open) {
                this.playSound(blockstate, SoundEvents.BLOCK_BARREL_CLOSE);
                this.setOpenProperty(blockstate, false);
            }
        }
    }

    public void closeInventory(PlayerEntity player)
    {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void setOpenProperty(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), state.with(BarrelBlock.PROPERTY_OPEN, Boolean.valueOf(open)), 3);
    }

    private void playSound(BlockState state, SoundEvent sound) {
        Vector3i vector3i = state.get(BarrelBlock.PROPERTY_FACING).getDirectionVec();
        double d0 = (double)this.pos.getX() + 0.5D + (double)vector3i.getX() / 2.0D;
        double d1 = (double)this.pos.getY() + 0.5D + (double)vector3i.getY() / 2.0D;
        double d2 = (double)this.pos.getZ() + 0.5D + (double)vector3i.getZ() / 2.0D;
        this.world.playSound((PlayerEntity)null, d0, d1, d2, sound, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    }
}