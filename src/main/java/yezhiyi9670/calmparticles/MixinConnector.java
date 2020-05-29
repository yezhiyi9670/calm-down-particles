package yezhiyi9670.calmparticles;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.calmparticles.mixins.json");
    }
}
