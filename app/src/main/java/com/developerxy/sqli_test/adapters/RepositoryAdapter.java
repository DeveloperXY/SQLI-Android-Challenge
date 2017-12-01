package com.developerxy.sqli_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.adapters.base.BaseSearchAdapter;
import com.developerxy.sqli_test.adapters.base.BinderViewHolder;
import com.developerxy.sqli_test.retrofit.models.QLGitHubRepository;

import java.util.List;

public class RepositoryAdapter extends BaseSearchAdapter<RepositoryAdapter.ViewHolder, QLGitHubRepository> {

    private onRepositorySelectedListener onRepositorySelectedListener;

    public RepositoryAdapter(Context context, List<QLGitHubRepository> items) {
        super(context, items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends BinderViewHolder<QLGitHubRepository> {
        private TextView repositoryName;

        public ViewHolder(View itemView) {
            super(itemView);
            repositoryName = itemView.findViewById(R.id.repo_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRepositorySelectedListener != null)
                        onRepositorySelectedListener.onRepositorySelected(mItems.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void bind(QLGitHubRepository repository) {
            repositoryName.setText(repository.getName());
        }
    }

    public void setOnRepositorySelectedListener(RepositoryAdapter.onRepositorySelectedListener onRepositorySelectedListener) {
        this.onRepositorySelectedListener = onRepositorySelectedListener;
    }

    /**
     * A listener that notifies the host fragment if a repository was selected.
     */
    public interface onRepositorySelectedListener {
        void onRepositorySelected(QLGitHubRepository repository);
    }
}
