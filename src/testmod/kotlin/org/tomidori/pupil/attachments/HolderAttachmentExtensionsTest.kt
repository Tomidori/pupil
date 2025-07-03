package org.tomidori.pupil.attachments

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.entity.decoration.ArmorStandEntity
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.tomidori.pupil.holders.elementHolder

class HolderAttachmentExtensionsTest : ModInitializer {
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            dispatcher.register(
                CommandManager
                    .literal("holder_attachment_extensions_test")
                    .executes { context ->
                        executeTest(context.source)
                    }
            )
        }
    }

    private fun executeTest(source: ServerCommandSource): Int {
        ArmorStandEntity(
            source.world,
            source.position.x,
            source.position.y,
            source.position.z
        ).apply {
            entityAttachment(elementHolder(), this) {
                onEntityLoad {
                    LOGGER.info("Entity load")
                }

                onEntityUnload {
                    LOGGER.info("Entity unload")
                }
            }

            world.spawnEntity(this)
        }

        return 1
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(HolderAttachmentExtensionsTest::class.java)
    }
}