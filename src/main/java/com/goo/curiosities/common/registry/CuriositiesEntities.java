package com.goo.curiosities.common.registry;


import com.goo.curiosities.common.Curiosities;
import com.goo.curiosities.common.entity.GlitchField;
import com.goo.curiosities.common.entity.HoneyGlobuleEntity;
import com.goo.curiosities.common.entity.TimeStopField;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CuriositiesEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Curiosities.MOD_ID);


    public static final DeferredHolder<EntityType<?>, EntityType<HoneyGlobuleEntity>> HONEY_GLOBULE = ENTITY_TYPES.register("honey_globule",
            () -> EntityType.Builder.of(HoneyGlobuleEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .fireImmune()
                    .build("honey_globule")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<TimeStopField>> TIME_STOP_FIELD = ENTITY_TYPES.register("time_stop_field",
            () -> EntityType.Builder.<TimeStopField>of(TimeStopField::new, MobCategory.MISC)
                    .sized(0, 0)
                    .fireImmune()
                    .build("time_stop_field")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<GlitchField>> GLITCH_FIELD = ENTITY_TYPES.register("glitch_field",
            () -> EntityType.Builder.<GlitchField>of(GlitchField::new, MobCategory.MISC)
                    .sized(0, 0)
                    .fireImmune()
                    .build("glitch_field")
    );
}