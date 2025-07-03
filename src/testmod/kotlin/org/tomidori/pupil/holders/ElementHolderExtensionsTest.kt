package org.tomidori.pupil.holders

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ElementHolderExtensionsTest : ModInitializer {
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            dispatcher.register(
                CommandManager
                    .literal("element_holder_extensions_test")
                    .executes {
                        elementHolder {
                            onStartWatching {
                                LOGGER.info("Start watching: {}, {}", networkHandler, player)
                            }

                            onStopWatching {
                                LOGGER.info("Stop watching: {}, {}", networkHandler, player)
                            }

                            onTick {
                                LOGGER.info("Tick: {}, {}", ticks, isFirstTick)
                            }

                            onAttachmentSet {
                                LOGGER.info("Attachment Set: {}, {}", attachment, oldAttachment)
                            }

                            onAttachmentRemoved {
                                LOGGER.info("Attachment Removed: {}", oldAttachment)
                            }

                            chunkAttachmentTicking(it.source.world, it.source.position)
                        }

                        1
                    }
            )
        }
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ElementHolderExtensionsTest::class.java)
    }
}