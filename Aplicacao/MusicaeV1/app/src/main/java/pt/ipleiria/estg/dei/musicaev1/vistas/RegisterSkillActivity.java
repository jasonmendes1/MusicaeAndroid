package pt.ipleiria.estg.dei.musicaev1.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import pt.ipleiria.estg.dei.musicaev1.R;
import pt.ipleiria.estg.dei.musicaev1.modelos.Singleton;

public class RegisterSkillActivity extends AppCompatActivity {

    private ListView lvListaHabilidades;
    private List<String> listaHabilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_skill);

        lvListaHabilidades = findViewById(R.id.lvListaHabilidades);
        listaHabilidades = Singleton.getInstance(getApplicationContext()).habilidadesAPI;


        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listaHabilidades);
        lvListaHabilidades.setAdapter(arrayAdapter);

        // register onClickListener to handle click events on each item
        lvListaHabilidades.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                Intent intent = new Intent(RegisterSkillActivity.this, RegisterGenreActivity.class);
                intent.putExtra(RegisterGenreActivity.ID_HABILIDADE, position);
                startActivity(intent);
            }
        });
    }
}
