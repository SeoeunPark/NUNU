package com.nunu.NUNU;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

public class CustomDialog extends FragmentActivity {
    private Context context;
    InfoSetting ChangeAll = new InfoSetting();

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {
        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.mydialog);
        dlg.show();

        final Button okButton = (Button) dlg.findViewById(R.id.Confirm);
        final Button cancelButton = (Button) dlg.findViewById(R.id.Cancel);
        final AppDatabase db = Room.databaseBuilder(context,AppDatabase.class,"userinfo-db")
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries()
                .build();



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.UserDao().deleteAll();
                Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
                Intent intent = new Intent(context, InfoSetting.class);
                startActivity(intent);
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, ChangeAll).commitAllowingStateLoss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             dlg.dismiss();
            }
        });
    }
}
