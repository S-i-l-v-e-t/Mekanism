package mekanism.defense.client;

import mekanism.client.lang.BaseLanguageProvider;
import mekanism.defense.common.DefenseLang;
import mekanism.defense.common.MekanismDefense;
import mekanism.defense.common.config.DefenseConfigTranslations;
import net.minecraft.data.PackOutput;

public class DefenseLangProvider extends BaseLanguageProvider {

    public DefenseLangProvider(PackOutput output) {
        super(output, MekanismDefense.MODID, MekanismDefense.instance);
    }

    @Override
    protected void addTranslations() {
        addConfigs(DefenseConfigTranslations.values());
        addMisc();
    }

    private void addMisc() {
        addModInfo("Defense module for Mekanism");
        addPackData(DefenseLang.MEKANISM_DEFENSE, DefenseLang.PACK_DESCRIPTION);
    }
}