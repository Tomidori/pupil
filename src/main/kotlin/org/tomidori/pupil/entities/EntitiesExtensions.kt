package org.tomidori.pupil.entities

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityAttachmentType
import net.minecraft.entity.EntityType
import net.minecraft.util.math.Vec3d

public val Entity.passengerOffset: Vec3d
    get() = attachments.getPointOrDefault(EntityAttachmentType.PASSENGER)

public val EntityType<*>.passengerOffset: Vec3d
    get() = dimensions.attachments.getPointOrDefault(EntityAttachmentType.PASSENGER)