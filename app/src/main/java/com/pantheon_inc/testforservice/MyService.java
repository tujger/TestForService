package com.pantheon_inc.testforservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    private ServiceBinder binder = new ServiceBinder();
    private int id;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("Service:onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = startId;
        String mode = intent.getStringExtra("mode");
        System.out.println("Service:onStartCommand:"+startId+":"+mode);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        String mode = intent.getStringExtra("mode");
        System.out.println("Service:onBind:"+mode);
        return binder;
    }

    class ServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }


    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("Service:onUnbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        System.out.println("Service:onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        System.out.println("Service:onTaskRemoved");
    }

    public int getId(){
        return id;
    }
}
