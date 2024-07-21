package mekanism.defense.common.config;

import mekanism.common.config.BaseMekanismConfig;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;

public class DefenseConfig extends BaseMekanismConfig {

    private final ModConfigSpec configSpec;

    DefenseConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        DefenseConfigTranslations.SERVER_TOP_LEVEL.applyToBuilder(builder).push("defense");

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "defense";
    }

    @Override
    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }
}