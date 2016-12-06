package ssthouse.love.xinying.joke.zhihu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.joke.bean.ZhiHuBean;
import timber.log.Timber;

/**
 * Created by ssthouse on 06/12/2016.
 */

public class ZhiHuFragment extends BaseFragment {

    @Bind(R.id.id_lv_zhihu)
    ListView lvZhihu;

    private List<ZhiHuBean.StoriesBean> storiesBeanList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_zhihu;
    }

    @Override
    public void init() {
        //init zhihu data
        ZhiHuGenerator.getStories(new Callback<ZhiHuBean>() {
            @Override
            public void onResponse(Call<ZhiHuBean> call, Response<ZhiHuBean> response) {
                for (ZhiHuBean.StoriesBean storiesBean : response.body().getStories()) {
                    Timber.e(storiesBean.getTitle());
                }
                storiesBeanList.addAll(response.body().getStories());
                lvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ZhiHuBean> call, Throwable t) {
                Timber.e("error");
            }
        });

        lvZhihu.setAdapter(lvAdapter);
    }

    private BaseAdapter lvAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return storiesBeanList.size();
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_zhihu, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ivThumbnail = (ImageView) convertView.findViewById(R.id.id_iv_thumbnail);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.id_tv_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvTitle.setText(storiesBeanList.get(position).getTitle());
            Picasso.with(getContext())
                    .load(storiesBeanList.get(position).getImages().get(0))
                    .into(viewHolder.ivThumbnail);
            return convertView;
        }
    };

    private static class ViewHolder{
        ImageView ivThumbnail;
        TextView tvTitle;
    }
}
