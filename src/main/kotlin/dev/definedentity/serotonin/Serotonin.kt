package dev.definedentity.serotonin

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

class Serotonin : ModInitializer {
    companion object {
        val MOD_ID = "serotonin"
        val MOD_NAME = "Serotonin"
        val LOGGER = LoggerFactory.getLogger(MOD_NAME)

        private fun init() {
            LOGGER.info("Initializing $MOD_NAME")
        }
    }

    override fun onInitialize() {
        init()
    }
}
