package com.jeff.android_jeff_db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private List<Device> mDeviceList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView deviceName;
        TextView deviceLongitude;
        TextView deviceLatitude;
        TextView deviceStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceName = (TextView) itemView.findViewById(R.id.device_name);
            deviceLongitude = (TextView) itemView.findViewById(R.id.device_longitude);
            deviceLatitude = (TextView) itemView.findViewById(R.id.device_latitude);
            deviceStatus = (TextView) itemView.findViewById(R.id.device_status);
        }
    }

    public DeviceAdapter(List<Device> deviceList){
        mDeviceList = deviceList;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
        Device device = mDeviceList.get(position);
        holder.deviceName.setText(device.getName());
        holder.deviceLongitude.setText(device.getLongitude());
        holder.deviceLatitude.setText(device.getLatitude());
        holder.deviceStatus.setText(device.getStatus());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
