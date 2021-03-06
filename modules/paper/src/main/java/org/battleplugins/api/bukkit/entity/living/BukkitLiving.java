package org.battleplugins.api.bukkit.entity.living;

import org.battleplugins.api.bukkit.entity.BukkitEntity;
import org.battleplugins.api.entity.Entity;
import org.battleplugins.api.entity.living.Living;
import org.bukkit.entity.LivingEntity;

public class BukkitLiving<T extends LivingEntity> extends BukkitEntity<T> implements Living {

    public BukkitLiving(T entity) {
        super(entity);
    }

    @Override
    public void damage(double amount) {
        handle.damage(amount);
    }

    @Override
    public void damage(double amount, Entity source) {
        handle.damage(amount, ((BukkitEntity) source).getHandle());
    }

    @Override
    public double getHealth() {
        return handle.getHealth();
    }

    @Override
    public void setHealth(double health) {
        handle.setHealth(health);
    }

    @Override
    public T getHandle() {
        return handle;
    }
}
