package com.pantheon_inc.testforservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private MyServiceConnection serviceConnection = new MyServiceConnection();
    private boolean serviceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("MainActivity:onCreate");

        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this, MyService.class);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainActivity:clickStart");
                intent.putExtra("mode","start");
                startService(intent);
            }
        });

        findViewById(R.id.bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainActivity:clickBind");

                if(!serviceBound) bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainActivity:clickStop");
                intent.putExtra("mode","stop");
                stopService(intent);

            }
        });

        findViewById(R.id.another).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainActivity:clickAnother");
                intent.putExtra("mode","another");
                startService(intent);

            }
        });

        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainActivity:clickExit");
                finish();
            }
        });

        findViewById(R.id.unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainActivity:clickUnbind");
                if(serviceBound){
                    unbindService(serviceConnection);
                    serviceBound = false;
                }
            }
        });
    }


    class MyServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, final IBinder binder) {
            serviceBound = true;
            System.out.println("MainActivity:onServiceConnected");
            findViewById(R.id.getid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("MainActivity:clickGetid:"+((MyService.ServiceBinder) binder).getService().getId());
                }
            });
        }

        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
            System.out.println("MainActivity:onServiceDisconnected");
            findViewById(R.id.getid).setOnClickListener(null);
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity:onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity:onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity:onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity:onStop");
    }
    @Override
    protected void onDestroy() {
        if(serviceBound){
            unbindService(serviceConnection);
            serviceBound = false;
        }
        super.onDestroy();
        System.out.println("MainActivity:onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("MainActivity:onRestart");
    }


}
