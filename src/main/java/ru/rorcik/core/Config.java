package ru.rorcik.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = RorcikCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue DEBUG_MODE = BUILDER
            .comment("Enable debug logging for RorcikCore systems")
            .define("debugMode", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean debugMode;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        debugMode = DEBUG_MODE.get();
    }
}