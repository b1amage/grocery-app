package com.example.myapplication.content;

import com.example.myapplication.model.Voucher;

import java.util.ArrayList;

public class Vouchers {
    private ArrayList<Voucher> vouchers;

    public Vouchers() {
        vouchers.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        vouchers.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        vouchers.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        vouchers.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        vouchers.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        vouchers.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
        vouchers.add(new Voucher("abc1", "ABC1","Hello", "percentage", 50));
    }

    public ArrayList<Voucher> getVouchers(){
        return vouchers;
    }
}
