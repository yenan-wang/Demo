package com.example.demo.service.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.demo.aidl.Book;
import com.example.demo.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class BookManagerService extends Service {
    private List<Book> mBookList = new ArrayList<>();

    private IBookManager.Stub mManager = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d("ngb_log", "服务端返回。");
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d("ngb_log", "服务端添加:" + book.toString());
            mBookList.add(book);
        }
    };

    public BookManagerService() {
        mBookList.add(new Book(1, "《小小南瓜饼》"));
        mBookList.add(new Book(2, "《金灿灿的南瓜饼》"));
        mBookList.add(new Book(3, "《苹果南瓜饼》"));
        mBookList.add(new Book(4, "《南瓜饼的100种做法》"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mManager;
    }
}