package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

abstract class NetworkDataSlot<T>(val getter: Supplier<T>, val setter: Consumer<T>) {
    private var cachedHash: Int? = null

    fun serializeNBT(fullUpdate: Boolean): Tag? {
        val value: T = getter.get()
        val hash = hashCode(value)

        if (!fullUpdate && cachedHash == hash) {
            return null
        }
        cachedHash = hash
        return serializeValueNBT(value)
    }

    fun writeBuffer(buf: FriendlyByteBuf) {
        val value: T = getter.get()
        hashCode(value)
        toBuffer(buf, value)
    }

    open fun fromNBT(nbt: Tag) {
        setter.accept(valueFromNBT(nbt))
    }

    open fun fromBuffer(buf: FriendlyByteBuf) {
        setter.accept(valueFromBuffer(buf))
    }

    abstract fun serializeValueNBT(value: T): Tag

    protected abstract fun valueFromNBT(nbt: Tag): T

    abstract fun toBuffer(buf: FriendlyByteBuf, value: T)

    protected abstract fun valueFromBuffer(buf: FriendlyByteBuf): T

    fun needsUpdate(): Boolean {
        val value: T = getter.get()
        val hash = hashCode(value)

        return cachedHash != hash
    }

    protected fun hashCode(value: T): Int {
        return value.hashCode()
    }

    /** This will be called after the server is updated with the new data */
    fun updateServerCallback() {}
}
