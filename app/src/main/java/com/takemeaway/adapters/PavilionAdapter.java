package com.takemeaway.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.takemeaway.R;
import com.takemeaway.TakeMeAwayApplication;
import com.takemeaway.bean.Photo;
import com.takemeaway.view.ForegroundImageView;

import java.util.List;

/**
 * 阁楼数据适配器
 * Created by longjianlin on 14/12/23.
 */
public class PavilionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Photo> mPhotos;
    private ImageLoader imageLoader;

    public PavilionAdapter(Context context, List<Photo> photos) {
        inflater = LayoutInflater.from(context);
        mPhotos = photos;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public Photo getItem(int position) {
        return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup vg) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pavilion, null);
            holder = new ViewHolder();
            holder.iv_photo = (ForegroundImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Photo photo = getItem(position);
        if (photo != null) {
            if (photo.photo_url != null && photo.photo_url.length() > 0) {
                imageLoader.displayImage(photo.photo_url, holder.iv_photo, TakeMeAwayApplication.options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        holder.iv_photo.setImageResource(R.drawable.ic_launcher);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // holder.iv_photo.setImageBitmap(loadedImage);

                        holder.iv_photo.setImageResource(R.drawable.longjl);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }
        }
        return convertView;
    }

    class ViewHolder {
        public ForegroundImageView iv_photo;
    }
}
