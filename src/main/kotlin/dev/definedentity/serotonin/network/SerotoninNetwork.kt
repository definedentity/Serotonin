package dev.definedentity.serotonin.network

import me.pepperbell.simplenetworking.C2SPacket
import me.pepperbell.simplenetworking.S2CPacket
import me.pepperbell.simplenetworking.SimpleChannel
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.chunk.LevelChunk

object SerotoninNetwork {
    private var CHANNEL: SimpleChannel? = null
    var packetId: Int = 0

    /**
     * **MUST** be called before any network related code.
     *
     * @param name **Must** be unique
     */
    fun init(name: ResourceLocation) {
        CHANNEL = SimpleChannel(name)
    }

    fun <P : C2SPacket> sendToServer(packet: P) {
        CHANNEL!!.sendToServer(packet)
    }

    fun <P : S2CPacket> sendToPlayer(player: ServerPlayer, packet: P) {
        CHANNEL!!.sendToClient(packet, player)
    }

    fun sendToTracking(chunk: LevelChunk, packet: S2CPacket) {
        CHANNEL!!.sendToClientsTracking(packet, chunk.level as ServerLevel, chunk.pos)
    }

    fun sendToDimension(level: ServerLevel, packet: S2CPacket) {
        CHANNEL!!.sendToClientsInWorld(packet, level)
    }

    fun id(): Int {
        return packetId++
    }
}
