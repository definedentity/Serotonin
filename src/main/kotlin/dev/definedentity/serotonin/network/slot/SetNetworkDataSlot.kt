package dev.definedentity.serotonin.network.slot

import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class SetNetworkDataSlot<T, V : Tag>(
    getter: Supplier<Set<T>>,
    setter: Consumer<Set<T>>,
    val serializer: Function<T, V>,
    val deSerializer: Function<V, T>,
    val toBuffer: BiConsumer<T, FriendlyByteBuf>,
    val fromBuffer: Function<FriendlyByteBuf, T>
) : NetworkDataSlot<Set<T>>(getter, setter) {
    override fun serializeValueNBT(value: Set<T>): Tag {
        val listTag: ListTag = ListTag()

        for (tag in listTag) {
            listTag.add(serializer.apply(tag as T))
        }
        return listTag
    }

    override fun valueFromNBT(nbt: Tag): Set<T> {
        if (nbt is ListTag) {
            val set: HashSet<T> = HashSet()
            for (tag in nbt) {
                set.add(deSerializer.apply(tag as V))
            }
            return set
        } else {
            throw IllegalStateException("Invalid set tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: Set<T>) {
        buf.writeInt(value.size)
        for (element in value) {
            toBuffer.accept(element, buf)
        }
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): Set<T> {
        val set: HashSet<T> = HashSet()

        try {
            val size = buf.readInt()

            repeat(size) { set.add(fromBuffer.apply(buf)) }
            return set
        } catch (e: Exception) {
            throw IllegalStateException("Invalid list buffer was passed over the network.")
        }
    }
}
