package com.sososhopping.customer.mysoso.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.DateFormatMethod;
import com.sososhopping.customer.databinding.ItemMysosoPointBinding;
import com.sososhopping.customer.databinding.ItemOrderRecordBinding;
import com.sososhopping.customer.mysoso.model.OrderRecordShortModel;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class MysosoOrderListAdapter extends RecyclerView.Adapter<MysosoOrderListAdapter.ViewHolder>{

    ItemOrderRecordBinding binding;
    ArrayList<OrderRecordShortModel> items;
    OnItemClickListener itemClickListener;

    public MysosoOrderListAdapter(ArrayList<OrderRecordShortModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemOrderRecordBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<OrderRecordShortModel> i){
        this.items = i;
    }

    //클릭 이벤트
    public interface OnItemClickListener{
        void onItemClick(long orderId);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemOrderRecordBinding binding;

        public ViewHolder(ItemOrderRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){

                        //리스너 호출
                        if(itemClickListener != null){
                            itemClickListener.onItemClick(items.get(pos).getOrderId());
                        }
                    }
                }
            });
        }

        public void bindItem(OrderRecordShortModel orderRecordShortModel){
            binding.textViewShopName.setText(orderRecordShortModel.getStoreName());
            binding.textViewOrderDescription.setText(orderRecordShortModel.getOrderDescription());
            binding.textViewOrderPrice.setText(orderRecordShortModel.getFinalPrice());
            binding.textViewOrderDate.setText(DateFormatMethod.dateFormatToKorean(orderRecordShortModel.getCreatedAt()));

            //이미지
            if(orderRecordShortModel.getImgUrl() != null){
                Glide.with(itemView)
                        .load(orderRecordShortModel.getImgUrl())
                        .override(80,80)
                        .transform(new CenterCrop(),new CircleCrop())
                        .thumbnail(0.2f)
                        .placeholder(R.drawable.icon_app_groceries)
                        .error(R.drawable.icon_app_groceries)
                        .fallback(R.drawable.icon_app_groceries)
                        .into(binding.imageViewStore);
            }
        }

    }
}
