package com.duarte.morewood.client.renderer.tileentity;

import com.duarte.morewood.client.Atlas;
import com.duarte.morewood.client.MyChestTileEntity;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;

import java.util.Map;

public class ChestTileEntityRenderer extends net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer {
    private Map<ChestType, RenderMaterial> materials;

    public ChestTileEntityRenderer(final TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
        this.materials = null;
    }

    @Override
    protected RenderMaterial getMaterial(final TileEntity tileEntity, final ChestType chestType) {
        if (this.materials == null) {
            this.materials = Atlas.getChestMaterials(((MyChestTileEntity) tileEntity).getWoodType());
        }
        assert this.materials != null;
        return this.materials.get(chestType);
    }
}
