package mekanism.common.tile.laser;

import java.util.Map;
import mekanism.api.IContentsListener;
import mekanism.api.IIncrementalEnum;
import mekanism.api.NBTConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.math.FloatingLong;
import mekanism.api.math.MathUtils;
import mekanism.api.text.IHasTranslationKey;
import mekanism.api.text.ILangEntry;
import mekanism.common.MekanismLang;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.energy.LaserEnergyContainer;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.integration.computer.ComputerException;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableEnum;
import mekanism.common.inventory.container.sync.SyncableFloatingLong;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.registries.MekanismAttachmentTypes;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.tile.interfaces.IHasMode;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;

public class TileEntityLaserAmplifier extends TileEntityLaserReceptor implements IHasMode {

    private FloatingLong minThreshold = FloatingLong.ZERO;
    private FloatingLong maxThreshold = MekanismConfig.storage.laserAmplifier.get();
    private int ticks = 0;
    private int delay = 0;
    private boolean emittingRedstone;
    private RedstoneOutput outputMode = RedstoneOutput.OFF;

    public TileEntityLaserAmplifier(BlockPos pos, BlockState state) {
        super(MekanismBlocks.LASER_AMPLIFIER, pos, state);
    }

    @Override
    protected void addInitialEnergyContainers(EnergyContainerHelper builder, IContentsListener listener) {
        builder.addContainer(energyContainer = LaserEnergyContainer.create(BasicEnergyContainer.alwaysTrue, BasicEnergyContainer.internalOnly, this, listener));
    }

    @Override
    protected void onUpdateServer() {
        setEmittingRedstone(false);
        if (ticks < delay) {
            ticks++;
        } else {
            ticks = 0;
        }
        super.onUpdateServer();
        if (outputMode != RedstoneOutput.ENTITY_DETECTION) {
            setEmittingRedstone(false);
        }
    }

    @Override
    protected void setEmittingRedstone(boolean foundEntity) {
        emittingRedstone = foundEntity;
    }

    private boolean shouldFire() {
        return ticks >= delay && energyContainer.getEnergy().compareTo(minThreshold) >= 0 && MekanismUtils.canFunction(this);
    }

    @Override
    protected FloatingLong toFire() {
        return shouldFire() ? super.toFire().min(maxThreshold) : FloatingLong.ZERO;
    }

    @Override
    public int getRedstoneLevel() {
        if (outputMode == RedstoneOutput.ENERGY_CONTENTS) {
            return MekanismUtils.redstoneLevelFromContents(energyContainer.getEnergy(), energyContainer.getMaxEnergy());
        }
        return emittingRedstone ? 15 : 0;
    }

    @Override
    protected boolean makesComparatorDirty(ContainerType<?, ?, ?> type) {
        return type == ContainerType.ENERGY;
    }

    @Override
    protected void notifyComparatorChange() {
        //Notify neighbors instead of just comparators as we also allow for direct redstone levels
        level.updateNeighborsAt(getBlockPos(), getBlockType());
    }

    public void setDelay(int delay) {
        delay = Math.max(0, delay);
        if (this.delay != delay) {
            this.delay = delay;
            markForSave();
        }
    }

    @Override
    public void nextMode() {
        outputMode = outputMode.getNext();
        setChanged();
    }

    @Override
    public void previousMode() {
        outputMode = outputMode.getPrevious();
        setChanged();
    }

    public void setMinThresholdFromPacket(FloatingLong target) {
        if (updateMinThreshold(target)) {
            markForSave();
        }
    }

    public void setMaxThresholdFromPacket(FloatingLong target) {
        if (updateMaxThreshold(target)) {
            markForSave();
        }
    }

    private boolean updateMinThreshold(FloatingLong target) {
        FloatingLong threshold = getThreshold(target);
        if (!minThreshold.equals(threshold)) {
            minThreshold = threshold;
            //If the min threshold is greater than the max threshold, update max threshold
            if (minThreshold.greaterThan(maxThreshold)) {
                maxThreshold = minThreshold;
            }
            return true;
        }
        return false;
    }

    private boolean updateMaxThreshold(FloatingLong target) {
        //Cap threshold at max energy capacity
        FloatingLong threshold = getThreshold(target);
        if (!maxThreshold.equals(threshold)) {
            maxThreshold = threshold;
            //If the max threshold is smaller than the min threshold, update min threshold
            if (maxThreshold.smallerThan(minThreshold)) {
                minThreshold = maxThreshold;
            }
            return true;
        }
        return false;
    }

    private FloatingLong getThreshold(FloatingLong target) {
        FloatingLong maxEnergy = energyContainer.getMaxEnergy();
        return target.smallerOrEqual(maxEnergy) ? target : maxEnergy.copyAsConst();
    }

    @Override
    public void readSustainedData(CompoundTag data) {
        super.readSustainedData(data);
        NBTUtils.setFloatingLongIfPresent(data, NBTConstants.MIN, this::updateMinThreshold);
        NBTUtils.setFloatingLongIfPresent(data, NBTConstants.MAX, this::updateMaxThreshold);
        NBTUtils.setIntIfPresent(data, NBTConstants.TIME, value -> delay = value);
        NBTUtils.setEnumIfPresent(data, NBTConstants.OUTPUT_MODE, RedstoneOutput::byIndexStatic, mode -> outputMode = mode);
    }

    @Override
    public void writeSustainedData(CompoundTag data) {
        super.writeSustainedData(data);
        data.putString(NBTConstants.MIN, minThreshold.toString());
        data.putString(NBTConstants.MAX, maxThreshold.toString());
        data.putInt(NBTConstants.TIME, delay);
        NBTUtils.writeEnum(data, NBTConstants.OUTPUT_MODE, outputMode);
    }

    @Override
    public Map<String, Holder<AttachmentType<?>>> getTileDataAttachmentRemap() {
        Map<String, Holder<AttachmentType<?>>> remap = super.getTileDataAttachmentRemap();
        remap.put(NBTConstants.MIN, MekanismAttachmentTypes.MIN_THRESHOLD);
        remap.put(NBTConstants.MAX, MekanismAttachmentTypes.MAX_THRESHOLD);
        remap.put(NBTConstants.TIME, MekanismAttachmentTypes.DELAY);
        remap.put(NBTConstants.OUTPUT_MODE, MekanismAttachmentTypes.REDSTONE_OUTPUT);
        return remap;
    }

    @Override
    public void readFromStack(ItemStack stack) {
        super.readFromStack(stack);
        updateMinThreshold(stack.getData(MekanismAttachmentTypes.MIN_THRESHOLD));
        updateMaxThreshold(stack.getData(MekanismAttachmentTypes.MAX_THRESHOLD));
        setDelay(stack.getData(MekanismAttachmentTypes.DELAY));
        outputMode = stack.getData(MekanismAttachmentTypes.REDSTONE_OUTPUT);
    }

    @Override
    public void writeToStack(ItemStack stack) {
        super.writeToStack(stack);
        stack.setData(MekanismAttachmentTypes.MIN_THRESHOLD, minThreshold);
        stack.setData(MekanismAttachmentTypes.MAX_THRESHOLD, maxThreshold);
        stack.setData(MekanismAttachmentTypes.DELAY, delay);
        stack.setData(MekanismAttachmentTypes.REDSTONE_OUTPUT, outputMode);
    }

    @Override
    public boolean supportsMode(RedstoneControl mode) {
        return true;
    }

    @ComputerMethod(nameOverride = "getRedstoneOutputMode")
    public RedstoneOutput getOutputMode() {
        return outputMode;
    }

    @ComputerMethod
    public int getDelay() {
        return delay;
    }

    @ComputerMethod
    public FloatingLong getMinThreshold() {
        return minThreshold;
    }

    @ComputerMethod
    public FloatingLong getMaxThreshold() {
        return maxThreshold;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableFloatingLong.create(this::getMinThreshold, value -> minThreshold = value));
        container.track(SyncableFloatingLong.create(this::getMaxThreshold, value -> maxThreshold = value));
        container.track(SyncableInt.create(this::getDelay, value -> delay = value));
        container.track(SyncableEnum.create(RedstoneOutput::byIndexStatic, RedstoneOutput.OFF, this::getOutputMode, value -> outputMode = value));
    }

    //Methods relating to IComputerTile
    @ComputerMethod(requiresPublicSecurity = true)
    void setRedstoneOutputMode(RedstoneOutput mode) throws ComputerException {
        validateSecurityIsPublic();
        if (outputMode != mode) {
            outputMode = mode;
            setChanged();
        }
    }

    @ComputerMethod(nameOverride = "setDelay", requiresPublicSecurity = true)
    void computerSetDelay(int delay) throws ComputerException {
        validateSecurityIsPublic();
        if (delay < 0) {
            throw new ComputerException("Delay cannot be negative. Received: %d", delay);
        }
        setDelay(delay);
    }

    @ComputerMethod(requiresPublicSecurity = true)
    void setMinThreshold(FloatingLong threshold) throws ComputerException {
        validateSecurityIsPublic();
        setMinThresholdFromPacket(threshold);
    }

    @ComputerMethod(requiresPublicSecurity = true)
    void setMaxThreshold(FloatingLong threshold) throws ComputerException {
        validateSecurityIsPublic();
        setMaxThresholdFromPacket(threshold);
    }
    //End methods IComputerTile

    @NothingNullByDefault
    public enum RedstoneOutput implements IIncrementalEnum<RedstoneOutput>, IHasTranslationKey {
        OFF(MekanismLang.OFF),
        ENTITY_DETECTION(MekanismLang.ENTITY_DETECTION),
        ENERGY_CONTENTS(MekanismLang.ENERGY_CONTENTS);

        private static final RedstoneOutput[] MODES = values();
        private final ILangEntry langEntry;

        RedstoneOutput(ILangEntry langEntry) {
            this.langEntry = langEntry;
        }

        @Override
        public String getTranslationKey() {
            return langEntry.getTranslationKey();
        }

        @Override
        public RedstoneOutput byIndex(int index) {
            return byIndexStatic(index);
        }

        public static RedstoneOutput byIndexStatic(int index) {
            return MathUtils.getByIndexMod(MODES, index);
        }
    }
}
