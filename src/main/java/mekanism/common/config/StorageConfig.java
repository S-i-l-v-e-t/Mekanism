package mekanism.common.config;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.value.CachedFloatingLongValue;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;

public class StorageConfig extends BaseMekanismConfig {

    private final ModConfigSpec configSpec;

    public final CachedFloatingLongValue enrichmentChamber;
    public final CachedFloatingLongValue osmiumCompressor;
    public final CachedFloatingLongValue combiner;
    public final CachedFloatingLongValue crusher;
    public final CachedFloatingLongValue metallurgicInfuser;
    public final CachedFloatingLongValue purificationChamber;
    public final CachedFloatingLongValue energizedSmelter;
    public final CachedFloatingLongValue digitalMiner;
    public final CachedFloatingLongValue electricPump;
    public final CachedFloatingLongValue chargePad;
    public final CachedFloatingLongValue rotaryCondensentrator;
    public final CachedFloatingLongValue chemicalOxidizer;
    public final CachedFloatingLongValue chemicalInfuser;
    public final CachedFloatingLongValue chemicalInjectionChamber;
    public final CachedFloatingLongValue electrolyticSeparator;
    public final CachedFloatingLongValue precisionSawmill;
    public final CachedFloatingLongValue chemicalDissolutionChamber;
    public final CachedFloatingLongValue chemicalWasher;
    public final CachedFloatingLongValue chemicalCrystallizer;
    public final CachedFloatingLongValue seismicVibrator;
    public final CachedFloatingLongValue pressurizedReactionBase;
    public final CachedFloatingLongValue fluidicPlenisher;
    public final CachedFloatingLongValue laser;
    public final CachedFloatingLongValue laserAmplifier;
    public final CachedFloatingLongValue laserTractorBeam;
    public final CachedFloatingLongValue formulaicAssemblicator;
    public final CachedFloatingLongValue teleporter;
    public final CachedFloatingLongValue modificationStation;
    public final CachedFloatingLongValue isotopicCentrifuge;
    public final CachedFloatingLongValue nutritionalLiquifier;
    public final CachedFloatingLongValue antiprotonicNucleosynthesizer;
    public final CachedFloatingLongValue pigmentExtractor;
    public final CachedFloatingLongValue pigmentMixer;
    public final CachedFloatingLongValue paintingMachine;
    public final CachedFloatingLongValue spsPort;
    public final CachedFloatingLongValue dimensionalStabilizer;

    StorageConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Machine Energy Storage Config. This config is synced from server to client.").push("storage");

        enrichmentChamber = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "enrichmentChamber",
              FloatingLong.createConst(20_000));
        osmiumCompressor = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "osmiumCompressor",
              FloatingLong.createConst(80_000));
        combiner = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "combiner",
              FloatingLong.createConst(40_000));
        crusher = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "crusher",
              FloatingLong.createConst(20_000));
        metallurgicInfuser = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "metallurgicInfuser",
              FloatingLong.createConst(20_000));
        purificationChamber = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "purificationChamber",
              FloatingLong.createConst(80_000));
        energizedSmelter = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "energizedSmelter",
              FloatingLong.createConst(20_000));
        digitalMiner = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "digitalMiner",
              FloatingLong.createConst(50_000));
        electricPump = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "electricPump",
              FloatingLong.createConst(40_000));
        chargePad = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "chargePad", FloatingLong.createConst(2_048_000));
        rotaryCondensentrator = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "rotaryCondensentrator",
              FloatingLong.createConst(20_000));
        chemicalOxidizer = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "chemicalOxidizer",
              FloatingLong.createConst(80_000));
        chemicalInfuser = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "chemicalInfuser",
              FloatingLong.createConst(80_000));
        chemicalInjectionChamber = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "chemicalInjectionChamber",
              FloatingLong.createConst(160_000));
        electrolyticSeparator = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "electrolyticSeparator",
              FloatingLong.createConst(160_000));
        precisionSawmill = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "precisionSawmill",
              FloatingLong.createConst(20_000));
        chemicalDissolutionChamber = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "chemicalDissolutionChamber",
              FloatingLong.createConst(160_000));
        chemicalWasher = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "chemicalWasher",
              FloatingLong.createConst(80_000));
        chemicalCrystallizer = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "chemicalCrystallizer",
              FloatingLong.createConst(160_000));
        seismicVibrator = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "seismicVibrator",
              FloatingLong.createConst(20_000));
        pressurizedReactionBase = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "pressurizedReactionBase",
              FloatingLong.createConst(2_000));
        fluidicPlenisher = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "fluidicPlenisher",
              FloatingLong.createConst(40_000));
        laser = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "laser",
              FloatingLong.createConst(2_000_000));
        laserAmplifier = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "laserAmplifier",
              FloatingLong.createConst(5_000_000_000L));
        laserTractorBeam = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "laserTractorBeam",
              FloatingLong.createConst(5_000_000_000L));
        formulaicAssemblicator = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "formulaicAssemblicator",
              FloatingLong.createConst(40_000));
        teleporter = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "teleporter",
              FloatingLong.createConst(5_000_000));
        modificationStation = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "modificationStation",
              FloatingLong.createConst(40_000));
        isotopicCentrifuge = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "isotopicCentrifuge",
              FloatingLong.createConst(80_000));
        nutritionalLiquifier = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "nutritionalLiquifier",
              FloatingLong.createConst(40_000));
        antiprotonicNucleosynthesizer = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules). Also defines max process rate.", "antiprotonicNucleosynthesizer",
              FloatingLong.createConst(1_000_000_000));
        pigmentExtractor = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "pigmentExtractor",
              FloatingLong.createConst(40_000));
        pigmentMixer = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "pigmentMixer",
              FloatingLong.createConst(80_000));
        paintingMachine = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "paintingMachine",
              FloatingLong.createConst(40_000));
        spsPort = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules). Also defines max output rate.", "spsPort",
              FloatingLong.createConst(1_000_000_000));
        dimensionalStabilizer = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "dimensionalStabilizer",
              FloatingLong.createConst(40_000));

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "machine-storage";
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