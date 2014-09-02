package net.flower.ixmsxms.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import net.flower.R;
import net.flower.ixmsxms.IxmsxmsConfig;
import net.flower.ixmsxms.async.EnhancedAsyncTask;
import net.flower.ixmsxms.async.SimpleAsyncTask;
import net.flower.ixmsxms.model.Child;
import net.flower.ixmsxms.model.Now;
import net.flower.ixmsxms.rest.api.ChildRestClient;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;
import java.util.logging.Logger;


@EActivity(R.layout.start_activity)
public class StartActivity extends Activity {
/*
    private static String TAG = "ixmsxms-android";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
    }
    */


    @RestService
    ChildRestClient childRestClient;

    @ViewById
    Button addChildBtn;

    @ViewById
    Button  getChildBtn;

    @Click
    void addChildBtnClicked(){
        Child childForAdd = new Child();
        childForAdd.birthDate="20140202";
        childForAdd.imageUrl="";
        childForAdd.name ="모모";
        childForAdd.sex = "F";
        childForAdd.userId = 2;
//        /Log.d("childForAdd :",""+childForAdd);

        Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show();
       new addChildAsyncTask(this, childForAdd).execute();

    }


    @Click
    void getChildBtnClicked(){
        Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show();
      //  Now test = childRestClient.getTest();
       // Toast.makeText(this, ""+test.nowId, Toast.LENGTH_SHORT).show();
       //  new getTestAsyncTask(this);
       new getChildAsyncTask(this, (long) 1).execute();
    }

    private class addChildAsyncTask extends SimpleAsyncTask<Child> {

        private Child childForAdd;

        protected addChildAsyncTask(Context context, Child childForAdd) {
            super(context);
            this.childForAdd = childForAdd;

            //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Child doInBackgroundInternal() {
            return childRestClient.addChild(childForAdd);
        }

        @Override
        protected void onTaskSucceeded(Child child) {
            Toast.makeText(context, "완료", Toast.LENGTH_LONG).show();
        }

    }

    private class getChildAsyncTask extends SimpleAsyncTask<Child> {

        private long userId;

        protected getChildAsyncTask(Context context, long userId) {
            super(context);
            this.userId = userId;
            //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Child doInBackgroundInternal() {
            return childRestClient.getChild(userId);
        }

        @Override
        protected void onTaskSucceeded(Child result) {
            Log.d("test",""+result.name);
            Toast.makeText(context, ""+result.name, Toast.LENGTH_LONG).show();
        }

        @Override
        public SimpleAsyncTask<Child> setErrorHandler(ErrorHandler errorHandler) {



            return super.setErrorHandler(errorHandler);
        }
    }

    private class getTestAsyncTask extends SimpleAsyncTask<Now> {

        protected getTestAsyncTask(Context context) {
            super(context);
            Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Now doInBackgroundInternal() {
            Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
            return childRestClient.getTest();
        }

        @Override
        protected void onTaskSucceeded(Now result) {
            Toast.makeText(context, ""+result.nowId, Toast.LENGTH_LONG).show();
        }

        @Override
        public SimpleAsyncTask<Now> setErrorHandler(ErrorHandler errorHandler) {
            return super.setErrorHandler(errorHandler);
        }
    }

}

