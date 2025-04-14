package net.pc2.p11craft.item;

// p11: helper class to register items, so minecraft knows we made new items :)

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.pc2.p11craft.P11Craft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pc2.p11craft.item.custom.ChiselItem;

public class ModItems {

    // helper class for creating the items below
    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(P11Craft.MOD_ID, name), item);
    }

    // p11: for items, added ".registryKey...RegistryKeys.ITEM" after Item.Settings() based on comments

    // Ruby item
    public static final Item RUBY = registerItem("ruby", new Item(new Item.Settings().registryKey(RegistryKey.of(
            RegistryKeys.ITEM, Identifier.of(P11Craft.MOD_ID,"ruby")))));

    // Raw Ruby item
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new Item.Settings().registryKey(RegistryKey.of(
            RegistryKeys.ITEM, Identifier.of(P11Craft.MOD_ID,"raw_ruby")))));

    // Cheese item (custom PNG)
    public static final Item CHEESE = registerItem("cheese", new Item(new Item.Settings().registryKey(RegistryKey.of(
            RegistryKeys.ITEM, Identifier.of(P11Craft.MOD_ID,"cheese"))).food(ModFoodComponents.CHEESE)));

    // Chisel item - create an instance of the custom ChiselItem class
    public static final Item CHISEL = registerItem("chisel", new ChiselItem(new Item.Settings().registryKey(RegistryKey.of(
            RegistryKeys.ITEM, Identifier.of(P11Craft.MOD_ID,"chisel"))).maxDamage(32)));

    public static final Item RESINWOOD = registerItem("resinwood", new Item(new Item.Settings().registryKey(RegistryKey.of(
            RegistryKeys.ITEM, Identifier.of(P11Craft.MOD_ID,"resinwood")))));

    public static void registerModItems() {
        P11Craft.LOGGER.info("Registering Mod Items for " + P11Craft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(RUBY);
            fabricItemGroupEntries.add(RAW_RUBY);
            fabricItemGroupEntries.add(CHEESE);
            fabricItemGroupEntries.add(CHISEL);
            fabricItemGroupEntries.add(RESINWOOD);

        });
    }
}
