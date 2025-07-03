package org.tomidori.pupil.attachments;

import org.tomidori.pupil.events.Disposable;

@SuppressWarnings("unused")
public interface EntityAttachmentHook {
    Disposable pupil$addEntityLoadListener(Runnable runnable);

    Disposable pupil$addEntityUnloadListener(Runnable runnable);

    void pupil$onEntityLoad();

    void pupil$onEntityUnload();
}
