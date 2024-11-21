package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class StringNetworkDataSlot(getter: Supplier<String>, consumer: Consumer<String>) :
    NetworkDataSlot<String>(getter, consumer) {
    override fun serializeValueNBT(value: String): Tag {
        return StringTag.valueOf(value)
    }

    override fun valueFromNBT(nbt: Tag): String {
        if (nbt is StringTag) {
            return nbt.asString
        } else {
            throw IllegalStateException("Invalid string tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: String) {
        buf.writeUtf(value)
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): String {
        try {
            return buf.readUtf()
        } catch (e: Exception) {
            throw IllegalStateException("Invalid string buffer was passed over the network.")
        }
    }
}
