package br.com.jdscaram.calstivelmvp.presentation.ui.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import br.com.jdscaram.calstivelmvp.App;
import br.com.jdscaram.calstivelmvp.R;
import br.com.jdscaram.calstivelmvp.dagger.components.DaggerRegisterComponent;
import br.com.jdscaram.calstivelmvp.dagger.components.RegisterComponent;
import br.com.jdscaram.calstivelmvp.dagger.modules.RegisterModule;
import br.com.jdscaram.calstivelmvp.presentation.presenter.register.RegisterInteractorView;
import br.com.jdscaram.calstivelmvp.presentation.presenter.register.RegisterPresenterImpl;
import br.com.jdscaram.calstivelmvp.services.animals.bean.Category;
import br.com.jdscaram.calstivelmvp.services.animals.bean.PetModel;
import br.com.jdscaram.calstivelmvp.services.animals.bean.Tags;
import br.com.jdscaram.calstivelmvp.toolbox.Constants;

/**
 * {Created by Jonatas Caram on 29/05/2017}.
 */
public class RegisterFragment extends Fragment implements RegisterInteractorView, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Inject
    RegisterPresenterImpl mRegisterPresenter;

    private static final int SELECT_FILE = 3566;
    private static final int REQUEST_CAMERA = 3565;

    private View mView;
    private Spinner mSpinner;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private EditText mName, mTag;
    private ImageButton mAttach;
    private TextView mTextUrl;
    private Button mBtRegister;
    private Bitmap mBitmap;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioSold, mRadioAvailable, mRadioPending;
    private String mCategorySelected, mRadioGroupSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mView = view.findViewById(R.id.layout);
        mSpinner = (Spinner) view.findViewById(R.id.category);
        mName = (EditText) view.findViewById(R.id.name);
        mTag = (EditText) view.findViewById(R.id.tag);
        mAttach = (ImageButton) view.findViewById(R.id.attach);
        mTextUrl = (TextView) view.findViewById(R.id.text_url);
        mBtRegister = (Button) view.findViewById(R.id.register);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        mRadioSold = (RadioButton) view.findViewById(R.id.radio_sold);
        mRadioAvailable = (RadioButton) view.findViewById(R.id.radio_available);
        mRadioPending = (RadioButton) view.findViewById(R.id.radio_pending);

        RegisterComponent mDaggerRegister = DaggerRegisterComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .registerModule(new RegisterModule(this))
                .build();
        mDaggerRegister.inject(this);

        afterViews(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRegisterPresenter.onDestroy();
    }

    private void afterViews(View view) {
        String[] categories = getResources().getStringArray(R.array.categories_pets);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_dropdown_item, categories) {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Deixa o hint com a cor cinza ( efeito de desabilitado)
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategorySelected = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner.setAdapter(adapter);

        mRadioGroup.setOnCheckedChangeListener(this);

        mAttach.setOnClickListener(this);
        mBtRegister.setOnClickListener(this);

    }

    @Override
    public void showProgress() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {
        mAlertDialog = new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void showErrorMessage() {
        mAlertDialog = new AlertDialog.Builder(getContext())
                .setMessage(getString(R.string.error_empty_field))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void receiveImageURL(String url) {
        if (url != null)
            mTextUrl.setText(url);
    }

    @Override
    public void showRegisterCallback() {
        showMessage(getString(R.string.register_success));
        mName.setText("");
        mTag.setText("");
        mSpinner.setSelection(0);
        mAttach.setImageDrawable(ContextCompat.getDrawable(getContext(),(R.drawable.ic_picture)));
        mTextUrl.setText(getString(R.string.select_photo));
        mRadioGroup.clearCheck();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attach:
                selectImage();
                break;
            case R.id.register:
                if (validForms())
                    register();
                else
                    showErrorMessage();
                break;
        }
    }

    private void register() {
        PetModel petRequest = new PetModel();
        petRequest.setMId((long) (Math.random() * 10)); //fake ID
        petRequest.setMCategory(new Category((long) (Math.random() * 100), mCategorySelected)); //fake ID
        petRequest.setMName(mName.getText().toString());
        petRequest.setMPhotourls(new String[]{mTextUrl.getText().toString()});
        petRequest.setMStatus(mRadioGroupSelected);
        petRequest.setMTags(new ArrayList<Tags>() {{
            add(new Tags((long) (Math.random() * 10), mTag.getText().toString()));
        }});

        mRegisterPresenter.sendRegister(petRequest);
    }

    private boolean validForms() {
        if (TextUtils.isEmpty(mName.getText().toString())) {
            mName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mTag.getText().toString())) {
            mTag.requestFocus();
            return false;
        }
        if (mCategorySelected == null || mCategorySelected.equals(getString(R.string.genre)))
            return false;

        if (TextUtils.isEmpty(mTextUrl.getText().toString()))
            return false;

        if (mRadioGroup.getCheckedRadioButtonId() == -1)
            return false;

        return true;

    }

    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.choose_photo));
        builder.setPositiveButton(getString(R.string.camera), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cameraIntent();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.gallery), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                galleryIntent();
                dialog.dismiss();
            }
        });
        builder.setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        mBitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(Constants.TAG, e.getMessage());
            e.printStackTrace();
        }

        mAttach.setImageBitmap(mBitmap);

        String img = getStringImage(mBitmap);

        Log.v(Constants.TAG, "base64" + img); //perfectly string base64 to image
        sendImgToServer(img);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getActivity().
                        getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mAttach.setImageBitmap(mBitmap);
        String img = getStringImage(mBitmap);

        Log.v(Constants.TAG, "base64" + img);//checking in log base64 string to image cropeed image found
        sendImgToServer(img);
    }

    private void sendImgToServer(String img) {
        if (img != null)
            mRegisterPresenter.uploadImage(img);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton radioButton = ((RadioButton) group.findViewById(checkedId));
        if (radioButton != null)
            mRadioGroupSelected = radioButton.getText().toString().toLowerCase();
    }
}
