package co.rxandroid_adddeleteitem_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dharma kshetri(@dharma.kshetri@gmail.com) on 12/12/16.
 */
public class MainActivity extends AppCompatActivity {
    public static  final String TAG="RXANDROID";
    RecyclerView recyclerListView;
    UserAdapter myAdapter;
    EditText editTextName;
    public static  TextView  textViewEmptyView;
    Button buttonAdd;
    Subscription subscription;
    ProgressBar myProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating layout
        creatingLayouts();
    }
    public void creatingLayouts(){
        myProgressBar=(ProgressBar) findViewById(R.id.loader);
        textViewEmptyView = (TextView) findViewById(R.id.tvEmptyView);
        editTextName = (EditText) findViewById(R.id.nameEditText);
        buttonAdd = (Button) findViewById(R.id.addButton);
        recyclerListView=(RecyclerView) findViewById(R.id.recylerview_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new UserAdapter(this);
        recyclerListView.setAdapter(myAdapter);
    }

    //Add Button click event
    public void btnAddOnClick(View v) {
        String name = editTextName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please enter name",
                    Toast.LENGTH_SHORT).show();
        } else {
            updateList(name);
        }
    }

    // update the user using subscription
    public void updateList(String name){
        subscription=Observable.just(UserClient.getInstance().getUserList(name))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<User> users) {
                        displayUsers(users);
                    }
                });



    }

    //display the user on Adapter
    public void displayUsers(List<User> users){
        myProgressBar.setVisibility(View.GONE);
        myProgressBar.setVisibility(View.GONE);
        textViewEmptyView.setVisibility(View.GONE);
        recyclerListView.setVisibility(View.VISIBLE);
        editTextName.setText("");
        myAdapter.setData(users);
        myAdapter.notifyDataSetChanged();
    }
    //destroy the subscription or unsubsciption
    @Override
    protected void onDestroy() {

        if(subscription !=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

}
