package com.odishakrushi.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.odishakrushi.Model.AddAnimalModel;
import com.odishakrushi.R;

import java.util.List;

public class AddAnimalAdapter extends

        RecyclerView.Adapter<AddAnimalAdapter.ViewHolder> {

    private static final String TAG = AddAnimalAdapter.class.getSimpleName();

    private Context context;

    private List<AddAnimalModel> list;

    private OnItemClickListener onItemClickListener;

    public AddAnimalAdapter(Context context, List<AddAnimalModel> list,

                    OnItemClickListener onItemClickListener) {

        this.context = context;

        this.list = list;

        this.onItemClickListener = onItemClickListener;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        EditText animalName,animal_variety,animal_numbers;

        public ViewHolder(View itemView) {

            super(itemView);
            animalName=(EditText)itemView.findViewById(R.id.animalName);
            animal_variety=(EditText)itemView.findViewById(R.id.animal_variety);
            animal_numbers=(EditText)itemView.findViewById(R.id.animal_numbers);


        }

        private void bind(final AddAnimalModel model,

                         final OnItemClickListener listener) {



            itemView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    listener.onItemClick(getLayoutPosition());

                }

            });

        }

    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.add_animal_row, parent, false);


        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {

        AddAnimalModel item = list.get(position);

   /*     holder.animalName.setText(item.getAnimalName());
        holder.animal_variety.setText(item.getAnimal_variety());
        holder.animal_numbers.setText(item.getAnimal_numbers());*/

        //Todo: Setup viewholder for item

        holder.bind(item, onItemClickListener);

    }

    @Override

    public int getItemCount() {

        return list.size();

    }

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

}
