package ssthouse.love.xinying.main.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.ImageViewActivity;
import ssthouse.love.xinying.utils.ImageUtil;

/**
 * Created by ssthouse on 16/5/19.
 */
public class FistImpressionFragment extends Fragment {


    private static int screenWidth;
    private static final String IMAGE_NAME_PREFIX = "fist_impression_";
    private static final int IMAGE_NUMBER = 8;

    @Bind(R.id.id_gv)
    StaggeredGridView mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取屏幕宽度
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        View rootView = inflater.inflate(R.layout.fragment_energy_girl, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return IMAGE_NUMBER;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_card, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.iv = (ImageView) convertView.findViewById(R.id.id_iv);
                    viewHolder.tv = (TextView) convertView.findViewById(R.id.id_tv);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                setImageResource(viewHolder.iv, IMAGE_NAME_PREFIX + (position + 1), position);
                viewHolder.tv.setText(IMAGE_NAME_PREFIX + (position + 1));
                return convertView;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageViewActivity.start(getContext(), IMAGE_NAME_PREFIX + (position+1));
            }
        });
    }

    //设置ImageView 资源
    private void setImageResource(final ImageView iv, String imageName, final int position) {
        //获取options
        int resourceId = ImageUtil.getImageId(getContext(), imageName);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId, options);

       //设置imageView大小
        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
        layoutParams.width = screenWidth / 2;
        int offsetHeight = (position+1)%2==0? (position+1)%3*20 : -(position+1)%3*20;
        layoutParams.height = (int) ((layoutParams.width + 0.0) * options.outHeight / options.outWidth)+offsetHeight;
        iv.setLayoutParams(layoutParams);
        //加载图片
        Observable.just(position)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        //resource id
                        int resourceId = ImageUtil.getImageId(getContext(), IMAGE_NAME_PREFIX+(position+1));
                        //options
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        //Timber.e("resourceID:  " + resourceId);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId, options);

                        int requestWidth, requestHeight;
                        requestWidth = screenWidth / 2;
                        int offsetHeight = (position+1)%2==0? (position+1)%3*20 : -(position+1)%3*20;
                        requestHeight = (int) ((requestWidth + 0.0) * options.outHeight / options.outWidth)+offsetHeight;
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

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
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

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height * 10 / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width * 10 / (float) reqWidth);
            }
        }
        //Timber.e("sampleSize:  " + inSampleSize);
        return inSampleSize;
    }

    private class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
