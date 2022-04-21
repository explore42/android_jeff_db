package com.jeff.android_jeff_db;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeviceDetailAdapter extends RecyclerView.Adapter<DeviceDetailAdapter.ViewHolder> {
    private List<DeviceDetail> mDeviceList;//更改为新的类型
    private Context mContext;//用于传入上下文

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView deviceName;
        TextView deviceStatus;

        //注册外层的点击事件
        View deviceView;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceView = itemView;

            //更改之后device_name_overview
            deviceName = (TextView) itemView.findViewById(R.id.device_name_detail);
            deviceStatus = (TextView) itemView.findViewById(R.id.device_status_detail);
        }
    }

    public DeviceDetailAdapter(List<DeviceDetail> deviceList){
        mDeviceList = deviceList;
    }

    @Override
    public DeviceDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_detial, parent, false);
        DeviceDetailAdapter.ViewHolder holder = new DeviceDetailAdapter.ViewHolder(view);
        mContext = parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceDetailAdapter.ViewHolder holder, int position) {
        DeviceDetail device = mDeviceList.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceStatus.setText(device.getStatus());

        if(device.getStatus().equals("1")){
            holder.deviceStatus.setBackgroundColor(Color.GREEN);
        }
        else {
            holder.deviceStatus.setBackgroundColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
