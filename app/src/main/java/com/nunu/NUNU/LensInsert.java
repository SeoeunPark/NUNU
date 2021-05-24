//package com.nunu.NUNU;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//
//import petrov.kristiyan.colorpicker.ColorPicker;
//
//public class LensInsert extends AppCompatActivity {
//    public static final String EXTRA_REPLY = "com.nunu.NUNU.REPLY";
//
//    //이미지
//    Calendar myCalendar = Calendar.getInstance();
//
//    private static final String TAG = "blackjin";
//
//    private Boolean isPermission = true;
//
//    private static final int PICK_FROM_ALBUM = 1;
//    private static final int PICK_FROM_CAMERA = 2;
//
//    private File tempFile;
//
//    private int posi;
//    private String clname; // 렌즈 색
//    private Button cancel; //X 버튼
//    private EditText lens_kind;//렌즈 종류
//    private EditText lens_name;//렌즈 이름
//    private EditText lens_type;//렌즈 유형
//    private EditText lens_cnt;//렌즈 개수
//    private EditText lens_cycle;//렌즈 주기
//    private EditText lens_period;//렌즈 기간
//    private Button pallete;//팔레트
//    private Button lens_save;
//    private EditText et_Date;
//    private int kind_num =0; // 1은 원데이 2는 기간렌즈
//    private ImageView imageView;
//    private Button gallery;
//    private Button camera;
//
//    private TextView lens_name_text;
//    private TextView lens_type_text;
//    private TextView lens_cnt_text;
//    private TextView lens_cycle_text;
//    private TextView lens_period_text;
//    private TextView lens_color_text;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lens_insert);
//
//        tedPermission();
//
//        lens_name_text = (TextView)findViewById(R.id.lens_name_text);
//        lens_type_text = (TextView)findViewById(R.id.lens_type_text);
//        lens_cnt_text = (TextView)findViewById(R.id.lens_cnt_text);
//        lens_cycle_text = (TextView)findViewById(R.id.lens_cycle_text);
//        lens_period_text = (TextView) findViewById(R.id.lens_period_text);
//        lens_color_text = (TextView)findViewById(R.id.lens_color_text);
//
//
//        et_Date = (EditText) findViewById(R.id.Oneday_period);
//        lens_kind = (EditText)findViewById(R.id.lens_kind);
//        lens_name = (EditText)findViewById(R.id.lens_name);
//        lens_type = (EditText) findViewById(R.id.lens_type);
//        lens_cnt = (EditText) findViewById(R.id.lens_cnt);
//        lens_cycle = (EditText)findViewById(R.id.lens_cycle);
//        lens_period = (EditText)findViewById(R.id.lens_period);
//        pallete = (Button)findViewById(R.id.lens_color);
//        lens_save = findViewById(R.id.lens_save);
//        cancel = (Button) findViewById(R.id.to_main);
//        imageView = (ImageView)findViewById(R.id.imageView);
//        gallery = (Button)findViewById(R.id.btnGallery);
//        camera = (Button)findViewById(R.id.btnCamera);
//
//        final Context context = this;
//        //렌즈 종류
//        lens_kind.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v){
//                v = v;
//                kind(v);
//            }
//        });
//
//        //이미지
//        findViewById(R.id.btnGallery).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
//                if(isPermission) goToAlbum();
//                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
//            }
//        });
//
//        findViewById(R.id.btnCamera).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
//                if(isPermission)  takePhoto();
//                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
//            }
//        });
//
//        lens_type.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v){
//                type();
//            }
//        });
//
//        lens_save.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent replyIntent = new Intent();
//                if (TextUtils.isEmpty(lens_name.getText()) || TextUtils.isEmpty(lens_type.getText()) ||
//                        TextUtils.isEmpty(clname)// || TextUtils.isEmpty(monthly_start.getText()) ||
//                        || TextUtils.isEmpty(lens_period.getText()) ) {
//                    Toast.makeText(context, "값을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    setResult(RESULT_CANCELED, replyIntent);
//                }
//                else {
//                    //String word = mEditWordView.getText().toString();
//                    replyIntent.putExtra("name",lens_name.getText().toString()); //name 이란 이름으로 one_name에 들어간 text 저장
//                    replyIntent.putExtra("type",lens_type.getText().toString());
//                    replyIntent.putExtra("cnt",Integer.parseInt(lens_cnt.getText().toString()));
//                    replyIntent.putExtra("period",kind_num); // 1은 원데이 2는 기간렌즈
//                    replyIntent.putExtra("cl",clname);
//                    replyIntent.putExtra("start",lens_cycle.getText().toString());
//                    replyIntent.putExtra("end",lens_period.getText().toString());
//                    setResult(RESULT_OK, replyIntent);
//                    finish();
//                }
//            }
//        });
//
//        lens_period.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                new DatePickerDialog(LensInsert.this, myDatePicker_end, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        //x 버튼
//        cancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        //컬러피커
//        pallete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openColorPicker();
//            }
//        });
//    } //end of onCreate
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != Activity.RESULT_OK) {
//            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
//
//            if (tempFile != null) {
//                if (tempFile.exists()) {
//                    if (tempFile.delete()) {
//                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
//                        tempFile = null;
//                    }
//                }
//            }
//
//            return;
//        }
//        if (requestCode == PICK_FROM_ALBUM) {
//
//            Uri photoUri = data.getData();
//            Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);
//
//            Cursor cursor = null;
//
//            try {
//
//                /*
//                 *  Uri 스키마를
//                 *  content:/// 에서 file:/// 로  변경한다.
//                 */
//                String[] proj = {MediaStore.Images.Media.DATA};
//
//                assert photoUri != null;
//                cursor = getContentResolver().query(photoUri, proj, null, null, null);
//
//                assert cursor != null;
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//
//                cursor.moveToFirst();
//
//                tempFile = new File(cursor.getString(column_index));
//
//                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(tempFile));
//
//            } finally {
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//
//            setImage();
//
//        } else if (requestCode == PICK_FROM_CAMERA) {
//
//            setImage();
//
//        }
//    }
//
//    /**
//     *  앨범에서 이미지 가져오기
//     */
//    private void goToAlbum() {
//
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        startActivityForResult(intent, PICK_FROM_ALBUM);
//    }
//
//
//    /**
//     *  카메라에서 이미지 가져오기
//     */
//    private void takePhoto() {
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        try {
//            tempFile = createImageFile();
//        } catch (IOException e) {
//            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//            finish();
//            e.printStackTrace();
//        }
//        if (tempFile != null) {
//
//            Uri photoUri = Uri.fromFile(tempFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//            startActivityForResult(intent, PICK_FROM_CAMERA);
//        }
//    }
//
//    /**
//     *  폴더 및 파일 만들기
//     */
//    private File createImageFile() throws IOException {
//
//        // 이미지 파일 이름 ( blackJin_{시간}_ )
//        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
//        String imageFileName = "blackJin_" + timeStamp + "_";
//
//        // 이미지가 저장될 폴더 이름 ( blackJin )
//        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
//        if (!storageDir.exists()) storageDir.mkdirs();
//
//        // 파일 생성
//        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
//        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());
//
//        return image;
//    }
//
//    /**
//     *  tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
//     */
//    private void setImage() {
//
//        ImageView imageView = findViewById(R.id.imageView);
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
//        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());
//
//        imageView.setImageBitmap(originalBm);
//
//        /**
//         *  tempFile 사용 후 null 처리를 해줘야 합니다.
//         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
//         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
//         */
//        tempFile = null;
//
//    }
//
//    /**
//     *  권한 설정
//     */
//    private void tedPermission() {
//
//        PermissionListener permissionListener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                // 권한 요청 성공
//                isPermission = true;
//
//            }
//
//            @Override
//            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                // 권한 요청 실패
//                isPermission = false;
//
//            }
//        };
//
//        TedPermission.with(this)
//                .setPermissionListener(permissionListener)
//                .setRationaleMessage(getResources().getString(R.string.permission_2))
//                .setDeniedMessage(getResources().getString(R.string.permission_1))
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .check();
//
//    }
//
//
//
//    public void kind(View v){
//        {
//            v=v;
//            final CharSequence[] items ={"원데이렌즈","기간렌즈"};
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//            alertDialogBuilder.setTitle("렌즈종류 선택");
//
//            View finalV = v;
//            alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    EditText type = (EditText) findViewById(R.id.lens_kind);
//                    type.setText(items[id]);
//                    dialog.dismiss();
//                    if(lens_kind.getText().toString().equals("원데이렌즈")) {
//                        kind_num=1;
//                        imageView.setVisibility(finalV.VISIBLE);
//                        gallery.setVisibility(finalV.VISIBLE);
//                        camera.setVisibility(finalV.VISIBLE);
//                        lens_name_text.setVisibility(finalV.VISIBLE);
//                        lens_name.setVisibility(finalV.VISIBLE);
//                        lens_type_text.setVisibility(finalV.VISIBLE);
//                        lens_type.setVisibility(finalV.VISIBLE);
//                        lens_cnt_text.setVisibility(finalV.VISIBLE);
//                        lens_cnt.setVisibility(finalV.VISIBLE);
//                        lens_cycle_text.setVisibility(finalV.GONE);//기간만
//                        lens_cycle.setVisibility(finalV.GONE);//기간만
//                        lens_period_text.setVisibility(finalV.VISIBLE);
//                        lens_period.setVisibility(finalV.VISIBLE);
//                        lens_color_text.setVisibility(finalV.VISIBLE);
//                        pallete.setVisibility(finalV.VISIBLE);
//                    }else if(lens_kind.getText().toString().equals("기간렌즈")){
//                        kind_num=2;
//                        imageView.setVisibility(finalV.VISIBLE);
//                        gallery.setVisibility(finalV.VISIBLE);
//                        camera.setVisibility(finalV.VISIBLE);
//                        lens_name_text.setVisibility(finalV.VISIBLE);
//                        lens_name.setVisibility(finalV.VISIBLE);
//                        lens_type_text.setVisibility(finalV.VISIBLE);
//                        lens_type.setVisibility(finalV.VISIBLE);
//                        lens_cnt_text.setVisibility(finalV.VISIBLE);
//                        lens_cnt.setVisibility(finalV.VISIBLE);
//                        lens_cycle_text.setVisibility(finalV.VISIBLE);//기간만
//                        lens_cycle.setVisibility(finalV.VISIBLE);//기간만
//                        lens_period_text.setVisibility(finalV.VISIBLE);
//                        lens_period.setVisibility(finalV.VISIBLE);
//                        lens_color_text.setVisibility(finalV.VISIBLE);
//                        pallete.setVisibility(finalV.VISIBLE);
//                    }
//                }
//
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }
//    }//end of kind
//
//
//
//    DatePickerDialog.OnDateSetListener myDatePicker_end = new DatePickerDialog.OnDateSetListener(){
//        @Override
//        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            myCalendar.set(java.util.Calendar.YEAR, year);
//            myCalendar.set(java.util.Calendar.MONTH, month);
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            updateLabel_end();
//        }
//    };
//
//    private void updateLabel_end() {
//        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
//        EditText monthly_end = (EditText) findViewById(R.id.lens_period);
//        monthly_end.setText(sdf.format(myCalendar.getTime()));
//    }
//
//    //색상 선택
//    public void openColorPicker() {
//        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
//        ArrayList<String> colors = new ArrayList<>();  // 렌즈 컬러 리스트
//        colors.add("#c35817"); //orange
//        colors.add("#966f33"); //wood
//        colors.add("#493d26"); //mocha
//        colors.add("#657383"); //gray
//        colors.add("#000000"); //black
//        colors.add("#fff380"); //yellow
//        colors.add("#387c44"); //green
//        colors.add("#4863ad"); //blue
//        colors.add("#e38aae"); //pink
//        colors.add("#9172ec"); //purple
//        colorPicker.setColors(colors)  // 만들어둔 list 적용
//                .setColumns(5)  // 5열로 설정
//                .setRoundColorButton(true)  // 원형 버튼으로 설정
//                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
//                    @Override
//                    public void onChooseColor(int position, int color) {
//                        //layout.setBackgroundColor(color);  // OK 버튼 클릭 시 이벤트
//                        pallete.setBackgroundColor(color);
//                        posi = position;
//                        //
//
//                        if(posi == 0){
//                            clname = "오렌지";
//                        }else if(posi == 1){
//                            clname = "연갈색";
//                        }else if(posi == 2){
//                            clname = "갈색";
//                        }else if(posi == 3) {
//                            clname = "회색";
//                        }else if(posi == 4){
//                            clname = "검정색";
//                        }else if(posi == 5){
//                            clname = "노란색";
//                        }else if(posi == 6){
//                            clname = "녹색";
//                        }else if(posi ==7){
//                            clname = "파랑색";
//                        }else if(posi ==8){
//                            clname = "분홍색";
//                        }else if(posi ==9) {
//                            clname = "보라색";
//                        }else{
//                            clname="파랑색";
//                        }
//
//                    }
//                    @Override
//                    public void onCancel() {
//                        // Cancel 버튼 클릭 시 이벤트
//                    }
//                }).show();  // dialog 생성
//        //색 잘 들어가는지 확인
//    }
//    final Context context = this;
//    //렌즈 타입 설정
//    public void type(){
//        {
//            final CharSequence[] items ={"소프트렌즈","하드렌즈","미용렌즈"};
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//            alertDialogBuilder.setTitle("유형 선택");
//
//            alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    EditText type = (EditText) findViewById(R.id.lens_type);
//                    type.setText(items[id]);
//                    dialog.dismiss();
//                }
//            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show(); }
//    }
//    //렌즈 종류 설정
//
//    //메인스레드에서 데이터베이스에 접근할 수 없으므로 AsyncTask를 사용하도록 한다.
//    public static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
//        private LensDao mLensDao;
//
//        public insertAsyncTask(LensDao lensDao) {
//            this.mLensDao = lensDao;
//        }
//
//        @Override //백그라운드작업(메인스레드 X)
//        protected Void doInBackground(Note... lens) {
//            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
//            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
//            mLensDao.insert(lens[0]);
//            return null;
//        }
//    }
//
//    }