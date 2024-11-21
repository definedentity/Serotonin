package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.IntTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class EnumNetworkDataSlot<T : Enum<T>>(
    val enumClass: Class<T>,
    getter: Supplier<T>,
    setter: Consumer<T>
) : NetworkDataSlot<T>(getter, setter) {
    override fun serializeValueNBT(value: T): Tag {
        return IntTag.valueOf(value.ordinal)
    }

    override fun valueFromNBT(nbt: Tag): T {
        if (nbt is IntTag) {
            return enumClass.getEnumConstants()[nbt.asInt]
        } else {
            throw IllegalStateException("Invalid enum/int tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: T) {
        buf.writeInt(value.ordinal)
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): T {
        try {
            return enumClass.getEnumConstants()[buf.readInt()]
        } catch (e: Exception) {
            throw IllegalStateException("Invalid enum/int buffer was passed over the network.")
        }
    }
}
