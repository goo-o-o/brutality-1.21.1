package com.goo.brutality.common.registry;


import com.goo.brutality.common.Brutality;
import com.goo.brutality.common.entity.CylinderCollider;
import com.goo.brutality.common.entity.MistWraith;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BrutalityEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Brutality.MOD_ID);


    public static final DeferredHolder<EntityType<?>, EntityType<MistWraith>> MIST_WRAITH = ENTITY_TYPES.register("mist_wraith",
            () -> EntityType.Builder.<MistWraith>of(MistWraith::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .fireImmune()
                    .build("mist_wraith")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<CylinderCollider>> CYLINDER_COLLIDER = ENTITY_TYPES.register("cylinder_collider",
            () -> EntityType.Builder.<CylinderCollider>of(CylinderCollider::new, MobCategory.MISC)
                    .sized(0, 0)
                    .fireImmune()
                    .noSummon()
                    .build("cylinder_collider")
    );
}
