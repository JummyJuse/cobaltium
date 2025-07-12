package com.jummyjuse.cobaltium.item;

import com.simibubi.create.content.equipment.armor.CardboardArmorStealthOverlay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class CobaltAlloyHelmetItem extends CobaltAlloyArmorItem {

    public CobaltAlloyHelmetItem(Properties props) {
        super(Type.HELMET, props);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);                    // overlay for invis
        //consumer.accept(new CardboardArmorStealthOverlay()); // *helmet* shape renderer
    }
}