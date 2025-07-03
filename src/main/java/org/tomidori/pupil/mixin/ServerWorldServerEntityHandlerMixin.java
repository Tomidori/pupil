package org.tomidori.pupil.mixin;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.impl.HolderAttachmentHolder;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tomidori.pupil.attachments.EntityAttachmentHook;

@Mixin(targets = "net/minecraft/server/world/ServerWorld$ServerEntityHandler")
public abstract class ServerWorldServerEntityHandlerMixin {
    @Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void pupil$startTracking(Entity entity, CallbackInfo ci) {
        for (HolderAttachment holderAttachment : ((HolderAttachmentHolder) entity).polymerVE$getHolders()) {
            if (holderAttachment instanceof EntityAttachmentHook hook) {
                hook.pupil$onEntityLoad();
            }
        }
    }

    @Inject(method = "stopTracking(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"))
    private void pupil$stopTracking(Entity entity, CallbackInfo info) {
        for (HolderAttachment holderAttachment : ((HolderAttachmentHolder) entity).polymerVE$getHolders()) {
            if (holderAttachment instanceof EntityAttachmentHook hook) {
                hook.pupil$onEntityUnload();
            }
        }
    }
}
