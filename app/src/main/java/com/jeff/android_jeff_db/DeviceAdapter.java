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

        Button deviceMap;

        //注册外层的点击事件
        View deviceView;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceView = itemView;

            //更改之前
            //deviceName = (TextView) itemView.findViewById(R.id.device_name);
            //deviceLongitude = (TextView) itemView.findViewById(R.id.device_longitude);
            //deviceLatitude = (TextView) itemView.findViewById(R.id.device_latitude);
            //deviceStatus = (TextView) itemView.findViewById(R.id.device_status);

            //更改之后
            deviceName = (TextView) itemView.findViewById(R.id.device_name_overview);
            deviceLongitude = (TextView) itemView.findViewById(R.id.device_longitude_overview);
            deviceLatitude = (TextView) itemView.findViewById(R.id.device_latitude_overview);
            deviceStatus = (TextView) itemView.findViewById(R.id.device_status_overview);

            deviceMap = (Button) itemView.findViewById(R.id.device_map_button);
        }
    }

    public DeviceAdapter(List<Device> deviceList){
        mDeviceList = deviceList;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);

        //更改之后
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

        holder.deviceMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"进入地图",Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
