package com.devsteve.juego_apps.signin_up;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devsteve.juego_apps.MainActivity;
import com.devsteve.juego_apps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //declaro viarables
    private TextView Yatienescuenta;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText nombre;
    private EditText password;
    private EditText confirmPassword;
    private Button Btn_Registro;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Yatienescuenta = view.findViewById(R.id.Tv_Regis_Ya_Tienes_Cuenta);
        parentFrameLayout = getActivity().findViewById(R.id.registro_framelayout);

        email = view.findViewById(R.id.Et_Regis_Correo);
        nombre = view.findViewById(R.id.Et_Regis_Nombre);
        password = view.findViewById(R.id.Et_Regis_Contraseña);
        confirmPassword = view.findViewById(R.id.Et_Regis_ConfContraseña);

        Btn_Registro = view.findViewById(R.id.Btn_Registrar);
        progressBar = view.findViewById(R.id.Regis_ProgresBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore =  FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Yatienescuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfragment(new SignInragment());
            }
        });
        /// todo : send data to firebase

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Validacion de campos
                checkImputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkImputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkImputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkImputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Btn_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificación
                checkEmailandPassword();
            }
        });

    }

    private void setfragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkImputs(){
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(nombre.getText())){
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8){
                    if (!TextUtils.isEmpty(confirmPassword.getText())){
                        //Habilitar el boton Registrar
                        Btn_Registro.setEnabled(true);
                        Btn_Registro.setTextColor(Color.rgb(255,255,255));
                    }else{
                        Btn_Registro.setEnabled(false);
                        Btn_Registro.setTextColor(Color.argb(50,255,255,255));
                    }

                }else{
                    Btn_Registro.setEnabled(false);
                    Btn_Registro.setTextColor(Color.argb(50,255,255,255));
                }

            }else{
                Btn_Registro.setEnabled(false);
                Btn_Registro.setTextColor(Color.argb(50,255,255,255));
            }

        }else{
            //Boton inabilitado
                Btn_Registro.setEnabled(false);
                Btn_Registro.setTextColor(Color.argb(50,255,255,255));
        }
    }
    private void checkEmailandPassword(){

        Drawable errorIcon = getResources().getDrawable(R.drawable.ic_error_icon);
        errorIcon.setBounds(0,0,errorIcon.getIntrinsicWidth(),errorIcon.getIntrinsicHeight());

        if (email.getText().toString().matches(emailPattern)){
            if (password.getText().toString().equals(confirmPassword.getText().toString())){

                progressBar.setVisibility(View.VISIBLE);
                Btn_Registro.setEnabled(false);
                Btn_Registro.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    Map<Object,String > userdata = new HashMap<>();
                                    userdata.put("Nombre",nombre.getText().toString());

                                    firebaseFirestore.collection("USERS")
                                            .add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                                    if (task.isSuccessful()){
                                                        Intent myintent = new Intent(getActivity(), MainActivity.class);
                                                        startActivity(myintent);
                                                        getActivity().finish();
                                                    }else {
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        Btn_Registro.setEnabled(true);
                                                        Btn_Registro.setTextColor(Color.rgb(50,255,255));
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Btn_Registro.setEnabled(true);
                                    Btn_Registro.setTextColor(Color.rgb(50,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }else{
                confirmPassword.setError("Las contraseñas no coinciden!",errorIcon);
            }
        }else {
            email.setError("Correo Invalido!",errorIcon);
        }
    }
}