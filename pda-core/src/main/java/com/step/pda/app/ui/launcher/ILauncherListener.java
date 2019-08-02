package com.step.pda.app.ui.launcher;

/**
 * Created by user on 2019-08-02.
 */

public interface ILauncherListener {
    public enum LaunchFinishTag{
        SIGNED,NOT_SIGNED
    }
    void onLauncherFinish( LaunchFinishTag tag);
}
