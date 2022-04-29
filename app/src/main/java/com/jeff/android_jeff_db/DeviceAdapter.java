package com.jeff.android_jeff_db;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private List<Device> mDeviceList;
    private Context mContext;//用于传入上下文

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView deviceName;
        TextView deviceLongitude;
        TextView deviceLatitude;
        TextView deviceStatus;
        TextView deviceInfo;

        //注册外层的点击事件
        View deviceView;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceView = itemView;

            deviceName = (TextView) itemView.findViewById(R.id.device_name_overview);
            deviceLongitude = (TextView) itemView.findViewById(R.id.device_longitude_overview);
            deviceLatitude = (TextView) itemView.findViewById(R.id.device_latitude_overview);
            deviceStatus = (TextView) itemView.findViewById(R.id.device_status_overview);
            deviceInfo = (TextView) itemView.findViewById(R.id.textView_cabinet_info);
        }
    }

    public DeviceAdapter(List<Device> deviceList){
        mDeviceList = deviceList;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_first, parent, false);
        ViewHolder holder = new ViewHolder(view);

        mContext = parent.getContext();

        //点击外层布局
        //跳转到下一层视图
        holder.deviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Device device = mDeviceList.get(position);
                //Toast.makeText(view.getContext(),"you click "+device.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MainActivity2.class);

                //放置触发切换视图的机柜名
                intent.putExtra("trigger_name",holder.deviceName.getText().toString());
                intent.putExtra("trigger_lo",Float.parseFloat(device.getLongitude()));//字符串转浮点数
                intent.putExtra("trigger_la",Float.parseFloat(device.getLatitude()));

                mContext.startActivity(intent);
            }
        });

        //内部各个子项的点击
        //点击这一项就不会触发外层的布局
        holder.deviceLongitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Device device = mDeviceList.get(position);
                Toast.makeText(view.getContext(),"you click longitude "+device.getLongitude(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//展示内部的List
        Device device = mDeviceList.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceLongitude.setText(device.getLongitude());
        holder.deviceLatitude.setText(device.getLatitude());

        holder.deviceStatus.setText(device.getStatus());
        //Log.d("color",device.getStatus());
        if(device.getStatus().equals("1")){
            //holder.deviceStatus.setBackgroundColor(0x008000);
            holder.deviceStatus.setBackgroundColor(Color.GREEN);
        }
        else {
            holder.deviceStatus.setBackgroundColor(Color.RED);
        }

        holder.deviceInfo.setText(device.getInfo());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
