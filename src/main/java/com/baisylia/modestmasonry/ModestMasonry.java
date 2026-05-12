package com.baisylia.modestmasonry;

import com.mojang.logging.LogUtils;
import com.baisylia.modestmasonry.block.ModBlocks;
import com.baisylia.modestmasonry.integration.everycompat.EveryCompatIntegration;
import com.baisylia.modestmasonry.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModestMasonry.MOD_ID)
public class ModestMasonry
{
    public static final String MOD_ID = "modestmasonry";
    private static final Logger LOGGER = LogUtils.getLogger();


    public ModestMasonry()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::creativeTabSetup);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);

        if (ModList.get().isLoaded("everycomp"))
            EveryCompatIntegration.register();
    }

    private void creativeTabSetup(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(ModItems.PLANK);

        }
        else if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            ModBlocks.NATURAL.forEach((block)->event.accept(block.get()));
        } else if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            ModBlocks.BUILDING_BLOCKS.forEach((block)->event.accept(block.get()));
        }  else if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            ModBlocks.FUNCTIONAL.forEach((block)->event.accept(block.get()));
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
}
