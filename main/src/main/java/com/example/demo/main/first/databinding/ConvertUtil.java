package com.example.demo.main.first.databinding;

import androidx.databinding.BindingConversion;

public class ConvertUtil {

    //添加该注解，dataBinding自动寻找到此方法，进行数据转换
    @BindingConversion
    public static String convertLong(long time) {
        return String.valueOf(time);
    }

    @BindingConversion
    public static String convertInt(int likeCount) {
        return String.valueOf(likeCount);
    }
}
