package com.sososhopping.customer.mysoso.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.common.DateFormatMethod;
import com.sososhopping.customer.databinding.ItemShopReviewBinding;
import com.sososhopping.customer.mysoso.model.MyreviewModel;
import com.sososhopping.customer.shop.model.ReviewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MysosoReviewAdapter extends RecyclerView.Adapter<MysosoReviewAdapter.ViewHolder>{
    ArrayList<MyreviewModel> reviewModels = new ArrayList<>();
    ItemShopReviewBinding binding;
    OnItemLongClickListener itemClickListener;


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemShopReviewBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bindItem(reviewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    public void setReviewModels(ArrayList<MyreviewModel> reviewModels){
        this.reviewModels =reviewModels;
    }

    public ArrayList<MyreviewModel> getReviewModels(){
        return reviewModels;
    }

    //클릭 이벤트
    public interface OnItemLongClickListener{
        void onItemClick(View view, int storeId, int pos);
    }

    public void setOnItemClickListener(OnItemLongClickListener listener){
        this.itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemShopReviewBinding binding;

        public ViewHolder(ItemShopReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){

                        //리스너 호출
                        if(itemClickListener != null){
                            itemClickListener.onItemClick(view,reviewModels.get(pos).getStoreId(), pos);
                        }
                    }
                    return false;
                }
            });
        }

        public void bindItem(MyreviewModel reviewModel){
            binding.textViewNickname.setText(reviewModel.getStoreName());
            binding.textViewReviewContents.setText(reviewModel.getContent());
            binding.textViewRating.setText(Float.toString(reviewModel.getScore()));
            binding.textViewReviewDate.setText(DateFormatMethod.dateFormatMin(reviewModel.getCreatedAt()));
        }
    }
}
