package mc.alk.nukkit.plugin;

import cn.nukkit.plugin.PluginBase;

import mc.alk.mc.MCServer;
import mc.alk.mc.command.MCCommand;
import mc.alk.mc.command.MCCommandExecutor;
import mc.alk.mc.plugin.MCPlugin;
import mc.alk.nukkit.NukkitServer;
import mc.alk.nukkit.command.NukkitCommandExecutor;

public abstract class NukkitPlugin extends PluginBase implements MCPlugin {

    private MCServer server;

    @Override
    public MCServer getMCServer() {
        if (server == null) {
            server = new NukkitServer();
            MCServer.setInstance(server);
        }

        return server;
    }

    @Override
    public void registerMCCommand(MCCommand command, MCCommandExecutor executor) {
        NukkitCommandExecutor nukkitExecutor = new NukkitCommandExecutor(command.getLabel(), executor);
        nukkitExecutor.setDescription(command.getDescription());
        nukkitExecutor.setPermission(command.getPermission());
        nukkitExecutor.setAliases(command.getAliases().toArray(new String[command.getAliases().size()]));


        getServer().getCommandMap().register(command.getLabel(),nukkitExecutor);
    }
}