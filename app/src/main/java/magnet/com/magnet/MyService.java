package magnet.com.magnet;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import magnet.com.magnet.library.IconCallback;
import magnet.com.magnet.library.Magnet;

//public class MyService extends Service
//{
//    private ServiceBinder serviceBinder;
//
//    @Override
//    public IBinder onBind(Intent intent)
//    {
//        if (serviceBinder == null)
//        {
//            serviceBinder = new ServiceBinder(this);
//        }
//        return serviceBinder;
//    }
//
//    @Override
//    public void onDestroy()
//    {
//        serviceBinder.destroy();
//        serviceBinder = null;
//        super.onDestroy();
//    }
//
//    interface IconService
//    {
//        void startMagnet();
//        void stopMagnet();
//    }

    /*static*/ class MyService extends Service implements IconCallback//, IconService
    {

        private static final String TAG = "Magnet";
        private Magnet magnet;
//        private final Context context;

//        MyService(Context context)
//        {
//            this.context = context;
//        }

        @Override
        public void onCreate()
        {
            super.onCreate();

            startMagnet();
        }

        void destroy()
        {
            if (magnet != null)
            {
                magnet.destroy();
            }
        }

//        @Override
        public void startMagnet()
        {
            if (magnet == null)
            {
                magnet = Magnet.newBuilder(MyService.this)
                        .setIconView(R.layout.logo_float_layout)
                        .setIconCallback(this)
                        .setHideFactor(0.2f)
                        .setShouldShowRemoveView(true)
                        .setRemoveIconResId(R.drawable.ic_close)
                        .setRemoveIconShadow(R.drawable.bottom_shadow)
                        .setShouldStickToWall(true)
                        .setRemoveIconShouldBeResponsive(true)
                        .setInitialPosition(100, 200)
                        .build();
                magnet.show();
            }
        }

//        @Override
        public void stopMagnet()
        {
            if (magnet != null)
            {
                magnet.destroy();
                magnet = null;
            }
        }

        @Override
        public void onFlingAway()
        {
            Log.i(TAG, "onFlingAway");
            if (magnet != null)
            {
                magnet.destroy();
                magnet = null;
            }
        }

        @Override
        public void onMove(float x, float y)
        {
            Log.i(TAG, "onMove(" + x + "," + y + ")");
        }

        @Override
        public void onIconClick(View icon, float iconXPose, float iconYPose)
        {
            Log.i(TAG, "onIconClick(..)");
//            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onIconDestroyed()
        {
            Log.i(TAG, "onIconDestroyed()");
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent)
        {
            return null;
        }
    }
//}
