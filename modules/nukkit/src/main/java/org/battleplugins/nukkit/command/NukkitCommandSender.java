package org.battleplugins.nukkit.command;

import cn.nukkit.command.CommandSender;

import org.battleplugins.util.MCWrapper;

public abstract class NukkitCommandSender<T extends CommandSender> extends MCWrapper<T> implements org.battleplugins.command.CommandSender {

	public NukkitCommandSender(T sender){
		super(sender);
	}

	@Override
	public boolean hasPermission(String node) {
		return handle.hasPermission(node);
	}

	@Override
	public void sendMessage(String message) {
		handle.sendMessage(message);
	}

	@Override
	public String getName() {
		return "Console";
	}

	@Override
	public T getHandle() {
		return handle;
	}
}
