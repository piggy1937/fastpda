package com.step.pda.app.ui.recycler;

import java.util.LinkedHashMap;

/**
 * Created by zhushubin
 */

public class MultipleEntityBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        //clear data
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }
}
