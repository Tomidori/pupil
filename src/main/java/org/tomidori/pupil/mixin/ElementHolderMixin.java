package org.tomidori.pupil.mixin;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.tomidori.pupil.events.Disposable;
import org.tomidori.pupil.holders.ElementHolderHook;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(value = ElementHolder.class, remap = false)
public abstract class ElementHolderMixin implements ElementHolderHook {
    @Unique
    private final List<Consumer<ServerPlayNetworkHandler>> pupil$startWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Consumer<ServerPlayNetworkHandler>> pupil$stopWatchingListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Runnable> pupil$tickListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<BiConsumer<HolderAttachment, @Nullable HolderAttachment>> pupil$attachmentSetListeners = new CopyOnWriteArrayList<>();
    @Unique
    private final List<Consumer<HolderAttachment>> pupil$attachmentRemovedListeners = new CopyOnWriteArrayList<>();

    @Override
    public Disposable pupil$addStartWatchingListener(Consumer<ServerPlayNetworkHandler> consumer) {
        pupil$startWatchingListeners.add(consumer);
        return () -> pupil$startWatchingListeners.remove(consumer);
    }

    @Override
    public Disposable pupil$addStopWatchingListener(Consumer<ServerPlayNetworkHandler> consumer) {
        pupil$stopWatchingListeners.add(consumer);
        return () -> pupil$stopWatchingListeners.remove(consumer);
    }

    @Override
    public Disposable pupil$addTickListener(Runnable runnable) {
        pupil$tickListeners.add(runnable);
        return () -> pupil$tickListeners.remove(runnable);
    }

    @Override
    public Disposable pupil$addAttachmentSetListener(BiConsumer<HolderAttachment, @Nullable HolderAttachment> consumer) {
        pupil$attachmentSetListeners.add(consumer);
        return () -> pupil$attachmentSetListeners.remove(consumer);
    }

    @Override
    public Disposable pupil$addAttachmentRemovedListener(Consumer<HolderAttachment> consumer) {
        pupil$attachmentRemovedListeners.add(consumer);
        return () -> pupil$attachmentRemovedListeners.remove(consumer);
    }

    @Inject(method = "startWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void pupil$startWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            pupil$startWatchingListeners.forEach(consumer -> consumer.accept(player));
        }
    }

    @Inject(method = "stopWatching(Lnet/minecraft/server/network/ServerPlayNetworkHandler;)Z", at = @At(value = "RETURN"))
    private void pupil$stopWatching(ServerPlayNetworkHandler player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            pupil$stopWatchingListeners.forEach(consumer -> consumer.accept(player));
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Leu/pb4/polymer/virtualentity/api/ElementHolder;onTick()V"))
    private void pupil$tick(CallbackInfo ci) {
        pupil$tickListeners.forEach(Runnable::run);
    }

    @Inject(method = "onAttachmentSet", at = @At(value = "TAIL"))
    private void pupil$onAttachmentSet(HolderAttachment attachment, @Nullable HolderAttachment oldAttachment, CallbackInfo ci) {
        pupil$attachmentSetListeners.forEach(consumer -> consumer.accept(attachment, oldAttachment));
    }

    @Inject(method = "onAttachmentRemoved", at = @At(value = "TAIL"))
    private void pupil$onAttachmentRemoved(HolderAttachment oldAttachment, CallbackInfo ci) {
        pupil$attachmentRemovedListeners.forEach(consumer -> consumer.accept(oldAttachment));
    }
}
