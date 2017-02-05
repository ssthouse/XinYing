package ssthouse.love.xinying.note;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import io.github.mthli.knife.KnifeText;
import ssthouse.love.xinying.R;
import timber.log.Timber;

/**
 * Created by ssthouse on 2015/12/9.
 */
public class FastNoteProvider extends AppWidgetProvider {

    /**
     * @param context          上下文
     * @param appWidgetManager widget manager
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        updateWidget(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        updateWidget(context);
    }

    private void initWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews rvs = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
        setRvClickListener(context, rvs);
        setRvText(context, rvs);
        appWidgetManager.updateAppWidget(appWidgetIds, rvs);
        // Timber.e("init widget");
    }

    private void updateWidget(Context context) {
        ComponentName componentName = new ComponentName(context, FastNoteProvider.class);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
        setRvText(context, remoteViews);
        setRvClickListener(context, remoteViews);
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        Timber.e("update widget");
    }

    private void setRvClickListener(Context context, RemoteViews rvs) {
        Intent intent = new Intent(context, FastNoteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        rvs.setOnClickPendingIntent(R.id.id_ll_fast_note, pendingIntent);
    }

    private void setRvText(Context context, RemoteViews rvs) {
        //create the custom view ==> KnifeText
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        KnifeText customView = (KnifeText) LayoutInflater.from(context).inflate(R.layout.item_knif_text, null);
        //config custom view
        customView.setLayoutParams(new LinearLayout.LayoutParams(width - 64, height));
        customView.layout(0, 0, width - 64, height);
        customView.fromHtml(FastNoteConfigUtil.getInstance(context).getNote());
        customView.setTextColor(FastNoteConfigUtil.getInstance(context).getColor());
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        customView.draw(new Canvas(bitmap));
        rvs.setImageViewBitmap(R.id.id_iv_fast_note, bitmap);
        rvs.setInt(R.id.id_iv_fast_note, "setBackgroundColor", FastNoteConfigUtil.getInstance(context).getBgColor());
    }
}
