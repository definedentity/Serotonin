package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.FloatTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class FloatNetworkDataSlot(getter: Supplier<Float>, consumer: Consumer<Float>) :
    NetworkDataSlot<Float>(getter, consumer) {
    override fun serializeValueNBT(value: Float): Tag {
        return FloatTag.valueOf(value)
    }

    override fun valueFromNBT(nbt: Tag): Float {
        if (nbt is FloatTag) {
            return nbt.asFloat
        } else {
            throw IllegalStateException("Invalid float tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: Float) {
        buf.writeFloat(value)
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): Float {
        try {
            return buf.readFloat()
        } catch (e: Exception) {
            throw IllegalStateException("Invalid float buffer was passed over the network.")
        }
    }
}
