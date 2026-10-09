package ru.rorcik.core.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import ru.rorcik.core.RorcikCore;

@Mod.EventBusSubscriber(modid = RorcikCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEventHandler {
    private static final ResourceLocation PROPERTY_CONTAINER_CAP =
            ResourceLocation.fromNamespaceAndPath(RorcikCore.MODID, "property_container");

    private static final TagKey<Item> HAS_PROPERTIES_TAG = TagKey.create(
            ForgeRegistries.ITEMS.getRegistryKey(),
            ResourceLocation.fromNamespaceAndPath(RorcikCore.MODID, "has_properties")
    );

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.isEmpty()) return;

        if (!stack.is(HAS_PROPERTIES_TAG)) {
            return;
        }

        event.addCapability(PROPERTY_CONTAINER_CAP, new DynamicCapabilityProvider());
    }
}