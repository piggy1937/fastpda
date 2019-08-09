package com.step.pda.ec.main;

import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.step.pda.app.delegate.bottom.BaseBottomDelegate;
import com.step.pda.app.delegate.bottom.BottomItemDelegate;
import com.step.pda.app.delegate.bottom.BottomTabBean;
import com.step.pda.app.delegate.bottom.ItemBuilder;
import com.step.pda.ec.main.index.BigPackingDelegate;
import com.step.pda.ec.main.index.IndexDelegate;
import com.step.pda.ec.main.personal.PersonalDelegate;

import java.util.LinkedHashMap;

/**
 * Created by user on 2019-08-06.
 * tab 按钮
 */

public class EcBottomDelegate extends BaseBottomDelegate {
    private final Fragment[] supportFragments = new Fragment[3];
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();

        Fragment IndexDelegate  = getChildFragmentManager().findFragmentByTag(IndexDelegate.class.getName());
        if(IndexDelegate ==null){
            supportFragments[0] =   new IndexDelegate();
            supportFragments[1] =   new BigPackingDelegate();
            supportFragments[2] =   new PersonalDelegate();

        }else{
            supportFragments[0] =   IndexDelegate;
            supportFragments[1] =    getChildFragmentManager().findFragmentByTag(BigPackingDelegate.class.getName());
            supportFragments[2] =    getChildFragmentManager().findFragmentByTag(PersonalDelegate.class.getName());
            flag = true;
        }
        items.put(new BottomTabBean("{fa-ship}", "小包标签"),(BottomItemDelegate)supportFragments[0]);
        items.put(new BottomTabBean("{fa-shield}", "大包标签"), (BottomItemDelegate)supportFragments[1]);
        items.put(new BottomTabBean("{fa-user}", "我的"),  (BottomItemDelegate)supportFragments[2]);
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
