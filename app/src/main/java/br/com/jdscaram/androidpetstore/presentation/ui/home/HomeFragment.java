package br.com.jdscaram.androidpetstore.presentation.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import javax.inject.Inject;

import br.com.jdscaram.androidpetstore.App;
import br.com.jdscaram.androidpetstore.R;
import br.com.jdscaram.androidpetstore.dagger.components.DaggerHomeComponent;
import br.com.jdscaram.androidpetstore.dagger.components.HomeComponent;
import br.com.jdscaram.androidpetstore.dagger.modules.HomeModule;
import br.com.jdscaram.androidpetstore.presentation.presenter.home.HomeInteractorView;
import br.com.jdscaram.androidpetstore.presentation.presenter.home.HomePresenterImpl;
import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;
import br.com.jdscaram.androidpetstore.services.animals.bean.Tags;

/**
 * {Created by Jonatas Caram on 29/05/2017}.
 */
public class HomeFragment extends Fragment implements HomeInteractorView, SwipeRefreshLayout.OnRefreshListener, RadioGroup.OnCheckedChangeListener {

    @Inject
    HomePresenterImpl mHomePresenter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioSold, mRadioAvailable, mRadioPending;
    private String mRadioGroupSelected;


    List<PetModel> mPetModels;
    HomeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        mRadioSold = (RadioButton) view.findViewById(R.id.radio_sold);
        mRadioAvailable = (RadioButton) view.findViewById(R.id.radio_available);
        mRadioPending = (RadioButton) view.findViewById(R.id.radio_pending);

        HomeComponent mDaggerHome = DaggerHomeComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();

        mDaggerHome.inject(this);

        afterViews();
        return view;
    }

    private void afterViews() {
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioPending.setChecked(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefresh.setRefreshing(false);
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
    public void showErrorMessage(String msg) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage(msg).create().show();
    }

    @Override
    public void showHome(List<PetModel> items) {
        if (items != null) {
            mPetModels = items;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mHomePresenter.getPets(mRadioGroupSelected);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton radioButton = ((RadioButton) group.findViewById(checkedId));
        if (radioButton != null)
            mRadioGroupSelected = radioButton.getText().toString().toLowerCase();

        if (mRadioGroupSelected != null)
            onRefresh();
    }


    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

        @Override
        public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_pets, parent, false);
            return new HomeAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
            PetModel item = mPetModels.get(position);
            holder.name.setText(item.getMName() != null ? item.getMName() : "Name : NULL");
            holder.category.setText(item.getMCategory() != null ? item.getMCategory().getMName() : "Name : NULL");
            holder.available.setText(item.getMStatus() != null ? item.getMStatus() : "Status : NULL");

            if (item.getMPhotourls().length > 0)
                holder.photo.setImageURI(Uri.parse(item.getMPhotourls()[0]));

            String tags = "";
            if (item.getMTags() != null) {
                if (item.getMTags().size() != 0) {
                    for (Tags tag : item.getMTags()) {
                        if (tag.getMName() != null)
                            tags = tags.concat(" ").concat(tag.getMName());
                    }
                    holder.tags.setText(tags);
                }
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
