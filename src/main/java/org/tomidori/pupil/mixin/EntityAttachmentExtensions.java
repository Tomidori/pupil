package org.tomidori.pupil.mixin;

import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.tomidori.pupil.attachments.EntityAttachmentHook;
import org.tomidori.pupil.events.Disposable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = EntityAttachment.class, remap = false)
public abstract class EntityAttachmentExtensions implements EntityAttachmentHook {
    @Unique
    private final List<Runnable> pupil$entityLoadListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Runnable> pupil$entityUnloadListeners = new CopyOnWriteArrayList<>();

    @Override
    public Disposable pupil$addEntityLoadListener(Runnable runnable) {
        pupil$entityLoadListeners.add(runnable);
        return () -> pupil$entityLoadListeners.remove(runnable);
    }

    @Override
    public Disposable pupil$addEntityUnloadListener(Runnable runnable) {
        pupil$entityUnloadListeners.add(runnable);
        return () -> pupil$entityUnloadListeners.remove(runnable);
    }

    @Override
    public void pupil$onEntityLoad() {
        pupil$entityLoadListeners.forEach(Runnable::run);
    }

    @Override
    public void pupil$onEntityUnload() {
        pupil$entityUnloadListeners.forEach(Runnable::run);
    }
}
