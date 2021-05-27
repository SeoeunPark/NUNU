package com.nunu.NUNU;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {//implements OnPersonItemClickListener{
    private static OnItemClickListener listener;
    private final LayoutInflater mInflater;
    private static List<Note> mNotes = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = mInflater.inflate(R.layout.note_item, parent, false);
        //만들어둔 note_item 넣기
        return new ViewHolder(itemView);
    }

    //item을 하나하나 보여주는 함수
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mNotes != null) {
            Note current = mNotes.get(position);
            holder.lens_name.setText(current.getLens_name());
            holder.lens_cnt.setText("보유개수 : " + current.getLens_cnt());
            holder.end_date.setText(current.getLens_end());
            holder.rec_line.setColorFilter(Color.parseColor("#55ff0000"));

            //디데이 계산
            String str = current.getLens_end();
            String col = current.getLens_color();
            String[] array = str.split("/");
            int cyear = Integer.parseInt(array[0]);
            int cmonth = Integer.parseInt(array[1]);
            int cday = Integer.parseInt(array[2]);

            if(col.equals("오렌지")){
                holder.rec_line.setColorFilter(Color.parseColor("#c35817"));
            }else if(col.equals("연갈색")){
                holder.rec_line.setColorFilter(Color.parseColor("#966f33"));
            }else if(col.equals("갈색")){
                holder.rec_line.setColorFilter(Color.parseColor("#493d26"));
            }else if(col.equals("회색")) {
                holder.rec_line.setColorFilter(Color.parseColor("#657383"));
            }else if(col.equals("검정색")){
                holder.rec_line.setColorFilter(Color.parseColor("#000000"));
            }else if(col.equals("노란색")){
                holder.rec_line.setColorFilter(Color.parseColor("#f9d71c"));
            }else if(col.equals("녹색")){
                holder.rec_line.setColorFilter(Color.parseColor("#387c44"));
            }else if(col.equals("파랑색")){
                holder.rec_line.setColorFilter(Color.parseColor("#4863ad"));
            }else if(col.equals("분홍색")){
                holder.rec_line.setColorFilter(Color.parseColor("#e38aae"));
            }else if(col.equals("보라색")) {
                holder.rec_line.setColorFilter(Color.parseColor("#9172ec"));
            }else{
                holder.rec_line.setColorFilter(Color.parseColor("#4863ad"));
            }

            LocalDate fromDate = LocalDate.now();
            LocalDate toDate = LocalDate.of(cyear, cmonth, cday);
            long subtract = ChronoUnit.DAYS.between(fromDate, toDate);

            if(subtract<0){
                holder.dday.setText("D + "+Integer.toString(Math.abs((int) subtract)));

            }else if(subtract==0){
                holder.dday.setText("D - DAY");
            }else{
                holder.dday.setText("D - " + Integer.toString((int) subtract));
            }
        } else {
            holder.lens_name.setText("No word");
            holder.lens_cnt.setText("No word");
            holder.end_date.setText("No word");
        }
    }



    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public void setItems(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return mNotes.get(position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout1;
        private TextView lens_name;
        private TextView lens_cnt;
        private TextView end_date;
        private TextView dday;
        private ImageView rec_line;


        public ViewHolder(View itemView) {
            super(itemView);
            layout1 = itemView.findViewById(R.id.layout1);
            lens_name = itemView.findViewById(R.id.lens_name);
            lens_cnt = itemView.findViewById(R.id.lens_cnt);
            rec_line = itemView.findViewById(R.id.rec_line);
            end_date = itemView.findViewById(R.id.end_date);
            dday = itemView.findViewById(R.id.dday_text);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mNotes.get(position));
                    }
                }
            });
        }

        //화면에 보여지게 넣는 함수
        public void setItem(Note item) {
            lens_name.setText(item.getLens_name());
            lens_cnt.setText("보유개수 :" + item.getLens_cnt());
            //start_date.setText(item.getLens_start());
            end_date.setText(item.getLens_end());

        }


    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    NoteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

}