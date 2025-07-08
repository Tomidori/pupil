package org.tomidori.pupil.holders

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.attachment.*
import eu.pb4.polymer.virtualentity.api.elements.*
import eu.pb4.polymer.virtualentity.impl.attachment.FallingBlockEntityAttachment
import eu.pb4.polymer.virtualentity.impl.attachment.PistonAttachment
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.FallingBlockEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.chunk.WorldChunk
import org.tomidori.pupil.annotations.ExperimentalPolymerApi
import org.tomidori.pupil.events.Disposable

public inline fun elementHolder(block: ElementHolder.() -> Unit = {}): ElementHolder = ElementHolder().apply(block)

public inline fun <reified T : VirtualElement> ElementHolder.filterElements(): List<T> = elements.filterIsInstance<T>()

public inline fun <reified T : VirtualElement> ElementHolder.forEachElement(block: T.() -> Unit): Unit =
    filterElements<T>().forEach(block)

public val ElementHolder.abstractElements: List<AbstractElement> get() = filterElements()

public val ElementHolder.blockDisplayElements: List<BlockDisplayElement> get() = filterElements()

public val ElementHolder.displayElements: List<DisplayElement> get() = filterElements()

public val ElementHolder.entityElements: List<EntityElement<*>> get() = filterElements()

public val ElementHolder.genericEntityElements: List<GenericEntityElement> get() = filterElements()

public val ElementHolder.interactionElements: List<InteractionElement> get() = filterElements()

public val ElementHolder.itemDisplayElements: List<ItemDisplayElement> get() = filterElements()

public val ElementHolder.markerElements: List<MarkerElement> get() = filterElements()

public val ElementHolder.mobAnchorElements: List<MobAnchorElement> get() = filterElements()

public val ElementHolder.simpleEntityElements: List<SimpleEntityElement> get() = filterElements()

public val ElementHolder.textDisplayElements: List<TextDisplayElement> get() = filterElements()

public inline fun ElementHolder.abstractElements(block: AbstractElement.() -> Unit): Unit = forEachElement(block)

public inline fun ElementHolder.blockDisplayElements(block: BlockDisplayElement.() -> Unit): Unit =
    forEachElement(block)

public inline fun ElementHolder.displayElements(block: DisplayElement.() -> Unit): Unit = forEachElement(block)

public inline fun ElementHolder.entityElements(block: EntityElement<*>.() -> Unit): Unit = forEachElement(block)

public inline fun ElementHolder.genericEntityElements(block: GenericEntityElement.() -> Unit): Unit =
    forEachElement(block)

public inline fun ElementHolder.interactionElements(block: InteractionElement.() -> Unit): Unit = forEachElement(block)

public inline fun ElementHolder.itemDisplayElements(block: ItemDisplayElement.() -> Unit): Unit = forEachElement(block)

public inline fun ElementHolder.markerElements(block: MarkerElement.() -> Unit): Unit = forEachElement(block)

public inline fun ElementHolder.mobAnchorElements(block: MobAnchorElement.() -> Unit): Unit = forEachElement(block)

public inline fun ElementHolder.simpleEntityElements(block: SimpleEntityElement.() -> Unit): Unit =
    forEachElement(block)

public inline fun ElementHolder.textDisplayElements(block: TextDisplayElement.() -> Unit): Unit = forEachElement(block)

public inline fun <T : Entity> ElementHolder.entityElement(
    entity: T,
    world: ServerWorld,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.tomidori.pupil.elements.entityElement(entity, world, block))

public inline fun <T : Entity> ElementHolder.entityElement(
    entity: T,
    world: ServerWorld,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.tomidori.pupil.elements.entityElement(entity, world, handler, block))

public inline fun <T : Entity> ElementHolder.entityElement(
    type: EntityType<T>,
    world: ServerWorld,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.tomidori.pupil.elements.entityElement(type, world, block))

public inline fun <T : Entity> ElementHolder.entityElement(
    type: EntityType<T>,
    world: ServerWorld,
    handler: VirtualElement.InteractionHandler,
    block: EntityElement<T>.() -> Unit = {}
): EntityElement<T> = addElement(org.tomidori.pupil.elements.entityElement(type, world, handler, block))

public inline fun ElementHolder.simpleEntityElement(
    type: EntityType<*>,
    block: SimpleEntityElement.() -> Unit = {}
): SimpleEntityElement = addElement(org.tomidori.pupil.elements.simpleEntityElement(type, block))

public inline fun ElementHolder.interactionElement(block: InteractionElement.() -> Unit = {}): InteractionElement =
    addElement(org.tomidori.pupil.elements.interactionElement(block))

public inline fun ElementHolder.interactionElement(
    handler: VirtualElement.InteractionHandler,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = addElement(org.tomidori.pupil.elements.interactionElement(handler, block))

public inline fun ElementHolder.interactionElementRedirect(
    target: Entity,
    block: InteractionElement.() -> Unit = {}
): InteractionElement = addElement(org.tomidori.pupil.elements.interactionElementRedirect(target, block))

public inline fun ElementHolder.textDisplayElement(block: TextDisplayElement.() -> Unit = {}): TextDisplayElement =
    addElement(org.tomidori.pupil.elements.textDisplayElement(block))

public inline fun ElementHolder.textDisplayElement(
    text: Text,
    block: TextDisplayElement.() -> Unit = {}
): TextDisplayElement = addElement(org.tomidori.pupil.elements.textDisplayElement(text, block))

public inline fun ElementHolder.blockDisplayElement(block: BlockDisplayElement.() -> Unit = {}): BlockDisplayElement =
    addElement(org.tomidori.pupil.elements.blockDisplayElement(block))

public inline fun ElementHolder.blockDisplayElement(
    state: BlockState,
    block: BlockDisplayElement.() -> Unit = {}
): BlockDisplayElement = addElement(org.tomidori.pupil.elements.blockDisplayElement(state, block))

public inline fun ElementHolder.itemDisplayElement(block: ItemDisplayElement.() -> Unit = {}): ItemDisplayElement =
    addElement(org.tomidori.pupil.elements.itemDisplayElement(block))

public inline fun ElementHolder.itemDisplayElement(
    stack: ItemStack,
    block: ItemDisplayElement.() -> Unit = {}
): ItemDisplayElement = addElement(org.tomidori.pupil.elements.itemDisplayElement(stack, block))

public inline fun ElementHolder.itemDisplayElement(
    item: Item,
    block: ItemDisplayElement.() -> Unit = {}
): ItemDisplayElement = addElement(org.tomidori.pupil.elements.itemDisplayElement(item, block))

public inline fun ElementHolder.mobAnchorElement(block: MobAnchorElement.() -> Unit = {}): MobAnchorElement =
    addElement(org.tomidori.pupil.elements.mobAnchorElement(block))

public inline fun ElementHolder.markerElement(block: MarkerElement.() -> Unit = {}): MarkerElement =
    addElement(org.tomidori.pupil.elements.markerElement(block))

public inline fun ElementHolder.manualAttachment(
    world: ServerWorld,
    noinline posSupplier: () -> Vec3d,
    block: ManualAttachment.() -> Unit = {}
): ManualAttachment = org.tomidori.pupil.attachments.manualAttachment(this, world, posSupplier, block)

public inline fun ElementHolder.entityAttachment(
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = org.tomidori.pupil.attachments.entityAttachment(this, entity, block)

public inline fun ElementHolder.entityAttachmentTicking(
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = org.tomidori.pupil.attachments.entityAttachmentTicking(this, entity, block)

public inline fun ElementHolder.chunkAttachment(
    world: ServerWorld,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = org.tomidori.pupil.attachments.chunkAttachment(this, world, pos, block)

public inline fun ElementHolder.chunkAttachmentTicking(
    world: ServerWorld,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = org.tomidori.pupil.attachments.chunkAttachmentTicking(this, world, pos, block)

public inline fun ElementHolder.chunkAttachment(
    world: ServerWorld,
    pos: Vec3d,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = org.tomidori.pupil.attachments.chunkAttachment(this, world, pos, block)

public inline fun ElementHolder.chunkAttachmentTicking(
    world: ServerWorld,
    pos: Vec3d,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = org.tomidori.pupil.attachments.chunkAttachmentTicking(this, world, pos, block)

public inline fun ElementHolder.identifiedUniqueEntityAttachment(
    id: Identifier,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment =
    org.tomidori.pupil.attachments.identifiedUniqueEntityAttachment(id, this, entity, block)

public inline fun ElementHolder.identifiedUniqueEntityAttachmentTicking(
    id: Identifier,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment =
    org.tomidori.pupil.attachments.identifiedUniqueEntityAttachmentTicking(id, this, entity, block)

public inline fun ElementHolder.fallingBlockEntityAttachment(
    entity: FallingBlockEntity,
    block: FallingBlockEntityAttachment.() -> Unit = {}
): FallingBlockEntityAttachment = org.tomidori.pupil.attachments.fallingBlockEntityAttachment(this, entity, block)

public inline fun ElementHolder.pistonAttachment(
    chunk: WorldChunk,
    state: BlockState,
    pos: BlockPos,
    direction: Direction,
    block: PistonAttachment.() -> Unit = {}
): PistonAttachment = org.tomidori.pupil.attachments.pistonAttachment(this, chunk, state, pos, direction, block)

@ExperimentalPolymerApi
public inline fun ElementHolder.blockBoundAttachment(
    world: ServerWorld,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = org.tomidori.pupil.attachments.blockBoundAttachment(this, world, pos, state, block)

@ExperimentalPolymerApi
public inline fun ElementHolder.blockBoundAttachment(
    world: ServerWorld,
    chunk: WorldChunk,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = org.tomidori.pupil.attachments.blockBoundAttachment(this, world, chunk, pos, state, block)

@ExperimentalPolymerApi
public inline fun ElementHolder.blockBoundAttachmentMoving(
    world: ServerWorld,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? = org.tomidori.pupil.attachments.blockBoundAttachmentMoving(this, world, pos, state, block)

public fun ElementHolder.startRiding(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public class HolderStartWatchingScope @PublishedApi internal constructor(
    public val networkHandler: ServerPlayNetworkHandler
) {
    public val player: ServerPlayerEntity get() = networkHandler.player
}

public inline fun ElementHolder.onStartWatching(crossinline block: HolderStartWatchingScope.() -> Unit): Disposable =
    (this as ElementHolderHook).`pupil$addStartWatchingListener` { HolderStartWatchingScope(it).block() }

public class HolderStopWatchingScope @PublishedApi internal constructor(
    public val networkHandler: ServerPlayNetworkHandler
) {
    public val player: ServerPlayerEntity get() = networkHandler.player
}

public inline fun ElementHolder.onStopWatching(crossinline block: HolderStopWatchingScope.() -> Unit): Disposable =
    (this as ElementHolderHook).`pupil$addStopWatchingListener` { HolderStopWatchingScope(it).block() }

public class HolderTickScope @PublishedApi internal constructor(
    public val ticks: Int
) {
    public val isFirstTick: Boolean get() = ticks == 0
}

public inline fun ElementHolder.onTick(crossinline block: HolderTickScope.() -> Unit): Disposable {
    var ticks = 0
    return (this as ElementHolderHook).`pupil$addTickListener` { HolderTickScope(ticks++).block() }
}

public class HolderAttachmentSetScope @PublishedApi internal constructor(
    public val attachment: HolderAttachment,
    public val oldAttachment: HolderAttachment?
)

public inline fun ElementHolder.onAttachmentSet(crossinline block: HolderAttachmentSetScope.() -> Unit): Disposable =
    (this as ElementHolderHook).`pupil$addAttachmentSetListener` { attachment, oldAttachment ->
        HolderAttachmentSetScope(attachment, oldAttachment).block()
    }

public class HolderAttachmentRemovedScope @PublishedApi internal constructor(
    public val oldAttachment: HolderAttachment
)

public inline fun ElementHolder.onAttachmentRemoved(crossinline block: HolderAttachmentRemovedScope.() -> Unit): Disposable =
    (this as ElementHolderHook).`pupil$addAttachmentRemovedListener` { HolderAttachmentRemovedScope(it).block() }