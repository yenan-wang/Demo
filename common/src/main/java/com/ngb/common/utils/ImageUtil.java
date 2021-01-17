package com.ngb.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ngb.common.R;

public class ImageUtil {
    public static final int TYPE_CIRCLE_CROP = -1;
    public static final int TYPE_CENTER_CROP = 0;
    private volatile static ImageUtil sInstance;

    private ImageUtil() {

    }

    public static ImageUtil getInstant() {
        if (sInstance == null) {
            synchronized (ImageUtil.class) {
                if (sInstance == null) {
                    sInstance = new ImageUtil();
                }
            }
        }
        return sInstance;
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, 0);
    }

    public void loadImage(Context context, String url, ImageView imageView, int radius) {
        loadImage(context, url, imageView, R.drawable.icon_app, 0);
    }

    public void loadImage(Context context, String url, ImageView imageView, int errorImage, int radius) {
        loadImage(context, url, imageView, errorImage, R.drawable.icon_app, imageView.getMeasuredHeight(), imageView.getMeasuredHeight(), radius);
    }

    public void loadImage(Context context, String url, ImageView imageView, int errorImage, int defaultImage, int width, int height, int radius) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImage)
                .error(errorImage)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                .override(width, height)
                .into(imageView);
    }

    public void loadWebpGif(Context context, String url, ImageView imageView, int defaultImage, int errorImage, int width, int height, int radius) {
        Transformation<Bitmap> crop;
        if (radius == TYPE_CENTER_CROP) {
            crop = new CenterCrop();
        } else if (radius == TYPE_CIRCLE_CROP) {
            crop = new CircleCrop();
        } else {
            crop = new RoundedCorners(radius);
        }
        WebpDrawableTransformation webpDrawableTransformation = new WebpDrawableTransformation(crop);
        Glide.with(context).load(url).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof WebpDrawable) {
                    ((WebpDrawable) resource).setLoopCount(WebpDrawable.LOOP_FOREVER);
                    ((WebpDrawable) resource).start();
                }
                return false;
            }
        }).optionalTransform(crop).optionalTransform(WebpDrawable.class, webpDrawableTransformation).skipMemoryCache(true).into(imageView);
    }

}

