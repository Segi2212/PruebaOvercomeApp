package com.example.pruebaovercome;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebaovercome.ClasesYAdaptadores.AdaptadorDepartamentos;
import com.example.pruebaovercome.ClasesYAdaptadores.Departamentos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class departamento extends Fragment {

    public static ArrayList<Departamentos> departamentosArrayList = new ArrayList<>();
    AdaptadorDepartamentos adaptadorDepartamentos;
    Departamentos departamentos;
    ListView lvDepartamentos;

    public departamento() {
        // Required empty public constructor
    }

    public static departamento newInstance(String param1, String param2) {
        departamento fragment = new departamento();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_departamento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvDepartamentos = view.findViewById(R.id.lvDepartamentos);

        adaptadorDepartamentos = new AdaptadorDepartamentos(getContext(), 0, departamentosArrayList);
        lvDepartamentos.setAdapter(adaptadorDepartamentos);
        lvDepartamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                Bundle bundle = new Bundle();
                Departamentos selectd = (Departamentos) lvDepartamentos.getItemAtPosition(position);
                bundle.putString("dep", selectd.getDepartamento());
                getParentFragmentManager().setFragmentResult("opcion", bundle);
                FragmentTransaction fragmentTransactionn = getFragmentManager().beginTransaction();
                fragmentTransactionn.add(R.id.fragmentContainerView, new departamento_confirmacion(), "dep_config").commit();
                lvDepartamentos.setEnabled(false);
            }
        });
        listarDepartamentos();

    }

    public void listarDepartamentos() {
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        departamentosArrayList.clear();
                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                String dep = object.getString("Departamentos");
                                departamentos = new Departamentos(dep);
                                departamentosArrayList.add(departamentos);
                            }
                            departamentos = new Departamentos("Otro");
                            departamentosArrayList.add(departamentos);
                            adaptadorDepartamentos.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opcion", "4");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}