package com.step.pda.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by user on 2019-07-30.
 */

public enum  EcIcons implements Icon {
    icon_scan('a'),icon_ali_pay('b');
    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
