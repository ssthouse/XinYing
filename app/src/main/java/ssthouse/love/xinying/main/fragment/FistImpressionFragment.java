package ssthouse.love.xinying.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ssthouse.love.xinying.R;
import ssthouse.love.xinying.main.ImageViewActivity;
import timber.log.Timber;

/**
 * Created by ssthouse on 16/5/19.
 */
public class FistImpressionFragment extends BaseFragment {

    @Bind(R.id.id_gv)
    StaggeredGridView mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setImageNamePrefix("fist_impression_");
        setImageNumber(8);
        View rootView = inflater.inflate(R.layout.fragment_energy_girl, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return getImageNumber();
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
                Timber.e( getImageNamePrefix() + (position + 1));
                setImageResource(viewHolder.iv, getImageNamePrefix() + (position + 1), position);
                viewHolder.tv.setText(getImageNamePrefix() + (position + 1));
                return convertView;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageViewActivity.start(getContext(), getImageNamePrefix() + (position+1));
            }
        });
    }

    private class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
