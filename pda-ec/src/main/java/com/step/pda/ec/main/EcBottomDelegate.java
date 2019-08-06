package com.step.pda.ec.main;

import android.graphics.Color;

import com.step.pda.app.delegate.bottom.BaseBottomDelegate;
import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.app.delegate.bottom.BottomTabBean;
import com.step.pda.app.delegate.bottom.ItemBuilder;
import com.step.pda.ec.main.index.IndexDelegate;
import com.step.pda.ec.main.personal.PersonalDelegate;

import java.util.LinkedHashMap;

/**
 * Created by user on 2019-08-06.
 * tab 按钮
 */

public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
