package com.example.demo.main.fourth;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.demo.aidl.Book;
import com.example.demo.aidl.IBookManager;
import com.example.demo.common.utils.LogUtil;
import com.example.demo.main.R;
import com.example.demo.common.BaseCommonActivity;
import com.example.demo.common.utils.ToastUtil;

import java.util.List;

public class AidlActivity extends BaseCommonActivity {
    private IBookManager mBookManager;
    private List<Book> mBookList;
    private Button mButtonGet;
    private Button mButtonAdd;
    private TextView mTextViewGet;
    private TextView mTextViewAdd;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ToastUtil.toastLong(getString(R.string.connect_success));
            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                mBookList = mBookManager.getBookList();
            } catch (RemoteException e) {
                LogUtil.e(e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ToastUtil.toastLong(getString(R.string.disconnect));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_layout);
        mButtonGet = findViewById(R.id.button_get);
        mButtonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBookList != null) {
                    mTextViewGet.setText("");
                    for (Book book : mBookList) {
                        mTextViewGet.append(book.toString());
                    }
                } else {
                    ToastUtil.toastLong(getString(R.string.connect_fail));
                    mTextViewGet.setText(getString(R.string.connect_fail_tip));
                }
            }
        });
        mButtonAdd = findViewById(R.id.button_add);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
        mTextViewGet = findViewById(R.id.text_get);
        mTextViewAdd = findViewById(R.id.text_add);

        Intent intent = new Intent();
        intent.setPackage("com.example.demo.service");
        intent.setAction("com.example.demo.SERVICE_MAIN");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    private void addBook() {
        if ((mBookManager == null) || (mBookList == null)) {
            ToastUtil.toastLong(getString(R.string.connect_fail));
            mTextViewGet.setText(getString(R.string.connect_fail_tip));
        } else {
            Book book = new Book(10086, "《客户端新书》");
            try {
                mBookManager.addBook(book);
                StringBuilder result = new StringBuilder();
                mBookList = mBookManager.getBookList();
                for (Book book1 : mBookList) {
                    result.append(book1.toString());
                }

                ToastUtil.toastLong(result.toString());
            } catch (RemoteException e) {
                LogUtil.e(e.getMessage());
            }
        }
    }
}
