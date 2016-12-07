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
import ssthouse.love.xinying.joke.bean.JuheJokeBean;
import ssthouse.love.xinying.utils.ToastUtil;
import timber.log.Timber;

/**
 * Created by ssthouse on 06/12/2016.
 */

public class JokeListFragment extends BaseFragment {


    @Bind(R.id.id_lv_jokes)
    ListView lvJokes;

    private List<JuheJokeBean.ResultBean> jokeBeanList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_joke_list;
    }

    @Override
    public void init() {
        lvJokes.setAdapter(jokeAdapter);

        //TODO: should save today's joke in disk to save network
        JuHeGenerator.getJuHeJoke(new Callback<JuheJokeBean>() {
            @Override
            public void onResponse(Call<JuheJokeBean> call, Response<JuheJokeBean> response) {
                Timber.e(response.body().toString());
                if (!response.isSuccessful() || response.body() == null
                        || response.body().getError_code() != 0) {
                    ToastUtil.show(getContext(), "Something is wrong\ntell your giant baby");
                    return;
                }
                jokeBeanList.clear();
                jokeBeanList.addAll(response.body().getResult());
                jokeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JuheJokeBean> call, Throwable t) {
                Timber.e("error");
                Timber.e(t.getMessage());
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
            viewHolder.tvContent.setText(jokeBeanList.get(position).getContent());
            return convertView;
        }
    };

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvContent;
    }

}
