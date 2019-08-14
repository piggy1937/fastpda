package com.step.pda.app.ui.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhushubin
 */

public abstract class DataConverter<T,K> {
    protected List<T> items =null;
    protected final ArrayList<K> ENTITIES = new ArrayList<>();
    private String mJsonData = null;
   //泛型方法类
    public abstract ArrayList<K> convert();

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
