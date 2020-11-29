package com.example.simpletodotest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



    public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
        ArrayList<Items> mItems = new ArrayList<>();
        OnLongClickListener longClickListener;
        CheckBox mTextCheckBox;
        CalendarView mToDueDate;
        TextView dpDueDate;
        TextView mTaskList;
        TextView mPriority;
        TextView tvItem;
        Button btnUpdated;
        private OnItemClickListener mListener;
        View.OnClickListener clickListener;
        Context mContext;



        public interface OnItemClickListener {
            void onItemClicked(int position);
            void onUpdateClick(int position);
            void onDeleteClick(int position);
            void onCompletedClick(int position);


        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }


        public interface OnLongClickListener {
            void onItemLongClicked(int position);


        }



        public ItemsAdapter(ArrayList<Items> toDoItems) {
            this.mItems = toDoItems;
            this.longClickListener = longClickListener;
            this.clickListener = clickListener;

        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public CheckBox mTextCheckBox;
            TextView mTaskList;
            TextView dpDueDate;
            TextView mPriority;
            TextView tvItem;
            public final Button btnUpdated;
            public ImageView mDeleteIcon;






            public ViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
                super(itemView);
                mTextCheckBox = (CheckBox) itemView.findViewById(R.id.checkBoxView);
                mTaskList = (TextView) itemView.findViewById(R.id.taskListTextView);
                dpDueDate = (TextView) itemView.findViewById(R.id.categoryTextView);
                mPriority = (TextView) itemView.findViewById(R.id.priorityTextView);
                tvItem = (TextView) itemView.findViewById(android.R.id.text1);
                btnUpdated = (Button) itemView.findViewById(R.id.updateBtn);
                mDeleteIcon = (ImageView) itemView.findViewById(R.id.imageViewDelete);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                itemClickListener.onItemClicked(position);
                            }

                        }
                    }
                });
                btnUpdated.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                itemClickListener.onUpdateClick(position);
                            }

                        }
                    }
                });
                mDeleteIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                itemClickListener.onDeleteClick(position);
                            }

                        }
                    }
                });
                mTextCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                itemClickListener.onCompletedClick(position);
                            }

                        }
                    }
                });
            }


        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list_item, parent, false);
            return new ViewHolder(todoView, mListener);
        }



        @Override
        public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
            //holder.btnUpdated.setActivated(true);
            holder.mTaskList.setText(mItems.get(position).getName());
            holder.dpDueDate.setText("Due date: " + mItems.get(position).getDate());
            holder.mPriority.setText(mItems.get(position).getPriority());
            //holder.mTextCheckBox.setActivated(true);
            //Items item = mItems.get(position);



        }

        //Tells RV how many items are in the list.
        @Override
        public int getItemCount() {
            return mItems.size();
        }


            public void bind(Items itemz) {
                mTaskList.setText(itemz.getName());
                dpDueDate.setText("Due date: " + itemz.getDate());
                mPriority.setText(itemz.getPriority());

        }
            }





