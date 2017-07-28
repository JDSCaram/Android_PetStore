package br.com.jdscaram.calstivelmvp.presentation.ui.search;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import javax.inject.Inject;

import br.com.jdscaram.calstivelmvp.App;
import br.com.jdscaram.calstivelmvp.R;
import br.com.jdscaram.calstivelmvp.dagger.components.DaggerSearchComponent;
import br.com.jdscaram.calstivelmvp.dagger.components.SearchComponent;
import br.com.jdscaram.calstivelmvp.dagger.modules.SearchModule;
import br.com.jdscaram.calstivelmvp.presentation.presenter.search.SearchInteractorView;
import br.com.jdscaram.calstivelmvp.presentation.presenter.search.SearchPresenter;
import br.com.jdscaram.calstivelmvp.presentation.presenter.search.SearchPresenterImpl;
import br.com.jdscaram.calstivelmvp.services.DataManager;
import br.com.jdscaram.calstivelmvp.services.animals.bean.PetModel;
import br.com.jdscaram.calstivelmvp.services.animals.bean.Tags;

/**
 * {Created by Jonatas Caram on 29/05/2017}.
 */
public class SearchFragment extends Fragment implements SearchInteractorView {

    @Inject
    SearchPresenterImpl mPresenter;

    private RecyclerView mRecyclerView;
    private EditText mInputPetId;
    private ShimmerFrameLayout mShimmerFrameLayout;

    List<PetModel> mPetModels;
    HistoryAdapter mAdapter;
    private long mCurrentId = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mInputPetId = (EditText) view.findViewById(R.id.input_pet_id);
        mShimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);

        SearchComponent mDaggerSearch = DaggerSearchComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .searchModule(new SearchModule(this))
                .build();
        mDaggerSearch.inject(this);

        afterViews();
        return view;
    }

    private void afterViews() {
        mPresenter.setView(this);
        mPresenter.getPet(mCurrentId);
        mInputPetId.setText(String.valueOf(mCurrentId));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new HistoryAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mInputPetId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.cancelRequest();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mInputPetId.getText().length() > 0) {
                    mCurrentId = Long.parseLong(s.toString());
                    mPresenter.getPet(mCurrentId);
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        mShimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void hideProgress() {
        mShimmerFrameLayout.stopShimmerAnimation();
    }

    @Override
    public void showMessage(String message) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage(message != null ? message : "").create().show();
    }

    @Override
    public void showErrorMessage() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Error").create().show();
    }

    @Override
    public void showHistory(List<PetModel> items) {
        if (items != null) {
            mPetModels = items;
            mAdapter.notifyDataSetChanged();
        }
    }


    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_pets, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            PetModel item = mPetModels.get(position);
            holder.name.setText(item.getMName() != null ? item.getMName() : "Name : NULL");
            holder.category.setText(item.getMCategory() != null ? item.getMCategory().getMName() : "Name : NULL");
            holder.available.setText(item.getMStatus() != null ? item.getMStatus() : "Status : NULL");

            if (item.getMPhotourls().length > 0)
                holder.photo.setImageURI(Uri.parse(item.getMPhotourls()[0]));

            String tags = "";
            if (item.getMTags().size() != 0) {
                for (Tags tag : item.getMTags()) {
                    tags = tags.concat(" ").concat(tag.getMName());
                }
                holder.tags.setText(tags);
            }

        }

        @Override
        public int getItemCount() {
            return mPetModels != null ? mPetModels.size() : 0;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            SimpleDraweeView photo;
            TextView name, available, tags, category;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                category = (TextView) itemView.findViewById(R.id.category);
                available = (TextView) itemView.findViewById(R.id.available);
                photo = (SimpleDraweeView) itemView.findViewById(R.id.photo);
                tags = (TextView) itemView.findViewById(R.id.tags);
            }
        }
    }

}
