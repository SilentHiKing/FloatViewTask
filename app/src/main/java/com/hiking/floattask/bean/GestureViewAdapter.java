package com.hiking.floattask.bean;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiking.floattask.R;
import com.hiking.floattask.utils.SPUtil;
import com.hiking.floattask.view.WaterDropView;
import com.hiking.floattask.view.WaterDropView.Gesture;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10.
 */
public class GestureViewAdapter extends RecyclerView.Adapter<GestureViewAdapter.GestureViewHolder> {


    private static final String TAG = GestureViewAdapter.class.getSimpleName();
    private ArrayList<GestureBean> mGestureBeans;

    public GestureViewAdapter(ArrayList<GestureBean> gestureBeans){
        mGestureBeans = gestureBeans;
    }

    @Override
    public GestureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new GestureViewHolder(this,LayoutInflater.from(parent.getContext()).inflate(R.layout.gesture_item,parent,false));
    }


    @Override
    public void onBindViewHolder(GestureViewHolder holder, int position) {
        holder.gesture.setText(mGestureBeans.get(position).getGesture());
        holder.gesFunction.setText(mGestureBeans.get(position).getGesFunction());
        holder.iconInfo.setImageResource(R.mipmap.ic_launcher);
    }


    @Override
    public int getItemCount() {
        return mGestureBeans.size();
    }

    class GestureViewHolder extends RecyclerView.ViewHolder{

        GestureViewAdapter mAdapter;

        TextView gesture;
        TextView gesFunction;
        ImageView iconInfo;
        public GestureViewHolder(GestureViewAdapter adapter,View itemView) {
            super(itemView);
            mAdapter = adapter;
            gesture = (TextView) itemView.findViewById(R.id.gesture);
            gesFunction = (TextView) itemView.findViewById(R.id.ges_function);
            iconInfo = (ImageView) itemView.findViewById(R.id.icon_info);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String gesture = mGestureBeans.get(getLayoutPosition()).getGesture();
                    Log.d(TAG,"gesture = "+ gesture +"getLayoutPosition() = "+getLayoutPosition()+ " getOldPosition() = "+ getOldPosition());
                    showSingleChoiceDialog(v.getContext(),mAdapter,mGestureBeans.get(getLayoutPosition()),getLayoutPosition());
                }
            });


        }
    }



    private void showSingleChoiceDialog(final Context c, final GestureViewAdapter adapter, final GestureBean gb, final int position) {
        final String gesture = gb.getGesture();

        final String[] lf = getFuncList(c,gesture);
        if(lf == null){
            Log.e(TAG,"showSingleChoiceDialog失败，没有陈列的");
            return;
        }
        int index =0;
        for (int i = 0;i <lf.length ;i++) {
            if(SPUtil.get(c,gesture,gb.getGesFunction()).equals(lf[i])){
                index =i;
                break;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(gesture);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(lf, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SPUtil.put(c,gesture,lf[which]);
                gb.setGesFunction(lf[which]);
                dialog.dismiss();
            }


        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                adapter.notifyItemChanged(position);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String[] getFuncList(Context c,String gesture) {

        String[] list =null;

        if(gesture.equals(Gesture.SINGLE_CLICK.toString())){
            list = c.getResources().getStringArray(R.array.singleclick);
        }
        else if (gesture.equals(Gesture.DOUBLE_CLICK.toString())){
            list = c.getResources().getStringArray(R.array.doubleclick);
        }
        else if (gesture.equals(Gesture.LONG_PRESS.toString())){
            list = c.getResources().getStringArray(R.array.longpress);
        }
        else if (gesture.equals(Gesture.FLING_UP.toString())){
            list = c.getResources().getStringArray(R.array.flingup);
        }
        else if (gesture.equals(Gesture.FLING_DOWN.toString())){
            list = c.getResources().getStringArray(R.array.flingdown);
        }
        else if (gesture.equals(Gesture.FLING_LEFT.toString())){
            list = c.getResources().getStringArray(R.array.flingleft);
        }
        else if(gesture.equals(Gesture.FLING_RIGHT.toString())){
            list = c.getResources().getStringArray(R.array.flingright);
        }
        return list;
    }
}
