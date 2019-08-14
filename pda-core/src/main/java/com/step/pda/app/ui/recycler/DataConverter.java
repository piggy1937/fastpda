package com.step.pda.app.ui.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhushubin
 */

public abstract class DataConverter<T> {
    protected List<T> items =null;
    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }
    public DataConverter setItems(List<T> items) {
        if(items==null){
            this.items =null;
            return this;
        }
        this.items = items ;
        return this;
    }
    public void clearData(){
        ENTITIES.clear();
    }
}
