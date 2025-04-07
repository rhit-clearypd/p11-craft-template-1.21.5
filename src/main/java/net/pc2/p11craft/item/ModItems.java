package net.pc2.p11craft.item;

// p11: helper class to register items, so minecraft knows we made new items :)

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.pc2.p11craft.P11Craft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new Item.Settings()));


    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(P11Craft.MOD_ID, name), item);
    }

    public static void registerModItems() {
        P11Craft.LOGGER.info("Registering Mod Items for " + P11Craft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(RUBY);
        });
    }
}
