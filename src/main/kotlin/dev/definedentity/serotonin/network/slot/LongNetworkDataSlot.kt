package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.LongTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class LongNetworkDataSlot(getter: Supplier<Long>, consumer: Consumer<Long>) :
    NetworkDataSlot<Long>(getter, consumer) {
    override fun serializeValueNBT(value: Long): Tag {
        return LongTag.valueOf(value)
    }

    override fun valueFromNBT(nbt: Tag): Long {
        if (nbt is LongTag) {
            return nbt.asLong
        } else {
            throw IllegalStateException("Invalid long tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: Long) {
        buf.writeLong(value)
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): Long {
        try {
            return buf.readLong()
        } catch (e: Exception) {
            throw IllegalStateException("Invalid long buffer was passed over the network.")
        }
    }
}
