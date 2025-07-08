package org.tomidori.pupil.mixin;

import eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement;
import eu.pb4.polymer.virtualentity.api.elements.MarkerElement;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.tomidori.pupil.elements.MarkerElementHook;

import java.util.Objects;

@Mixin(value = MarkerElement.class, remap = false)
public abstract class MarkerElementMixin extends GenericEntityElement implements MarkerElementHook {
    @Override
    public void pupil$setSmall(boolean small) {
        dataTracker.set(ArmorStandEntity.ARMOR_STAND_FLAGS, pupil$setBitField(pupil$getFlag(), ArmorStandEntity.SMALL_FLAG, small));
    }

    @Override
    public boolean pupil$isSmall() {
        return (pupil$getFlag() & ArmorStandEntity.SMALL_FLAG) != 0;
    }

    @Override
    public void pupil$setMarker(boolean marker) {
        dataTracker.set(ArmorStandEntity.ARMOR_STAND_FLAGS, pupil$setBitField(pupil$getFlag(), ArmorStandEntity.MARKER_FLAG, marker));
    }

    @Override
    public boolean pupil$isMarker() {
        return (pupil$getFlag() & ArmorStandEntity.MARKER_FLAG) != 0;
    }

    @Unique
    private byte pupil$getFlag() {
        return Objects.requireNonNullElse(dataTracker.get(ArmorStandEntity.ARMOR_STAND_FLAGS), (byte) 0);
    }

    @Unique
    private byte pupil$setBitField(byte value, int bitField, boolean set) {
        return set ? (byte) (value | bitField) : (byte) (value & ~bitField);
    }
}
