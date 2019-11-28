package org.battleplugins.nukkit.inventory;

import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;

import org.battleplugins.inventory.item.ItemStack;
import org.battleplugins.nukkit.inventory.item.NukkitItemStack;
import org.battleplugins.nukkit.util.NukkitInventoryUtil;
import org.battleplugins.util.MCWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NukkitInventory<T extends Inventory> extends MCWrapper<T> implements org.battleplugins.inventory.Inventory {

	public NukkitInventory(T inventory) {
		super(inventory);
	}

	@Override
	public void addItem(ItemStack... itemStacks) {
		for (ItemStack item : itemStacks)
			addItem(item);
	}

	public void addItem(ItemStack itemStack) {
		if (itemStack == null || itemStack.getType().equals("0")) {
			return;
		}

		NukkitInventoryUtil.addItemToInventory(handle, ((NukkitItemStack)itemStack).getHandle(),itemStack.getQuantity());
	}

	@Override
	public void removeItem(ItemStack itemStack) {
		handle.removeItem(((NukkitItemStack) itemStack).getHandle());
	}

	@Override
	public void setItem(int slot, ItemStack item) { handle.setItem(slot, ((NukkitItemStack) item).getHandle());
	}

	@Override
	public Optional<NukkitItemStack> getItem(int slot) {
		return Optional.ofNullable(handle.getItem(slot)).map(NukkitItemStack::new);
	}

	@Override
	public int getItemAmount(ItemStack itemStack) {
		return NukkitInventoryUtil.getItemAmountFromInventory(handle, ((NukkitItemStack) itemStack).getHandle());
	}

	@Override
	public boolean canFit(ItemStack itemStack) {
		return freeSpaceAfter(itemStack) >= 0;
	}

	@Override
	public int freeSpaceAfter(ItemStack itemStack) {
		return NukkitInventoryUtil.amountFreeSpace(handle,
				((NukkitItemStack) itemStack).getHandle(), itemStack.getQuantity());
	}

	@Override
	public NukkitItemStack[] getContents() {
		List<Item> items = new ArrayList<>(handle.getContents().values());
		NukkitItemStack[] mcItems = new NukkitItemStack[items.size()];
		for (int i = 0; i < items.size(); i++){
			mcItems[i] = new NukkitItemStack(items.get(i));
		}

		return mcItems;
	}

	@Override
	public void clear() {
		handle.clearAll();
	}

	@Override
	public T getHandle() {
		return handle;
	}
}