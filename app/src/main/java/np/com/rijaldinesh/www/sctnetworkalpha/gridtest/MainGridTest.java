package np.com.rijaldinesh.www.sctnetworkalpha.gridtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import android.content.*;
import android.widget.ImageView;
import android.widget.TextView;

import np.com.rijaldinesh.www.sctnetworkalpha.R;


/**
 * Created by dinesh on 5/13/16.
 */
public class MainGridTest extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_grid);
        GridView gridView = (GridView)findViewById(R.id.gridviewTest);
        gridView.setAdapter(new MyAdapter(this));
        gridView.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }

        });
    }
    private final class MyAdapter extends BaseAdapter {
        private final List<Item> mItems = new ArrayList<Item>();
        private final LayoutInflater mInflater;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);

            mItems.add(new Item("Red",       R.drawable.menu_main));
            mItems.add(new Item("Magenta",   R.drawable.profile));
            mItems.add(new Item("Dark Gray", R.drawable.scan_barcode));
            mItems.add(new Item("Gray",      R.drawable.setting));
            mItems.add(new Item("Green",     R.drawable.menu_main));
            mItems.add(new Item("Cyan",      R.drawable.profile));
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Item getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mItems.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ImageView picture;
            TextView name;

            if (v == null) {
                v = mInflater.inflate(R.layout.grid_iteam_test, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView) v.getTag(R.id.picture);
            name = (TextView) v.getTag(R.id.text);

            Item item = getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);

            return v;
        }

        private class Item {
            public final String name;
            public final int drawableId;

            Item(String name, int drawableId) {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
    }
}
