package com.example.demo.fourth.recyclerview;

public class RecyclerViewConstants {

    public static class DemoDataType {
        public static final int TYPE_BUYERS_SHOW = 1;
        public static final int TYPE_IMAGE = TYPE_BUYERS_SHOW << 1;
        public static final int TYPE_COMMODITY = TYPE_BUYERS_SHOW & TYPE_IMAGE;
    }
}
