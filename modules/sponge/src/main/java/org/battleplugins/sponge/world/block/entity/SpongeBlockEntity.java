package org.battleplugins.sponge.world.block.entity;

import org.battleplugins.sponge.world.block.SpongeBlock;
import org.battleplugins.util.MCWrapper;
import org.battleplugins.world.block.Block;
import org.battleplugins.world.block.entity.BlockEntity;
import org.spongepowered.api.block.tileentity.TileEntity;

public class SpongeBlockEntity<T extends TileEntity> extends MCWrapper<T> implements BlockEntity {

    public SpongeBlockEntity(T handle) {
        super(handle);
    }

    @Override
    public Block getBlock() {
        return new SpongeBlock(handle.getBlock().snapshotFor(handle.getLocation()));
    }

    @Override
    public void update() {
        // TODO: Add API here
    }

    @Override
    public void update(boolean update) {
        // TODO: Add API here
    }

    @Override
    public T getHandle() {
        return handle;
    }
}
