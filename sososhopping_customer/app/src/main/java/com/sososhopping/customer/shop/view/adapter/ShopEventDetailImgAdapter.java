package com.sososhopping.customer.shop.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ItemShopEventImgBinding;

public class ShopEventDetailImgAdapter extends RecyclerView.Adapter<ShopEventDetailImgAdapter.ViewHolder> {
    ItemShopEventImgBinding binding;
    private String[] sliderImage;

    public ShopEventDetailImgAdapter(String[] sliderImage){
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemShopEventImgBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    public void setSliderImage(String[] sliderImage){
        this.sliderImage = sliderImage;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(ItemShopEventImgBinding binding) {
            super(binding.getRoot());
        }

        public void bindItem(String imageURL) {
            //이미지
            Glide.with(itemView)
                    .load(imageURL)
                    .transform(new CenterCrop())
                    .placeholder(R.drawable.icon_app_groceries)
                    .error(R.drawable.icon_app_groceries)
                    .fallback(R.drawable.icon_app_groceries)
                    .into(binding.imageSlider);
        }
    }
}