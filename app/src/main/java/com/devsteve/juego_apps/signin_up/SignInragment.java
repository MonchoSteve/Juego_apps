package com.devsteve.juego_apps.signin_up;

import android.content.Intent;
import android.graphics.Color;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInragment newInstance(String param1, String param2) {
        SignInragment fragment = new SignInragment();
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

    //Declaro de variables
    private TextView Notienescuenta;
    private FrameLayout parentFrameLayout;
    private TextView OlvidarContraseña;
    private EditText email;
    private EditText password;
    private Button Btn_Iniciar;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_inragment, container, false);
        Notienescuenta = view.findViewById(R.id.Tv_Ini_No_tienes_cuenta);
        OlvidarContraseña = view.findViewById(R.id.Tv_Ini_Olvidar_Passwors);
        email = view.findViewById(R.id.Et_Ini_Correo);
        password = view.findViewById(R.id.Et_Ini_Password);
        Btn_Iniciar = view.findViewById(R.id.Btn_IniciarSesion);
        progressBar = view.findViewById(R.id.Ini_ProgresBar);

        parentFrameLayout = getActivity().findViewById(R.id.registro_framelayout);

        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       Notienescuenta.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               setFragment(new SignUpFragment());
           }
       });

       email.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkInputs();
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
               checkInputs();
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });

       Btn_Iniciar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               checkEmailandPassword();
           }
       });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText())){
                Btn_Iniciar.setEnabled(true);
                Btn_Iniciar.setTextColor(Color.rgb(255,255,255));
            }else {
                Btn_Iniciar.setEnabled(false);
                Btn_Iniciar.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            Btn_Iniciar.setEnabled(false);
            Btn_Iniciar.setTextColor(Color.argb(50,255,255,255));
        }
    }
    private void checkEmailandPassword() {

        if (email.getText().toString().matches(emailPattern)){
            if (password.length()>= 8){

                progressBar.setVisibility(View.VISIBLE);
                Btn_Iniciar.setEnabled(true);
                Btn_Iniciar.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent mIntent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(mIntent);
                                    getActivity().finish();
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Btn_Iniciar.setEnabled(true);
                                    Btn_Iniciar.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                Toast.makeText(getActivity(), "Correo Electronico o Contraseña Incorrectos!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "Correo Electronico o Contraseña Incorrectos!", Toast.LENGTH_SHORT).show();
        }
    }
}