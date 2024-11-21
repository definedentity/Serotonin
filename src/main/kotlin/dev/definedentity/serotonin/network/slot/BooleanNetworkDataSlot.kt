package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.ByteTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class BooleanNetworkDataSlot(getter: Supplier<Boolean>, setter: Consumer<Boolean>) :
    NetworkDataSlot<Boolean>(getter, setter) {
    override fun serializeValueNBT(value: Boolean): Tag {
        return ByteTag.valueOf(value)
    }

    override fun valueFromNBT(nbt: Tag): Boolean {
        if (nbt is ByteTag) {
            return nbt.asByte.toInt() == 1
        } else {
            throw IllegalStateException("Invalid boolean tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: Boolean) {
        buf.writeBoolean(value)
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): Boolean {
        try {
            return buf.readBoolean()
        } catch (e: Exception) {
            throw IllegalStateException("Invalid boolean buffer was passed over the network.")
        }
    }
}
