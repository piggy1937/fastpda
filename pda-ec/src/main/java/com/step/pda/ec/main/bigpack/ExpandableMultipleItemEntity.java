package com.step.pda.ec.main.bigpack;

import com.chad.library.adapter.base.entity.IExpandable;
import com.step.pda.app.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/***
 * 可折叠的MultipleItemEntity
 */
public class ExpandableMultipleItemEntity extends MultipleItemEntity implements IExpandable<ExpandableMultipleItemEntity> {
    public ExpandableMultipleItemEntity(LinkedHashMap<Object, Object> fields) {
        super(fields);
    }
    public static ExpandableMultipleEntityBuilder _builder(){
        return new ExpandableMultipleEntityBuilder();
    }
    protected boolean mExpandable = false;
    protected List<ExpandableMultipleItemEntity> mSubItems;

    @Override
    public boolean isExpanded() {
        return mExpandable;
    }

    @Override
    public void setExpanded(boolean expanded) {
        mExpandable = expanded;
    }

    @Override
    public List<ExpandableMultipleItemEntity> getSubItems() {
        return mSubItems;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public boolean hasSubItem() {
        return mSubItems != null && mSubItems.size() > 0;
    }

    public void setSubItems(List<ExpandableMultipleItemEntity> list) {
        mSubItems = list;
    }

    public MultipleItemEntity getSubItem(int position) {
        if (hasSubItem() && position < mSubItems.size()) {
            return mSubItems.get(position);
        } else {
            return null;
        }
    }

    public int getSubItemPosition(MultipleItemEntity subItem) {
        return mSubItems != null ? mSubItems.indexOf(subItem) : -1;
    }

    public void addSubItem(ExpandableMultipleItemEntity subItem) {
        if (mSubItems == null) {
            mSubItems = new ArrayList<>();
        }
        mSubItems.add(subItem);
    }

    public void addSubItem(int position, ExpandableMultipleItemEntity subItem) {
        if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
            mSubItems.add(position, subItem);
        } else {
            addSubItem(subItem);
        }
    }

    public boolean contains(MultipleItemEntity subItem) {
        return mSubItems != null && mSubItems.contains(subItem);
    }

    public boolean removeSubItem(MultipleItemEntity subItem) {
        return mSubItems != null && mSubItems.remove(subItem);
    }

    public boolean removeSubItem(int position) {
        if (mSubItems != null && position >= 0 && position < mSubItems.size()) {
            mSubItems.remove(position);
            return true;
        }
        return false;
    }

}
