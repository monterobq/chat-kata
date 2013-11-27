package org.ejmc.android.simplechat;
import android.os.AsyncTask;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import  org.ejmc.android.simplechat.model.*;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
//import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.ejmc.android.simplechat.net.JSONparser;
import org.ejmc.android.simplechat.net.Rest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

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
    private int seqNumber=0;



    //URL to get JSON Array
    //private static String url = "http://10.0.2.2:8080/chat-kata/api/chat?seq=0";

    private static String url ="http://172.16.100.73:8080/chat-kata/api/chat?seq=";


    //JSON Node Names
    private static final String TAG_USER = "user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";


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
                new Message("Oye "+nick+",", "¡¡Mira los mensajes que no has leido!!"));

        /*listado.add(
                new Message("Nombre 2", "Que pasa?"));

        listado.add(
                new Message("Nombre 3", "Aqui estamos"));

        listado.add(
                new Message("Nombre 4", "Ok"));
          */
        adaptador = new Adaptador(ChatActivity.this, listado);
        //setListAdapter((ListAdapter) adaptador);
        recargar();
        lv=(ListView)this.getListView();

        //new getChat().execute();

        url+=seqNumber;

        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        /*
                        rest.get("http://google.com");
                        rest.getResponseString();
                        listado.add(
                                new Message("Nombre 4", rest.getResponseText()));
                        recargar();
                                             */

                        new getChat().execute();

                    }
                });
            }
        }, 1000, 1000);  //Timer cada segundo, ejecuto el run










        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editor.getText().toString();
                if (hayCaracter(msg)) {//lo envio si no no
                    Message m = listado.get(listado.size() - 1);
                    if (m.getNombre().equals(nick)) {
                        String lastmessage = listado.get(listado.size() - 1).getMensaje();
                        //listado.get(listado.size()).setMensaje(listado.get(listado.size()).getMensaje()+"/n"+msg);
                        listado.remove(listado.size() - 1);
                        listado.add(new Message(nick, lastmessage + "\n" + msg));

                    } else {
                        listado.add(new Message(nick, msg));
                    }


                    recargar();
                    lv.setSelection(lv.getAdapter().getCount() - 1);
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

    private class getChat extends AsyncTask<Void, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result=false;

                            // Creating new JSON Parser
                            JSONparser jParser = new JSONparser();

                            // Getting JSON from URL
                            JSONObject json = jParser.getJSONFromUrl(url);

                            try {
                                // Getting JSON Array(Messages) and Sequence number

                                if(seqNumber!=json.getInt("nextSeq")) {
                                result=true;
                                }else
                                result=false;







                                JSONArray arrayJS= json.getJSONArray("messages");

                                if(arrayJS.length()!=0){
                                for(int i=seqNumber;i<arrayJS.length();i++){

                                    JSONObject c = arrayJS.getJSONObject(i);

                                    listado.add(new Message(c.getString("nick"), c.getString("message")));

                                }
                                }
                                seqNumber=json.getInt("nextSeq");


                                // Storing  JSON item in a Variable


                                //Importing TextView
                                /*final TextView uid = (TextView)findViewById(R.id.uid);
                                final TextView name1 = (TextView)findViewById(R.id.name);
                                final TextView email1 = (TextView)findViewById(R.id.email);

                                //Set JSON Data in TextView
                                uid.setText(id);
                                name1.setText(name);
                                email1.setText(email);*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }












            return result;  //To change body of implemented methods use File | Settings | File Templates.
        }


        @Override
        protected void onPostExecute(Boolean result) {

            //If someone send a new message...
            if(result)
            recargar();


                /*adaptador = new Adaptador(ChatActivity.this, listado);
                setListAdapter((ListAdapter) adaptador);
                lv.setSelection(lv.getAdapter().getCount() - 1);          */

        }

    }

}
