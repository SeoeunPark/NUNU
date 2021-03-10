package com.nunu.NUNU;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LensRepository {
    private LensDao mlensdao;
    private LiveData<List<Note>> mAllData;

    public LensRepository(Application application) {
        LensDatabase db = LensDatabase.getDatabase(application);
        //RoomDatabase에 있는 Dao를 가져온다.
        mlensdao = db.lensDao();
        //Dao의 쿼리를 이용하여 저장되어있는 모든 word를 가져온다.
        mAllData = mlensdao.getAllWords();
    }

    //word를 추가하는 함수
    public void insert(Note note) {
        new insertAsyncTask(mlensdao).execute(note);
    }
    //수정
    public void update(Note note) {
        new UpdateAsyncTask(mlensdao).execute(note);
    }
    //삭제
    public void delete(Note note) {
        new DeleteAsyncTask(mlensdao).execute(note);
    }
    //모두 삭제
    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(mlensdao).execute();
    }
    public LiveData<List<Note>> getAllWords() {
        return mAllData;
    }

    public static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
        private LensDao mAsyncTaskDao;

        public insertAsyncTask(LensDao lensDao) {
            mAsyncTaskDao = lensDao;
        }

        @Override //백그라운드작업(메인스레드 X)
        protected Void doInBackground(final Note... lens) {
            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
            mAsyncTaskDao.insert(lens[0]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        private LensDao mAsyncTaskDao;

        public UpdateAsyncTask(LensDao lensDao) {
            mAsyncTaskDao = lensDao;
        }

        @Override //백그라운드작업(메인스레드 X)
        protected Void doInBackground(final Note... lens) {
            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
            mAsyncTaskDao.update(lens[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private LensDao mAsyncTaskDao;

        public DeleteAsyncTask(LensDao lensDao) {
            mAsyncTaskDao = lensDao;
        }

        @Override //백그라운드작업(메인스레드 X)
        protected Void doInBackground(final Note... lens) {
            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
            mAsyncTaskDao.delete(lens[0]);
            return null;
        }
    }

    //메인스레드에서 데이터베이스에 접근할 수 없으므로 AsyncTask를 사용하도록 한다.
    public static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private LensDao mAsyncTaskDao;

        public DeleteAllNotesAsyncTask(LensDao lensDao) {
            mAsyncTaskDao = lensDao;
        }

        @Override //백그라운드작업(메인스레드 X)
        protected Void doInBackground(Void... voids) {
            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
            mAsyncTaskDao.deleteAll();
            return null;
        }

    }
}

