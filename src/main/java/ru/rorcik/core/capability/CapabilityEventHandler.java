package ru.rorcik.core.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.rorcik.core.RorcikCore;

@Mod.EventBusSubscriber(modid = RorcikCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEventHandler {
    private static final ResourceLocation PROPERTY_CONTAINER_CAP =
            ResourceLocation.fromNamespaceAndPath(RorcikCore.MODID, "property_container");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.isEmpty()) {
            return;
        }
        event.addCapability(PROPERTY_CONTAINER_CAP, new DynamicCapabilityProvider());
    }
}