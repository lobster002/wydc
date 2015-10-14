package com.sky.wydc.utils;

import com.sky.wydc.httputils.HttpUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

public class ImageLoader {
	private LruCache<String, Bitmap> mCache;
	private static ImageLoader instance;// 实例

	public static ImageLoader getInstance() {
		if (instance == null) {
			synchronized (ImageLoader.class) {
				if (instance == null)
					instance = new ImageLoader();
			}
		}
		return instance;
	}

	private ImageLoader() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 4;
		mCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}

	private void addBitmapToCache(String url, Bitmap bitmap) {
		if (getBitmapFromCache(url) == null) {
			mCache.put(url, bitmap);
		}
	}

	private Bitmap getBitmapFromCache(String url) {
		return mCache.get(url);
	}

	public void showImageByAsynctask(ImageView imageview, String url) {
		if (imageview.getTag().equals(url)) {
			Bitmap bm = mCache.get(url);
			if (bm == null) {
				MyAsyncTask task = new MyAsyncTask(imageview);
				task.execute(url);
			} else {
				imageview.setImageBitmap(bm);
			}
		}
	}

	private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

		private ImageView imageview;

		public MyAsyncTask(ImageView imageView) {
			this.imageview = imageView;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			imageview.setImageBitmap(result);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bit = null;
			String url = params[0];
			bit = BitmapFactory.decodeStream(HttpUtils.getInputStream(url));
			addBitmapToCache(url, bit);
			return bit;
		}
	}
}
