package com.jeff.android_jeff_db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//为RecycleView准备的适配器
//泛型ViewHolder是我们定义的一个内部类
public class FruitAdapter2 extends RecyclerView.Adapter<FruitAdapter2.ViewHolder> {
    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);//view就是RecyclerView子项的最外层布局
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter2(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    //重写onCreateViewHolder()、onBindViewHolder()和getItemCount()这3个方法
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //将fruit_item布局加载进来
        // 然后创建一个ViewHolder实例
        // 并把加载出来的布局传入到构造函数当中
        // 最后将ViewHolder的实例返回
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //对RecyclerView子项的数据进行赋值
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        //用于告诉RecyclerView一共有多少子项
        return mFruitList.size();
    }
}