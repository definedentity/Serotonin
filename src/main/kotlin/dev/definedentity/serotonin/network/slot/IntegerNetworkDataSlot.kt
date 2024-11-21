package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.IntTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class IntegerNetworkDataSlot(getter: Supplier<Int>, setter: Consumer<Int>) :
    NetworkDataSlot<Int>(getter, setter) {
    override fun serializeValueNBT(value: Int): Tag {
        return IntTag.valueOf(value)
    }

    override fun valueFromNBT(nbt: Tag): Int {
        if (nbt is IntTag) {
            return nbt.asInt
        } else {
            throw IllegalStateException("Invalid int tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: Int) {
        buf.writeInt(value)
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): Int {
        try {
            return buf.readInt()
        } catch (e: Exception) {
            throw IllegalStateException("Invalid int buffer was passed over the network.")
        }
    }
}
