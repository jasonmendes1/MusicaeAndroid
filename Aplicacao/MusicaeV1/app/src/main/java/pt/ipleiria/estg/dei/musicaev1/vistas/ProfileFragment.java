package pt.ipleiria.estg.dei.musicaev1.vistas;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.dei.musicaev1.R;
import pt.ipleiria.estg.dei.musicaev1.modelos.Perfil;
import pt.ipleiria.estg.dei.musicaev1.modelos.Singleton;

public class ProfileFragment extends Fragment {

    private TextView tvUsername, tvId, tvEmail;
    private EditText etNome, etSexo,etLocalidade, etDtaNasc, etInstrumento, etGenero, etDescricao;
    private Perfil perfil;
    private SharedPreferences sharedPreferences;
    private Button buttonEditar;
    private int IdUser;
    private RequestQueue mQueue;
    private String urlAPI;
    private String ipURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mQueue = Volley.newRequestQueue(getContext());

        tvUsername = rootView.findViewById(R.id.tvUsernamePerfil);
        tvId = rootView.findViewById(R.id.tvIDPerfil);
        tvEmail = rootView.findViewById(R.id.tvEmailPerfil);
        etNome = rootView.findViewById(R.id.etNomeProfile);
        etSexo = rootView.findViewById(R.id.etSexoProfile);
        etLocalidade = rootView.findViewById(R.id.etLocalidadeProfile);
        etDtaNasc = rootView.findViewById(R.id.etDtaNascProfile);
        etInstrumento = rootView.findViewById(R.id.etInstrumentoProfile);
        etGenero = rootView.findViewById(R.id.etGeneroProfile);
        etDescricao = rootView.findViewById(R.id.etDescricaoProfile);


        ipURL = Singleton.getInstance(getContext()).getIp();
        urlAPI = "http://" + ipURL + "/MusicaeWeb/backend/web/v1/user/profile";

        // NÃO ESQUECER DE METER O IDUSER A FUNCIONAR
        IdUser = Singleton.getInstance(getContext()).getIdUser();
        System.out.println("--> url perfilAPI: "+ urlAPI + "/" + IdUser);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlAPI + "/" + IdUser, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("-->RESPOSTA ");
                        try {
                            JSONObject profile = response.getJSONObject(0);
                            System.out.println("--> profile: " + profile.toString());

                            String UserUsername = profile.getString("UserUsername");
                            String UserEmail = profile.getString("UserEmail");
                            String ProfileNome = profile.getString("ProfileNome");
                            String ProfileSexo = profile.getString("ProfileSexo");
                            String ProfileLocalidade = profile.getString("ProfileLocalidade");
                            String ProfileDataNasc = profile.getString("ProfileDataNasc");
                            String ProfileDescricao = profile.getString("ProfileDescricao");
                            String HabilidadeNome = profile.getString("HabilidadeNome");
                            String GeneroNome = profile.getString("GeneroNome");
                            String ProfileFoto = profile.getString("ProfileFoto");

                            tvUsername.setText(UserUsername);
                            tvId.setText("#" + IdUser);
                            tvEmail.setText(UserEmail);
                            etNome.setText(ProfileNome);
                            etSexo.setText(ProfileSexo);
                            etLocalidade.setText(ProfileLocalidade);
                            etDtaNasc.setText(ProfileDataNasc);
                            etDescricao.setText(ProfileDescricao);
                            etInstrumento.setText(HabilidadeNome);
                            etGenero.setText(GeneroNome);
                            System.out.println("--> tvUsername: " + tvUsername.getText());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

        buttonEditar = rootView.findViewById(R.id.btnEditProfile);
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return rootView;
    }

    private void mostrarPerfil(Perfil perfil) {
        tvUsername.setText(perfil.getNome());
        tvId.setText(perfil.getId());
        etNome.setText(perfil.getNome());
        etDtaNasc.setText(perfil.getDatanasc());
    }

    private Perfil editarPerfil() {
        perfil.setNome(perfil.getNome());
        perfil.setSexo(perfil.getSexo());
        perfil.setLocalidade(perfil.getLocalidade());
        perfil.setDatanasc(perfil.getDatanasc());

        return perfil;
    }

}