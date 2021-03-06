package org.battleplugins.api.registry.inventory;

import org.battleplugins.api.Platform;
import org.battleplugins.api.inventory.item.ItemType;
import org.battleplugins.api.inventory.item.component.ItemComponent;
import org.battleplugins.api.registry.ComponentRegistry;
import org.battleplugins.api.registry.IdentifiableRegistry;
import org.battleplugins.api.util.Identifier;

/**
 * A registry containing all the items based on various
 * implementations depending on the platform.
 *
 * Certain platforms base their implementations of items
 * on different values (e.g. Bukkit is Material/identifier
 * based whilst Nukkit is ID based). This class allows for
 * items to be mapped from their platform implementations as
 * well as from {@link Identifier}'s.
 */
public abstract class ItemRegistry extends ComponentRegistry<ItemComponent<?>> implements IdentifiableRegistry<ItemType> {

    /**
     * Gets the current item registry
     *
     * @return the current item registry
     */
    public static ItemRegistry get() {
        return Platform.getRegistry().getItemRegistry();
    }
}
