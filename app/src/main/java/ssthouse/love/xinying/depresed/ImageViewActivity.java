package ssthouse.love.xinying.depresed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.utils.ImageUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/5/19.
 */
public class ImageViewActivity extends AppCompatActivity {

    public static void start(Context context, String imageName) {
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra("imageName", imageName);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_view);
        initImageView();
    }

    private void initImageView(){
        Intent intent = getIntent();
        String imageName = intent.getStringExtra("imageName");
        int resourceId = ImageUtil.getImageId(this, imageName);
        Timber.e("iamgName: "+imageName);
        ImageViewTouch imageViewTouch = (ImageViewTouch) findViewById(R.id.id_iv);
        imageViewTouch.setImageResource(resourceId);
        imageViewTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
    }
}
