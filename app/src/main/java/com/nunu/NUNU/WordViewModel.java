package com.nunu.NUNU;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private  LensRepository mRepository;
    private LiveData<List<Note>> mAllWords;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new LensRepository(application);
        mAllWords=mRepository.getAllWords();
    }

    LiveData<List<Note>> getAllWords() {
        return mAllWords;
    }
    public void insert(Note note){
        mRepository.insert(note);
    }
    public void update(Note note){
        mRepository.update(note);
    }
    public void delete(Note note){
        mRepository.delete(note);
    }
    public void deleteAllNotes(){
        mRepository.deleteAllNotes();
    }

}
