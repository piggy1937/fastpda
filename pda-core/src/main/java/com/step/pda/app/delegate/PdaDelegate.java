package com.step.pda.app.delegate;

/**
 * Created by user on 2019-07-30.
 */

public abstract class PdaDelegate extends PermissionCheckerDelagate{
    @SuppressWarnings("unchecked")
    public <T extends PdaDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
