package ru.rorcik.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rorcik.core.capability.PropertyContainerCapability;
import ru.rorcik.core.event.RegisterIdentifiableEvent;
import ru.rorcik.core.event.RegisterPropertiesEvent;

@Mod(RorcikCore.MODID)
public class RorcikCore {
    public static final String MODID = "rorcik_core";
    public static final Logger LOGGER = LogManager.getLogger();

    public RorcikCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCapabilities);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("RorcikCore common setup");
        event.enqueueWork(() -> {
            MinecraftForge.EVENT_BUS.post(new RegisterPropertiesEvent());
            MinecraftForge.EVENT_BUS.post(new RegisterIdentifiableEvent());
        });
    }

    private void registerCapabilities(final RegisterCapabilitiesEvent event) {
        PropertyContainerCapability.register();
        event.register(ru.rorcik.core.property.PropertyContainer.class);
    }

    @SubscribeEvent
    public void onRegisterProperties(RegisterPropertiesEvent event) {
        LOGGER.info("Properties registration phase started");
    }

    @SubscribeEvent
    public void onRegisterIdentifiable(RegisterIdentifiableEvent event) {
        LOGGER.info("Identifiable objects registration phase started");
    }
}