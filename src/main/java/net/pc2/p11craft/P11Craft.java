package net.pc2.p11craft;

import net.fabricmc.api.ModInitializer;
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

		ModItems.registerModItems();
	}
}