package com.step.pda.app.delegate.bottom;

import java.util.LinkedHashMap;

/**
 * Created by user on 2019-08-06.
 * 将tab跟ui绑定起来，建造器
 */

public class ItemBuilder {
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    static ItemBuilder builder() {
        //简单工程模式
        return new ItemBuilder();
    }
    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }
    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
