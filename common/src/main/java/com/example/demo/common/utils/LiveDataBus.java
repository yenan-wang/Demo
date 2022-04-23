package com.example.demo.common.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LiveDataBus {

    private Map<String, BusMutableLiveData<Object>> mBusMap;

    private LiveDataBus() {
        mBusMap = new HashMap<>();
    }

    private static class SingleHolder {
        private static final LiveDataBus LIVE_DATA_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingleHolder.LIVE_DATA_BUS;
    }

    public <T> MutableLiveData<T> with(String key, Class<T> clz) {
        if (!mBusMap.containsKey(key)) {
            mBusMap.put(key, new BusMutableLiveData<>());
        }
        return (MutableLiveData<T>) mBusMap.get(key);
    }

    public MutableLiveData<Object> with(String key) {
        return with(key, Object.class);
    }

    private static class BusMutableLiveData<T> extends MutableLiveData<T> {
        private Map<Observer<? super T>, Observer<? super T>> map = new HashMap<>();

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull androidx.lifecycle.Observer<? super T> observer) {
            super.observe(owner, observer);
            try {
                hook(observer);
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }

        @Override
        public void observeForever(@NonNull androidx.lifecycle.Observer<? super T> observer) {
            if (!map.containsKey(observer)) {
                map.put(observer, new ObserverWrapper(observer));
            }
            super.observeForever(map.get(observer));
        }

        @Override
        public void removeObserver(@NonNull Observer<? super T> observer) {
            Observer<? super T> realObserver = null;
            if (map.containsKey(observer)) {
                realObserver = map.remove(observer);
            } else {
                realObserver = observer;
            }
            super.removeObserver(realObserver);
        }

        private void hook(Observer<? super T> observer) throws Exception{
            Class<LiveData> classLiveData = LiveData.class;
            Field fieldObservers = classLiveData.getDeclaredField("mObservers");
            fieldObservers.setAccessible(true);
            Object objectObservers = fieldObservers.get(this);
            Class<?> classObservers = objectObservers.getClass();
            Method methodGets = classObservers.getDeclaredMethod("get", Object.class);
            methodGets.setAccessible(true);
            Object objectWrapperEntry = methodGets.invoke(objectObservers, observer);
            Object objectWrapper = null;
            if (objectWrapperEntry instanceof Map.Entry) {
                objectWrapper = ((Map.Entry<?, ?>) objectWrapperEntry).getValue();
            }
            if (objectWrapper == null) {
                throw new NullPointerException("Wrapper can not be null.");
            }
            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
            fieldLastVersion.setAccessible(true);

            Field fieldVersion = classLiveData.getDeclaredField("mVersion");
            fieldVersion.setAccessible(true);
            Object objectVersion = fieldVersion.get(this);

            fieldLastVersion.set(objectWrapper, objectVersion);
        }
    }

    private static class ObserverWrapper<T> implements Observer<T> {

        private Observer<T> mObserver;

        public ObserverWrapper(Observer<T> observer) {
            this.mObserver = observer;
        }
        @Override
        public void onChanged(T t) {
            if (mObserver != null) {
                if (isCallOnObserve()) {
                    return;
                }
                mObserver.onChanged(t);
            }
        }

        private boolean isCallOnObserve() {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            if (stackTraceElements.length > 0) {
                for (StackTraceElement element : stackTraceElements) {
                    if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
                            "observeForever".equals(element.getMethodName())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
