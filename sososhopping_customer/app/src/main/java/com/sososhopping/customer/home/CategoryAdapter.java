package com.sososhopping.customer.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ItemHomeCategoryBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<String> category_name = new ArrayList<>();
    ArrayList<Integer> category_icon = new ArrayList<>();
    private OnItemClickListener itemClickListener;
    ItemHomeCategoryBinding binding;

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.item_home_category, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bindItem(category_name.get(position), category_icon.get(position));
    }

    public void setCategory(ArrayList<String> name, ArrayList<Integer> iconId){
        this.category_name = name;
        this.category_icon = iconId;
    }

    @Override
    public int getItemCount() {
        return category_name.size();
    }

    public String getCategoryName(int position){
        return category_name.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    //클릭 이벤트
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemHomeCategoryBinding binding;

        public ViewHolder(ItemHomeCategoryBinding binding) {
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

        public void bindItem(String text, int icon){
            binding.textViewCategory.setText(text);
            binding.imageButtonCategory.setImageResource(icon);
        }
    }
}
