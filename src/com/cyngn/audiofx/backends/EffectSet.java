package com.cyngn.audiofx.backends;

import android.media.AudioDeviceInfo;

/**
 * Helper class representing the full complement of effects attached to one
 * audio session.
 */
public abstract class EffectSet {

    protected static final String TAG = "AudioFx-EffectSet";

    protected final int mSessionId;

    protected boolean mGlobalEnabled;

    private AudioDeviceInfo mDeviceInfo;

    public EffectSet(int sessionId, AudioDeviceInfo deviceInfo) {
        mSessionId = sessionId;
        mDeviceInfo = deviceInfo;
        onCreate();
    }

    /**
     * Called to do subclass-first initialization in case
     * an implementation has ordering restrictions.
     */
    protected void onCreate() { }

    /**
     * Destroy all effects in this set.
     *
     * Attempting to use this object after calling release is
     * undefined behavior.
     */
    public void release() { }

    /**
     * Returns the enumerated brand of this implementation
     * @return brandId
     */
    public abstract int getBrand();

    /**
     * Called when the user toggles the engine on or off. If the
     * implementation has a built-in bypass mode, this is where
     * to use it.
     *
     * @param globalEnabled
     */
    public void setGlobalEnabled(boolean globalEnabled) {
        mGlobalEnabled = globalEnabled;
    }

    public boolean isGlobalEnabled() {
        return mGlobalEnabled;
    }

    /**
     * Called when the output device has changed. All cached
     * data should be cleared at this point.
     *
     * @param deviceInfo
     */
    public void setDevice(AudioDeviceInfo deviceInfo) {
        mDeviceInfo = deviceInfo;
    }

    /**
     * Return the current active output device
     * @return deviceInfo
     */
    public AudioDeviceInfo getDevice() {
        return mDeviceInfo;
    }

    /**
     * Begin bulk-update of parameters. This can be used if the
     * implementation supports operation in a transactional/atomic
     * manner. Parameter changes will immediately follow this call
     * and should be committed to the backend when the subsequent
     * commitUpdate() is called.
     *
     * Optional.
     *
     * @return status - false on failure
     */
    public boolean beginUpdate() { return true; }

    /**
     * Commit accumulated updates to the backend. See above.
     *
     * begin/commit are used when a large number of parameters need
     * to be sent to the backend, such as in the case of a device
     * switch or preset change. This can increase performance and
     * reduce click/pop issues.
     *
     * Optional.
     *
     * @return status - false on failure
     */
    public boolean commitUpdate() { return true; }

    /* ---- Top level effects begin here ---- */

    // required effects
    public abstract boolean hasVirtualizer();

    public abstract boolean hasBassBoost();

    // optional effects
    public boolean hasTrebleBoost() {
        return false;
    }

    public boolean hasVolumeBoost() {
        return false;
    }

    public boolean hasReverb() {
        return false;
    }

    public abstract void enableEqualizer(boolean enable);

    /**
     * @param levels in decibels
     */
    public abstract void setEqualizerLevelsDecibels(float[] levels);

    public abstract short getNumEqualizerBands();

    /**
     * @param band
     * @param level in millibels
     */
    public abstract void setEqualizerBandLevel(short band, float level);

    /**
     * @return level in millibels
     */
    public abstract int getEqualizerBandLevel(short band);

    public abstract String getEqualizerPresetName(short preset);

    public abstract void useEqualizerPreset(short preset);

    public abstract short getNumEqualizerPresets();

    public abstract short[] getEqualizerBandLevelRange();

    /**
     * @param band
     * @return center frequency of the band in millihertz
     */
    public abstract int getCenterFrequency(short band);

    public abstract void enableBassBoost(boolean enable);

    /**
     * @param strength with range [0-1000]
     */
    public abstract void setBassBoostStrength(short strength);

    public abstract void enableVirtualizer(boolean enable);

    /**
     * @param strength with range [0-1000]
     */
    public abstract void setVirtualizerStrength(short strength);

    public void enableReverb(boolean enable) {
        return;
    }

    public void setReverbPreset(short preset) {
        return;
    }

    public void enableTrebleBoost(boolean enable) {
        return;
    }

    /**
     * @param strength with range [0-100]
     */
    public void setTrebleBoostStrength(short strength) {
        return;
    }

    public void enableVolumeBoost(boolean enable) {
        return;
    }
}
