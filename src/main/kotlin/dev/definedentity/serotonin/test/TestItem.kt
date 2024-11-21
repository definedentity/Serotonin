package dev.definedentity.serotonin.test

import dev.definedentity.serotonin.test.TestMod.NETWORK
import me.pepperbell.simplenetworking.C2SPacket
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class TestItem: Item(FabricItemSettings()) {
}