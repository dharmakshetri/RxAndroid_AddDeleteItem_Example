package co.rxandroid_adddeleteitem_example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dharma kshetri(@dharma.kshetri@gmail.com) on 12/12/16.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private final Context mContext;
    private List<User> nameList = new ArrayList<>();

    public UserAdapter(Context context){
        mContext=context;
        }

    public void setData(List<User> users){
        nameList.clear();
        nameList.addAll(users);
        notifyDataSetChanged();
    }
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, final int position) {
    holder.myTextView.setText(nameList.get(position).toString());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext," "+nameList.get(position).toString(),Toast.LENGTH_SHORT).show();
            }
        });

     holder.myButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameList.remove(position);
                UserClient.getInstance().deleteUser(position);
                notifyDataSetChanged();
                if (nameList.size() == 0) {
                    MainActivity.textViewEmptyView.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public  TextView myTextView;
        public  Button myButtonDelete;
        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView=(TextView)itemView.findViewById(R.id.tvName);
            myButtonDelete=(Button)itemView.findViewById(R.id.deleteButton);
        }
    }
}
