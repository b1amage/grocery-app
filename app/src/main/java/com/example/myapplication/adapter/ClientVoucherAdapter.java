package com.example.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.ImageLoader;

import java.util.List;

public class ClientVoucherAdapter extends BaseAdapter {
    private List<Voucher> voucherList;

    public ClientVoucherAdapter(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }


    @Override
    public int getCount() {
        return voucherList.size();
    }

    @Override
    public Object getItem(int i) {
        return voucherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View itemView = View.inflate(parent.getContext(), R.layout.voucher, null);

        Voucher voucher = (Voucher) getItem(i);
        ((TextView) itemView.findViewById(R.id.voucher_title)).setText(voucher.getTitle());
        ((TextView) itemView.findViewById(R.id.voucher_value)).setText(String.valueOf(voucher.getValue()) + (voucher.getType().equals("percentage") ? "%" : "VND"));
        ((TextView) itemView.findViewById(R.id.voucher_description)).setText(voucher.getDescription());

        return itemView;
    }
}
