package dev.definedentity.serotonin.utils

import net.fabricmc.loader.api.FabricLoader

object ModUtils {
    fun isModLoaded(modId: String): Boolean {
        return FabricLoader.getInstance().isModLoaded(modId)
    }
}