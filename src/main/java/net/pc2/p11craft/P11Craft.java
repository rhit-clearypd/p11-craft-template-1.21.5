package net.pc2.p11craft;

import net.fabricmc.api.ModInitializer;
import net.pc2.p11craft.block.ModBlocks;
import net.pc2.p11craft.item.ModItemGroups;
import net.pc2.p11craft.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// p11: This is the EXAMPLE MOD CLASS

// Class name: P11Craft
public class P11Craft implements ModInitializer {
	public static final String MOD_ID = "p11-craft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}

// p11: for crafting recipes, go to external libraries and expand, scroll until you see wall of all the same, go up 2-3 to
// 		minecraft merged, open minecaft merged, data, minecraft, recipe