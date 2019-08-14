package com.step.pda.ec.main.bigpack;

import com.step.pda.app.ui.recycler.MultipleFields;

import java.util.LinkedHashMap;

public class ExpandableMultipleEntityBuilder {
    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public ExpandableMultipleEntityBuilder() {
        //clear data
        FIELDS.clear();
    }

    public final ExpandableMultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final ExpandableMultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final ExpandableMultipleEntityBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final ExpandableMultipleItemEntity build() {
        return new ExpandableMultipleItemEntity(FIELDS);
    }
}
