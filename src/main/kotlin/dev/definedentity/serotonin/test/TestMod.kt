package dev.definedentity.serotonin.test

import dev.definedentity.serotonin.Serotonin
import dev.definedentity.serotonin.network.SerotoninNetwork
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

object TestMod {
    val NETWORK = SerotoninNetwork(ResourceLocation(Serotonin.MOD_ID, "network"))

    fun init() {
        Registry.register(Registry.ITEM, ResourceLocation(Serotonin.MOD_ID, "test_item"), TestItem())
    }
}