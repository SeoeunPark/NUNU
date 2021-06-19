package com.nunu.NUNU;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
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

public class Lens extends Fragment implements View.OnClickListener {
    private static final String TAG = "Lens";
//    private Animation fab_open, fab_close;
//    private Boolean isFabOpen = false;
//    private FloatingActionButton fab, fab1, fab2;
    RecyclerView recyclerView;
    private List<Note> mDataItemList;
    private NoteAdapter mListAdapter;
    private TextView emptyText;
    private Button oneday_btn;
    private Button monthly_btn;
    private Button add_lens_btn;
    private int one_or_mon =1;
//    private TextView fab1t, fab2t;
    private com.airbnb.lottie.LottieAnimationView emptyImage;
    //private ImageView emptyImage;
    NoteAdapter adapter;
    Context context;
    Oneday oneday;
    Monthly monthly;
    Dialog dialog;
    Dialog all_dialog;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int ED_NOTE_REQUEST = 3;
    private WordViewModel mWordViewModel;
    SwipeController swipeController = null;

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
        monthly_btn = (Button)rootView.findViewById(R.id.monthly_select_btn);
        oneday_btn = (Button)rootView.findViewById(R.id.oneday_select_btn);
        add_lens_btn = (Button)rootView.findViewById(R.id.add_lens_btn);

        all_dialog = new Dialog(getActivity());
        all_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        all_dialog.setContentView(R.layout.all_delete_dialog);

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.delete_dialog);

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
                if (notes.size() == 0) {
                    emptyText.setVisibility(View.VISIBLE);
                    emptyImage.setVisibility(View.VISIBLE);
                }
                adapter.setItems(notes);
            }
        });

        swipeController = new SwipeController (new SwipeControllerActions () {
            @Override
            public void onRightClicked (int position) {
                dialog.show(); // 다이얼로그 띄우기
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

                Button noBtn = dialog.findViewById(R.id.noBtn);
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });
                // 네 버튼
                dialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mWordViewModel.delete(adapter.getNoteAt(position));
                        Toast.makeText(getActivity(), "렌즈가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });

            }
            public void onLeftClicked(int position) {
                if (adapter.getNoteAt(position).getLens_period() == 1) {
                            Intent intent = new Intent(getActivity(), EditOneday.class);
                            intent.putExtra("id", adapter.getNoteAt(position).get_id());
                            intent.putExtra("name", adapter.getNoteAt(position).getLens_name()); //name 이란 이름으로 one_name에 들어간 text 저장
                            intent.putExtra("type", adapter.getNoteAt(position).getLens_type());
                            intent.putExtra("cnt", adapter.getNoteAt(position).getLens_cnt());
                            intent.putExtra("period", adapter.getNoteAt(position).getLens_period());
                            intent.putExtra("cl", adapter.getNoteAt(position).getLens_color());
                            intent.putExtra("start", adapter.getNoteAt(position).getLens_start());
                            intent.putExtra("end", adapter.getNoteAt(position).getLens_end());
                            startActivityForResult(intent, ED_NOTE_REQUEST);
                        } else if (adapter.getNoteAt(position).getLens_period() == 2) {
                            Intent intent = new Intent(getActivity(), EditMonthly.class);
                            intent.putExtra("id", adapter.getNoteAt(position).get_id());
                            intent.putExtra("name", adapter.getNoteAt(position).getLens_name()); //name 이란 이름으로 one_name에 들어간 text 저장
                            intent.putExtra("type", adapter.getNoteAt(position).getLens_type());
                            intent.putExtra("cnt", adapter.getNoteAt(position).getLens_cnt());
                            intent.putExtra("period", adapter.getNoteAt(position).getLens_period());
                            intent.putExtra("cl", adapter.getNoteAt(position).getLens_color());
                            intent.putExtra("start", adapter.getNoteAt(position).getLens_start());
                            intent.putExtra("end", adapter.getNoteAt(position).getLens_end());
                            startActivityForResult(intent, ED_NOTE_REQUEST);
                        }
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper (swipeController);
        itemTouchhelper. attachToRecyclerView (recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        //클릭했을 때의 이벤트

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(getActivity(), Detail.class);
                intent.putExtra("id", note.get_id());
                intent.putExtra("name", note.getLens_name()); //name 이란 이름으로 one_name에 들어간 text 저장
                intent.putExtra("type", note.getLens_type());
                intent.putExtra("cnt", note.getLens_cnt());
                intent.putExtra("period", note.getLens_period());
                intent.putExtra("cl", note.getLens_color());
                intent.putExtra("start", note.getLens_start());
                intent.putExtra("end", note.getLens_end());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
                getActivity().overridePendingTransition(R.anim.sliding_up, R.anim.stay);
            }
        });
        //이건 floating 버튼 애니메이션

        add_lens_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(one_or_mon ==1){
                    Intent o_intent = new Intent(view.getContext(), Oneday.class); //이거 진짜 중요
                    startActivityForResult(o_intent, NEW_WORD_ACTIVITY_REQUEST_CODE); // 이것도 중요
                }else if (one_or_mon==2){
                    Intent m_intent = new Intent(view.getContext(), Monthly.class); //이거 진짜 중요
                    startActivityForResult(m_intent, NEW_WORD_ACTIVITY_REQUEST_CODE); // 이것도 중요
                }
            }
        });

        oneday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneday_btn.setBackgroundResource(R.drawable.lens_select_button);
                oneday_btn.setTextColor(Color.WHITE);
                monthly_btn.setBackgroundResource(R.drawable.lens_unselect_button);
                monthly_btn.setTextColor(Color.parseColor("#7C889A"));
                one_or_mon=1;
                Toast.makeText(getActivity(),"원데이 렌즈가 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        monthly_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                monthly_btn.setBackgroundResource(R.drawable.lens_select_button);
                monthly_btn.setTextColor(Color.WHITE);
                oneday_btn.setBackgroundResource(R.drawable.lens_unselect_button);
                oneday_btn.setTextColor(Color.parseColor("#7C889A"));
                one_or_mon=2;
                Toast.makeText(getActivity(),"기간 렌즈가 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

//        initFL(rootView);
        recyclerView.setAdapter(adapter);
        return rootView;
    } // end of onCreateView

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Note word = new Note(data.getExtras().getString("name"), data.getExtras().getString("type"), data.getExtras().getInt("cnt"), data.getExtras().getInt("period"), data.getExtras().getString("cl"), data.getExtras().getString("start"), data.getExtras().getString("end"));
            mWordViewModel.insert(word); //갑 저장
        } else if (requestCode == ED_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
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
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lens_menu, menu);
    }

    //모든 렌즈 삭제
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                all_dialog.show(); // 다이얼로그 띄우기
                all_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 투명 배경

                Button noBtn = all_dialog.findViewById(R.id.noBtn);
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 원하는 기능 구현
                        all_dialog.dismiss(); // 다이얼로그 닫기
                    }
                });
                // 네 버튼
                all_dialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 원하는 기능 구현
                        mWordViewModel.deleteAllNotes();
                        Toast.makeText(getActivity(),"모든 렌즈가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        all_dialog.dismiss();           // 앱 종료
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //floating 버튼 눌르면 뜨게 작업

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oneday = new Oneday();
        monthly = new Monthly();
    }

    @Override
    public void onClick(View view) {

    }

        public interface OnListFragmentInteractionListener {
            //onClick items of list
            void onListClickItem(Note dataItem);

            //onClick delete item from list
            void onListFragmentDeleteItemById(long idItem);
        }
}