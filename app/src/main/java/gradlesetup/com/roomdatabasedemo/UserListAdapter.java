package gradlesetup.com.roomdatabasedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by appinventiv on 28/12/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserData> userList;
    private RecyclerItemClick recyclerItemClick;

    public UserListAdapter(Context context, ArrayList<UserData> userList, RecyclerItemClick recyclerItemClick) {
        this.context = context;
        this.userList = userList;
        this.recyclerItemClick = recyclerItemClick;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvuserId.setText(userList.get(position).getUserId());
        holder.tvUserName.setText(userList.get(position).getUserName());
        holder.tvUserCity.setText(userList.get(position).getUserCity());
        holder.tvUserPhone.setText(userList.get(position).getUserPhone());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvuserId,tvUserName, tvUserCity, tvUserPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvuserId = itemView.findViewById(R.id.tv_user_id);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserCity = itemView.findViewById(R.id.tv_city);
            tvUserPhone = itemView.findViewById(R.id.tv_phone);
            itemView.setOnLongClickListener(view -> {
                recyclerItemClick.onItemClick(getAdapterPosition());
                return true;
            });
        }
    }
}
