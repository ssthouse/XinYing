package ssthouse.love.xinying.main.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import ssthouse.love.xinying.utils.ImageUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/5/19.
 */
public class BaseFragment extends Fragment {


    public static int screenWidth;
    private String imageNamePrefix = "";
    private int imageNumber = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取屏幕宽度
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    //设置ImageView 资源
    protected void setImageResource(final ImageView iv, final String imageName, final int position) {
        //获取options
        int resourceId = ImageUtil.getImageId(getContext(), imageName);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId, options);

        //设置imageView大小
        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
        layoutParams.width = screenWidth / 2;
        int offsetHeight = (position + 1) % 2 == 0 ? (position + 1) % 3 * 20 : -(position + 1) % 3 * 20;
        layoutParams.height = (int) ((layoutParams.width + 0.0) * options.outHeight / options.outWidth) + offsetHeight;
        iv.setLayoutParams(layoutParams);
        //加载图片
        Observable.just(position)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        //resource id
                        int resourceId = ImageUtil.getImageId(getContext(), imageName);
                        //options
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        Timber.e("resourceID:  " + resourceId);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId, options);

                        int requestWidth, requestHeight;
                        requestWidth = screenWidth / 2;
                        int offsetHeight = (position + 1) % 2 == 0 ? (position + 1) % 3 * 20 : -(position + 1) % 3 * 20;
                        requestHeight = (int) ((requestWidth + 0.0) * options.outHeight / options.outWidth) + offsetHeight;
                        return decodeSampledBitmapFromResource(getResources(), resourceId, requestWidth, requestHeight);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        iv.setImageBitmap(bitmap);
                    }
                });
    }

    protected Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    protected int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height * 6 / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width * 6 / (float) reqWidth);
            }
        }
        //Timber.e("sampleSize:  " + inSampleSize);
        return inSampleSize;
    }

    private class ViewHolder {
        ImageView iv;
        TextView tv;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getImageNamePrefix() {
        return imageNamePrefix;
    }

    public void setImageNamePrefix(String imageNamePrefix) {
        this.imageNamePrefix = imageNamePrefix;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }
}
