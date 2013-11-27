package org.ejmc.android.simplechat;
import  org.ejmc.android.simplechat.model.*;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
//import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends ListActivity {
    /** Called when the activity is first created. */

    private EditText editor;
    private ImageButton boton;
    private TextView nombre;
    private String nick;
    private ArrayList<Message> listado;
    private Adaptador adaptador;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        nick = getIntent().getExtras().getString("nick");
        nombre=(TextView) findViewById(R.id.nick);
        nombre.setText("¡¡¡Bienvenido "+nick+"!!!");
        editor = (EditText) findViewById(R.id.editTextMensaje);
        boton = (ImageButton) findViewById(R.id.sendButton);

        listado =
                new ArrayList<Message>();

        listado.add(
                new Message("Nombre 1", "Hola"));

        listado.add(
                new Message("Nombre 2", "Que pasa?"));

        listado.add(
                new Message("Nombre 3", "Aqui estamos"));

        listado.add(
                new Message("Nombre 4", "Ok"));

        adaptador = new Adaptador(ChatActivity.this, listado);
        //setListAdapter((ListAdapter) adaptador);
        recargar();
        lv=(ListView)this.getListView();






        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editor.getText().toString();
                if( hayCaracter(msg)){//lo envio si no no
                    Message m= listado.get(listado.size()-1);
                    if(m.getNombre().equals(nick)){
                        String lastmessage=  listado.get(listado.size()-1).getMensaje();
                        //listado.get(listado.size()).setMensaje(listado.get(listado.size()).getMensaje()+"/n"+msg);
                        listado.remove(listado.size()-1);
                        listado.add(new Message(nick, lastmessage+"\n"+msg));

                    } else{
                        listado.add(new Message(nick, msg));
                    }


                    recargar();
                    lv.setSelection(lv.getAdapter().getCount()-1);
                }
                editor.setText("");
            }
        });

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read values from the "savedInstanceState"-object and put them in your textview

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the values you need from your textview into "outState"-object
        super.onSaveInstanceState(outState);
    }

    public boolean hayCaracter( String cadena ){
        for(int i=0; i<cadena.length()-1; i++){
            if(cadena.charAt(i)!=' ' && cadena.charAt(i)!='\n' && cadena.charAt(i)!='\t' )//tiene algun caracter distinto de ' '
                return true;
        }
        return false;
    }

    public void recargar(){

        //adaptador = new Adaptador(
        //        Chat.this, listado);
        setListAdapter((ListAdapter) adaptador);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().hide();
	}

}
