package ssthouse.love.xinying.joke.zhihu;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
                storiesBeanList.clear();
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

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    };

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_zhihu, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.cardView = (CardView) convertView.findViewById(R.id.id_card_view);
                viewHolder.ivThumbnail = (ImageView) convertView.findViewById(R.id.id_iv_thumbnail);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.id_tv_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZhiHuDetailAty.start(getActivity(), storiesBeanList.get(position));
                }
            });
            viewHolder.tvTitle.setText(storiesBeanList.get(position).getTitle());
            Picasso.with(getContext())
                    .load(storiesBeanList.get(position).getImages().get(0))
                    .into(viewHolder.ivThumbnail);
            return convertView;
        }
    };

    private static class ViewHolder{
        CardView cardView;
        ImageView ivThumbnail;
        TextView tvTitle;
    }
}
