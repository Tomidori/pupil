package org.tomidori.pupil.attachments

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.attachment.*
import eu.pb4.polymer.virtualentity.impl.attachment.FallingBlockEntityAttachment
import eu.pb4.polymer.virtualentity.impl.attachment.PistonAttachment
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.FallingBlockEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.chunk.WorldChunk
import org.tomidori.pupil.annotations.ExperimentalPolymerApi
import org.tomidori.pupil.events.Disposable

public inline fun manualAttachment(
    holder: ElementHolder,
    world: ServerWorld,
    noinline posSupplier: () -> Vec3d,
    block: ManualAttachment.() -> Unit = {}
): ManualAttachment = ManualAttachment(holder, world, posSupplier).apply(block)

public inline fun entityAttachment(
    holder: ElementHolder,
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = EntityAttachment.of(holder, entity).apply(block)

public inline fun entityAttachmentTicking(
    holder: ElementHolder,
    entity: Entity,
    block: EntityAttachment.() -> Unit = {}
): EntityAttachment = EntityAttachment.ofTicking(holder, entity).apply(block)

public inline fun chunkAttachment(
    holder: ElementHolder,
    world: ServerWorld,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.of(holder, world, pos).apply(block)

public inline fun chunkAttachmentTicking(
    holder: ElementHolder,
    world: ServerWorld,
    pos: BlockPos,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.ofTicking(holder, world, pos).apply(block)

public inline fun chunkAttachment(
    holder: ElementHolder,
    world: ServerWorld,
    pos: Vec3d,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.of(holder, world, pos).apply(block)

public inline fun chunkAttachmentTicking(
    holder: ElementHolder,
    world: ServerWorld,
    pos: Vec3d,
    block: HolderAttachment.() -> Unit = {}
): HolderAttachment = ChunkAttachment.ofTicking(holder, world, pos).apply(block)

public inline fun identifiedUniqueEntityAttachment(
    id: Identifier,
    holder: ElementHolder,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment = IdentifiedUniqueEntityAttachment.of(id, holder, entity).apply(block)

public inline fun identifiedUniqueEntityAttachmentTicking(
    id: Identifier,
    holder: ElementHolder,
    entity: Entity,
    block: IdentifiedUniqueEntityAttachment.() -> Unit = {}
): IdentifiedUniqueEntityAttachment = IdentifiedUniqueEntityAttachment.ofTicking(id, holder, entity).apply(block)

public inline fun fallingBlockEntityAttachment(
    holder: ElementHolder,
    entity: FallingBlockEntity,
    block: FallingBlockEntityAttachment.() -> Unit = {}
): FallingBlockEntityAttachment = FallingBlockEntityAttachment(holder, entity).apply(block)

public inline fun pistonAttachment(
    holder: ElementHolder,
    chunk: WorldChunk,
    state: BlockState,
    pos: BlockPos,
    direction: Direction,
    block: PistonAttachment.() -> Unit = {}
): PistonAttachment = PistonAttachment(holder, chunk, state, pos, direction).apply(block)

@ExperimentalPolymerApi
public inline fun blockBoundAttachment(
    holder: ElementHolder,
    world: ServerWorld,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? {
    @Suppress("UnstableApiUsage")
    return BlockBoundAttachment.of(holder, world, pos, state)?.apply(block)
}

@ExperimentalPolymerApi
public inline fun blockBoundAttachment(
    holder: ElementHolder,
    world: ServerWorld,
    chunk: WorldChunk,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? {
    @Suppress("UnstableApiUsage")
    return BlockBoundAttachment.of(holder, world, chunk, pos, state)?.apply(block)
}

@ExperimentalPolymerApi
public inline fun blockBoundAttachmentMoving(
    holder: ElementHolder,
    world: ServerWorld,
    pos: BlockPos,
    state: BlockState,
    block: BlockBoundAttachment.() -> Unit = {}
): BlockBoundAttachment? {
    @Suppress("UnstableApiUsage")
    return BlockBoundAttachment.fromMoving(holder, world, pos, state)?.apply(block)
}

public class EntityAttachmentEntityLoadScope @PublishedApi internal constructor()

public inline fun EntityAttachment.onEntityLoad(crossinline block: EntityAttachmentEntityLoadScope.() -> Unit): Disposable =
    (this as EntityAttachmentHook).`pupil$addEntityLoadListener` { EntityAttachmentEntityLoadScope().block() }

public class EntityAttachmentEntityUnloadScope @PublishedApi internal constructor()

public inline fun EntityAttachment.onEntityUnload(crossinline block: EntityAttachmentEntityUnloadScope.() -> Unit): Disposable =
    (this as EntityAttachmentHook).`pupil$addEntityUnloadListener` { EntityAttachmentEntityUnloadScope().block() }