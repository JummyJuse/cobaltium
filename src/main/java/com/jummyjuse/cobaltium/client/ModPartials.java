package com.jummyjuse.cobaltium.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

/** Single place to expose every Flywheel partial.  CLIENT-SIDE ONLY. */
public final class ModPartials {


    private static PartialModel partial(String name) {
        return PartialModel.of(
                ResourceLocation.fromNamespaceAndPath("cobaltium", "block/" + name)
        );
    }

    public static final PartialModel COBALT_HIDE = partial("cobalt_hide");
    private static PartialModel testPartial(String name) {
        PartialModel model = partial(name);
        System.out.println("[COBALT DEBUG] Partial loaded: " + name + " — " + model.modelLocation());
        return model;
    }
    private ModPartials() {}

}
