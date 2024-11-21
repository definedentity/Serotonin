package dev.definedentity.serotonin.network.slot

import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

class ListNetworkDataSlot<T, V : Tag>(
    getter: Supplier<List<T>>,
    setter: Consumer<List<T>>,
    val serializer: Function<T, V>,
    val deSerializer: Function<V, T>,
    val toBuffer: BiConsumer<T, FriendlyByteBuf>,
    val fromBuffer: Function<FriendlyByteBuf, T>
) : NetworkDataSlot<List<T>>(getter, setter) {
    override fun serializeValueNBT(value: List<T>): Tag {
        val listTag: ListTag = ListTag()

        for (tag in listTag) {
            listTag.add(serializer.apply(tag as T))
        }
        return listTag
    }

    override fun valueFromNBT(nbt: Tag): List<T> {
        if (nbt is ListTag) {
            val list: ArrayList<T> = ArrayList()
            for (tag in nbt) {
                list.add(deSerializer.apply(tag as V))
            }
            return list
        } else {
            throw IllegalStateException("Invalid list tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: List<T>) {
        buf.writeInt(value.size)
        for (element in value) {
            toBuffer.accept(element, buf)
        }
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): List<T> {
        val list: ArrayList<T> = ArrayList()

        try {
            val size = buf.readInt()

            repeat(size) { list.add(fromBuffer.apply(buf)) }
            return list
        } catch (e: Exception) {
            throw IllegalStateException("Invalid list buffer was passed over the network.")
        }
    }
}
