package com.jeff.android_jeff_db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {
    //泛型指定为Fruit类

    private int resourceId;

    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        //重写父类的一组构造函数
        //用于将上下文、ListView子项布局的id和数据都传递进来
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //这个方法在每个子项被滚动到屏幕内的时候会被调用
        Fruit fruit = getItem(position);//获取当前的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);//加载布局

        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);

        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());

        return view;
    }
}