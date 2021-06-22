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
        mlensdao = db.lensDao();
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
            mAsyncTaskDao.insert(lens[0]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        private LensDao mAsyncTaskDao;

        public UpdateAsyncTask(LensDao lensDao) {
            mAsyncTaskDao = lensDao;
        }

        @Override
        protected Void doInBackground(final Note... lens) {
            mAsyncTaskDao.update(lens[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private LensDao mAsyncTaskDao;

        public DeleteAsyncTask(LensDao lensDao) {
            mAsyncTaskDao = lensDao;
        }

        @Override
        protected Void doInBackground(final Note... lens) {
            mAsyncTaskDao.delete(lens[0]);
            return null;
        }
    }

    public static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private LensDao mAsyncTaskDao;

        public DeleteAllNotesAsyncTask(LensDao lensDao) {
            mAsyncTaskDao = lensDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }

    }
}

