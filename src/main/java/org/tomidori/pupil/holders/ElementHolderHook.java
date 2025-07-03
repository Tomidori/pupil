package org.tomidori.pupil.holders;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.tomidori.pupil.events.Disposable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public interface ElementHolderHook {
    Disposable pupil$addStartWatchingListener(Consumer<ServerPlayNetworkHandler> consumer);

    Disposable pupil$addStopWatchingListener(Consumer<ServerPlayNetworkHandler> consumer);

    Disposable pupil$addTickListener(Runnable runnable);

    Disposable pupil$addAttachmentSetListener(BiConsumer<HolderAttachment, @Nullable HolderAttachment> consumer);

    Disposable pupil$addAttachmentRemovedListener(Consumer<HolderAttachment> consumer);
}
