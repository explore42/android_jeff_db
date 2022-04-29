package com.jeff.android_jeff_db;

import android.content.Context;
import android.content.Intent;
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
        TextView deviceInfo;

        //注册外层的点击事件
        View deviceView;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceView = itemView;

            deviceName = (TextView) itemView.findViewById(R.id.device_name_detail);
            deviceStatus = (TextView) itemView.findViewById(R.id.device_status_detail);
            deviceInfo = (TextView) itemView.findViewById(R.id.textView_device_info);
        }
    }

    public DeviceDetailAdapter(List<DeviceDetail> deviceList){
        mDeviceList = deviceList;
    }//传入从数据库查找到的数据

    @Override
    public DeviceDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_detial, parent, false);
        DeviceDetailAdapter.ViewHolder holder = new DeviceDetailAdapter.ViewHolder(view);
        mContext = parent.getContext();

        //点击外层布局
        //跳转到下一层视图
        holder.deviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                DeviceDetail deviceDetail = mDeviceList.get(position);

                Intent intent = new Intent(mContext, MainActivity3.class);
                intent.putExtra("device_website",deviceDetail.getWebsite());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceDetailAdapter.ViewHolder holder, int position) {
        DeviceDetail device = mDeviceList.get(position);//是DeviceDetail类，不是Device类
        holder.deviceName.setText(device.getName());
        holder.deviceStatus.setText(device.getStatus());

        if(device.getStatus().equals("1")){
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
