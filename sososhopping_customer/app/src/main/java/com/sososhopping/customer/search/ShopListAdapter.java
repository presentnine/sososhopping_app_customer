package com.sososhopping.customer.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ItemShopInfoBinding;
import com.sososhopping.customer.home.CategoryAdapter;

import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    ArrayList<ShopInfo> shopLists = new ArrayList<>();
    private CategoryAdapter.OnItemClickListener itemClickListener;
    ItemShopInfoBinding binding;

    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.item_shop_info, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        holder.bindItem(shopLists.get(position));
    }

    @Override
    public int getItemCount() {
        return shopLists.size();
    }

    public void setShopLists(ArrayList<ShopInfo> shopLists) {
        this.shopLists = shopLists;
    }
    public ArrayList<ShopInfo> getShopLists(){
        return shopLists;
    }

    //클릭 이벤트
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemShopInfoBinding binding;

        public ViewHolder(ItemShopInfoBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //리스너 호출
                        if(itemClickListener != null){
                            itemClickListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }

        public void bindItem(ShopInfo shopInfo){
            binding.textViewShopName.setText(shopInfo.getShopName());
            binding.textViewShopDescription.setText(shopInfo.getShopDescription());
            binding.textViewRating.setText(Float.toString(shopInfo.getRating()));
            binding.textViewDistance.setText(Integer.toString(shopInfo.getDistance()));

            //지역화폐, 배달여부
            if(!shopInfo.isLocalPay()){
                binding.layoutLocalPay.setVisibility(View.GONE);
            }
            if(!shopInfo.isDelivery()){
                binding.layoutDelivery.setVisibility(View.GONE);
            }

            //이미지
            Glide.with(itemView)
                    .load(shopInfo.getShopURL())
                    .transform(new CenterCrop(),new RoundedCorners(10))
                    .thumbnail(0.2f)
                    .placeholder(R.drawable.icon_category_cafe)
                    .error(R.drawable.icon_category_cafe)
                    .fallback(R.drawable.icon_category_cafe)
                    .into(binding.imageViewStore);
        }
    }
}
