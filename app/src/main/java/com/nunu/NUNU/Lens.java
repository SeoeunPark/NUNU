package com.nunu.NUNU;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Lens extends Fragment implements View.OnClickListener{
        private static final String TAG= "Lens";
        private Animation fab_open, fab_close;
        private Boolean isFabOpen = false;
        private FloatingActionButton fab, fab1, fab2;
        RecyclerView recyclerView;
        private List<Note> mDataItemList;
        private NoteAdapter mListAdapter;
        private TextView emptyText;
        private TextView fab1t,fab2t;
        private com.airbnb.lottie.LottieAnimationView emptyImage;
        //private ImageView emptyImage;
        NoteAdapter adapter;
        Context context;
        Oneday oneday;
        Monthly monthly;

        public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
        public static final int EDIT_NOTE_REQUEST =2;
        public static final int ED_NOTE_REQUEST =3;
        private WordViewModel mWordViewModel;

        public void setListData(List<Note> dataItemList) {
            //if data changed, set new list to adapter of recyclerview

            if (mDataItemList == null) {
                mDataItemList = new ArrayList<>();
            }
            mDataItemList.clear();
            mDataItemList.addAll(dataItemList);

            if (mListAdapter != null) {
                mListAdapter.setItems(dataItemList);
                emptyText.setVisibility(View.GONE);
                emptyImage.setVisibility(View.GONE);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_lens, container, false);
            emptyImage = (LottieAnimationView) rootView.findViewById(R.id.emptyimage);
            emptyText = (TextView) rootView.findViewById(R.id.emptytext);
            fab1t = (TextView) rootView.findViewById(R.id.fab1text);
            fab2t = (TextView) rootView.findViewById(R.id.fab2text);
            super.onCreate(savedInstanceState);
            RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
            final NoteAdapter adapter = new NoteAdapter(getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            setHasOptionsMenu(true); //전체삭제할수 있는 상단 바 보여주기
            mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
            mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                @Override
                public void onChanged(@Nullable final List<Note> notes) {
                    emptyText.setVisibility(View.GONE);
                    emptyImage.setVisibility(View.GONE);
                    //update RecylerView 리사이클러뷰 업데이트 매우 중요
                    if (notes.size()==0) {
                        emptyText.setVisibility(View.VISIBLE);
                        emptyImage.setVisibility(View.VISIBLE);
                    }
                    adapter.setItems(notes);
                }
            });

            //스와이프해서 삭제
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    switch (direction){
                        case ItemTouchHelper.LEFT:
                            mWordViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(getActivity(), "렌즈가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case ItemTouchHelper.RIGHT:
                                    if(adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_period()==1){
                                        Intent intent = new Intent(getActivity(),EditOneday.class);
                                        intent.putExtra("id", adapter.getNoteAt(viewHolder.getAdapterPosition()).get_id());
                                        intent.putExtra("name", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_name()); //name 이란 이름으로 one_name에 들어간 text 저장
                                        intent.putExtra("type", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_type());
                                        intent.putExtra("cnt", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_cnt());
                                        intent.putExtra("period", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_period());
                                        intent.putExtra("cl", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_color());
                                        intent.putExtra("start", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_start());
                                        intent.putExtra("end", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_end());
                                        startActivityForResult(intent, ED_NOTE_REQUEST);
                                    }else if(adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_period()==2) {
                                        Intent intent = new Intent(getActivity(),EditMonthly.class);
                                        intent.putExtra("id", adapter.getNoteAt(viewHolder.getAdapterPosition()).get_id());
                                        intent.putExtra("name", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_name()); //name 이란 이름으로 one_name에 들어간 text 저장
                                        intent.putExtra("type", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_type());
                                        intent.putExtra("cnt", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_cnt());
                                        intent.putExtra("period", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_period());
                                        intent.putExtra("cl", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_color());
                                        intent.putExtra("start", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_start());
                                        intent.putExtra("end", adapter.getNoteAt(viewHolder.getAdapterPosition()).getLens_end());
                                        startActivityForResult(intent, ED_NOTE_REQUEST);
                                    }
                            break;
                    }
                }
                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(),R.color.red))
                            .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                            .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(),R.color.green))
                            .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                            .create()
                            .decorate();

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            ).attachToRecyclerView(recyclerView);

            //클릭했을 때의 이벤트

            adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(Note note) {
                    Intent intent = new Intent(getActivity(),Detail.class);
                    intent.putExtra("id",note.get_id());
                    intent.putExtra("name",note.getLens_name()); //name 이란 이름으로 one_name에 들어간 text 저장
                    intent.putExtra("type",note.getLens_type());
                    intent.putExtra("cnt",note.getLens_cnt());
                    intent.putExtra("period",note.getLens_period());
                    intent.putExtra("cl",note.getLens_color());
                    intent.putExtra("start",note.getLens_start());
                    intent.putExtra("end",note.getLens_end());
                    startActivityForResult(intent, EDIT_NOTE_REQUEST);
                }
            });
            //이건 floating 버튼 애니메이션
            initFL(rootView);
            recyclerView.setAdapter(adapter);
            return rootView;
        } // end of onCreateView

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Note word = new Note(data.getExtras().getString("name"),data.getExtras().getString("type"),data.getExtras().getInt("cnt"),data.getExtras().getInt("period"),data.getExtras().getString("cl"),data.getExtras().getString("start"),data.getExtras().getString("end"));
                mWordViewModel.insert(word); //갑 저장
            }
            else if(requestCode == ED_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
                int id = data.getExtras().getInt("eid");
                Note word = new Note(data.getExtras().getString("ename"), data.getExtras().getString("etype"), data.getExtras().getInt("ecnt"), data.getExtras().getInt("eperiod"), data.getExtras().getString("ecl"), data.getExtras().getString("estart"), data.getExtras().getString("eend"));
                word.set_id(id);
                mWordViewModel.update(word);
            }
        }
            //adapter = new NoteAdapter();

        //lens_menu 가져와서 보여주기
        @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            super.onCreateOptionsMenu(menu,inflater);
            inflater.inflate(R.menu.lens_menu,menu);
        }
        //모든 렌즈 삭제
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_all_notes:
                    mWordViewModel.deleteAllNotes();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void initFL(ViewGroup rootView) {
            fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
            fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);

            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab1 = (FloatingActionButton) rootView.findViewById(R.id.fab1);
            fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab2);

            fab.setOnClickListener(this);
            fab1.setOnClickListener(this);
            fab2.setOnClickListener(this);
        }

        //floating 버튼 눌르면 뜨게 작업

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            oneday = new Oneday();
            monthly = new Monthly();
        }
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.fab:
                    anim();
                    break;
                case R.id.fab1:
                    Intent intent = new Intent(v.getContext(), Oneday.class); //이거 진짜 중요
                    startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE); // 이것도 중요
                    anim();
                    break;
                case R.id.fab2:
                    Intent mintent = new Intent(v.getContext(), Monthly.class); //이거 진짜 중요
                    startActivityForResult(mintent, NEW_WORD_ACTIVITY_REQUEST_CODE); // 이것도 중요
                    anim();
                    break;
            }
        }

        public void anim() {
            if (isFabOpen) {
                fab.setImageResource(R.drawable.fab_plus);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab1t.startAnimation(fab_close);
                fab2t.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                isFabOpen = false;
            } else {
                fab.setImageResource(R.drawable.fab_close);
                fab1.startAnimation(fab_open);
                fab2.startAnimation(fab_open);
                fab1t.startAnimation(fab_open);
                fab2t.startAnimation(fab_open);
                fab1.setClickable(true);
                fab2.setClickable(true);
                isFabOpen = true;
            }
        }
        public interface OnListFragmentInteractionListener {
            //onClick items of list
            void onListClickItem(Note dataItem);

            //onClick delete item from list
            void onListFragmentDeleteItemById(long idItem);
        }
}