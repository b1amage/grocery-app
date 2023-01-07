package com.example.myapplication.content;

import com.example.myapplication.model.Voucher;

import java.util.ArrayList;

public class Vouchers {
    private ArrayList<Voucher> voucherList;

    public Vouchers() {
        voucherList = new ArrayList<>();
        this.voucherList.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        this.voucherList.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        this.voucherList.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        this.voucherList.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        this.voucherList.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        this.voucherList.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        this.voucherList.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
    }

    public ArrayList<Voucher> getVouchers(){
        return voucherList;
    }
}
