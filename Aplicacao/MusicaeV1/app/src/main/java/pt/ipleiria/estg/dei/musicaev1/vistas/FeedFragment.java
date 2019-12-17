package pt.ipleiria.estg.dei.musicaev1.vistas;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.musicaev1.R;
import pt.ipleiria.estg.dei.musicaev1.adaptadores.ListaFeedAdaptador;
import pt.ipleiria.estg.dei.musicaev1.modelos.Singleton;
import pt.ipleiria.estg.dei.musicaev1.modelos.FeedModel;


public class FeedFragment extends Fragment {

    private ArrayList<FeedModel> listaFeed;
    private ListView lvListaBandas;
    private SearchView searchView;
    private Button buttonFiltro, buttonNome, buttonData;
    private Integer selecionado = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        listaFeed = Singleton.getInstance(getContext()).getBandasFeed();
        lvListaBandas = rootView.findViewById(R.id.lvFeed);
        lvListaBandas.setAdapter(new ListaFeedAdaptador(getContext(), listaFeed));

        lvListaBandas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ProfileBandActivity.class);
                startActivity(intent);
            }
        });

        /*FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalhesLivro.class);
                startActivity(intent);
            }
        });*/

        buttonFiltro = rootView.findViewById(R.id.btnFiltro);
        buttonFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        buttonNome = rootView.findViewById(R.id.btnNomeFeed);
        buttonData = rootView.findViewById(R.id.btnDataFeed);

        buttonNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (selecionado){
                    case 0:
                        selecionado = 1;
                        buttonNome.setBackgroundResource(R.drawable.button_branco_selecionado);
                        Toast.makeText(getContext(), "Nome", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        selecionado = 0;
                        buttonNome.setBackgroundResource(R.drawable.button_branco);
                        buttonData.setBackgroundResource(R.drawable.button_branco);
                        Toast.makeText(getContext(), "Limpo", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        selecionado = 1;
                        buttonNome.setBackgroundResource(R.drawable.button_branco_selecionado);
                        buttonData.setBackgroundResource(R.drawable.button_branco);
                        Toast.makeText(getContext(), "Nome", Toast.LENGTH_SHORT).show();
                        break;
                        default:
                            return;
                }
            }
        });

        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (selecionado){
                    case 0:
                        selecionado = 2;
                        buttonData.setBackgroundResource(R.drawable.button_branco_selecionado);
                        Toast.makeText(getContext(), "Data", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        selecionado = 2;
                        buttonData.setBackgroundResource(R.drawable.button_branco_selecionado);
                        buttonNome.setBackgroundResource(R.drawable.button_branco);
                        Toast.makeText(getContext(), "Data", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        selecionado = 0;
                        buttonData.setBackgroundResource(R.drawable.button_branco);
                        buttonNome.setBackgroundResource(R.drawable.button_branco);
                        Toast.makeText(getContext(), "Limpo", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<FeedModel> tempListaLivros = new ArrayList<>();
                for (FeedModel tempLivro: Singleton.getInstance(getContext()).getBandasFeed()) {
                    if(tempLivro.getNome().toLowerCase().contains(newText.toLowerCase())){
                        tempListaLivros.add(tempLivro);
                    }
                }

                lvListaBandas.setAdapter(new ListaFeedAdaptador(getContext(), tempListaLivros));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        if(searchView != null){
            searchView.onActionViewCollapsed();
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemPesquisa){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
