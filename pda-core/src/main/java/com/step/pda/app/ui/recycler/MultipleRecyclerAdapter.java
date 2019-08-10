package com.step.pda.app.ui.recycler;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.step.pda.R;
import com.step.pda.app.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 傅令杰
 */

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements
        BaseQuickAdapter.SpanSizeLookup,
        OnItemClickListener {

    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;
    private onSlideListener mOnSlideListener;
    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }

    public void refresh(List<MultipleItemEntity> data) {
        getData().clear();
        setNewData(data);
        notifyDataSetChanged();
    }
    public void refresh() {
        notifyDataSetChanged();
    }


    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final String lastModifyTime;
        final int quantity;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                quantity =Integer.parseInt(entity.getField(MultipleFields.QUANTITY).toString());
                lastModifyTime =entity.getField(MultipleFields.LAST_MODIFY_TIME);
                holder.setText(R.id.text_single, text);
                holder.setText(R.id.tv_quantity, quantity+"");
                holder.setText(R.id.tv_last_modify_time,lastModifyTime);
                AppCompatTextView mTvEdite =holder.getView(R.id.tv_edite);
                mTvEdite.setOnClickListener(new  View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        mOnSlideListener.onModify(holder.getAdapterPosition());
                    }
                });
                AppCompatTextView mTvDelete =holder.getView(R.id.tv_delete);
                mTvDelete.setOnClickListener(new  View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        mOnSlideListener.onDel(holder.getAdapterPosition());
                    }
                });
                break;
            case ItemType.IMAGE:
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_single));
                break;
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));
                holder.setText(R.id.tv_multiple, text);
                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return   4;//getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }

    public void onSlideListener(onSlideListener onSlideListener) {
        if(onSlideListener !=null){
            this.mOnSlideListener = onSlideListener;
        }
    }



    /**
     * 和Activity通信的接口
     */
    public interface onSlideListener {
        void onDel(int position);//删除
        void onModify(int position);//修改
    }

//    @Override
//    public int getItemCount() {
//        if(getData()!=null&&getData().size()>0)
//             return getData().size();
//        return 0;
//    }
}
