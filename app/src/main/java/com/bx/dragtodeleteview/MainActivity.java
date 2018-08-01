package com.bx.dragtodeleteview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
*@author small white
*@date 2018/7/31
*@fuction demo
*/
public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<Integer> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 5;
            }
        });
        setData();
    }

    private void setData(){
        if (data==null){
            data=new ArrayList<>();
            for (int i = 0; i <20 ; i++) {
                data.add(i+1);
            }
        }
        DeleteAdapter adapter = new DeleteAdapter();
        rv.setAdapter(adapter);
    }
    /**
     * 构造adapter
     */
    private class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_delete,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tv.setText("第"+data.get(position)+"行");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv;
            public ViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.item);
            }
        }
    }
}
