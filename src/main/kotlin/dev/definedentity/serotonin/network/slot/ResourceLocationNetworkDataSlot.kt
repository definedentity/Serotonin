package dev.definedentity.serotonin.network.slot

import java.util.function.Consumer
import java.util.function.Supplier
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

class ResourceLocationNetworkDataSlot(
    getter: Supplier<ResourceLocation>,
    setter: Consumer<ResourceLocation>
) : NetworkDataSlot<ResourceLocation>(getter, setter) {
    override fun serializeValueNBT(value: ResourceLocation): Tag {
        return StringTag.valueOf(value.toString())
    }

    override fun valueFromNBT(nbt: Tag): ResourceLocation {
        if (nbt is StringTag) {
            return ResourceLocation(nbt.asString)
        } else {
            throw IllegalStateException("Invalid string tag was passed over the network.")
        }
    }

    override fun toBuffer(buf: FriendlyByteBuf, value: ResourceLocation) {
        buf.writeResourceLocation(value)
    }

    override fun valueFromBuffer(buf: FriendlyByteBuf): ResourceLocation {
        try {
            return buf.readResourceLocation()
        } catch (e: Exception) {
            throw IllegalStateException(
                "Invalid resourceLocation buffer was passed over the network."
            )
        }
    }
}
