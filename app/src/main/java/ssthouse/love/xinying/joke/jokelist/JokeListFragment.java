package ssthouse.love.xinying.joke.jokelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.base.BaseFragment;
import ssthouse.love.xinying.joke.JokerGenerator;
import ssthouse.love.xinying.joke.bean.JokeBean;
import ssthouse.love.xinying.utils.ToastUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 06/12/2016.
 */

public class JokeListFragment extends BaseFragment {


    @Bind(R.id.id_lv_jokes)
    ListView lvJokes;

    private List<JokeBean> jokeBeanList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_joke_list;
    }

    @Override
    public void init() {
        lvJokes.setAdapter(jokeAdapter);

        //TODO: should save today's joke in disk to save network
        JokerGenerator.getJokeList(new Callback<List<JokeBean>>() {
            @Override
            public void onResponse(Call<List<JokeBean>> call, Response<List<JokeBean>> response) {
                for (JokeBean jokeBean : response.body()) {
                    jokeBean.setContent(jokeBean.getContent().replace("<br/><br/>", "\n"));
                }
                jokeBeanList.addAll(response.body());
                jokeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<JokeBean>> call, Throwable t) {
                Timber.e("error");
                ToastUtil.show(getContext(), "something is wrong: not network or something else\nplease tell ssthouse");
            }
        });
    }

    private BaseAdapter jokeAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return jokeBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return jokeBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_joke, parent, false);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.id_tv_joke_title);
                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.id_tv_joke_content);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //set value
            viewHolder.tvTitle.setText(jokeBeanList.get(position).getTitle());
            viewHolder.tvContent.setText(jokeBeanList.get(position).getContent());
            return convertView;
        }
    };

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvContent;
    }

}
