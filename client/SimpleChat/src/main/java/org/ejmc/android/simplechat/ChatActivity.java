package org.ejmc.android.simplechat;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import  org.ejmc.android.simplechat.model.*;

import android.app.ListActivity;
import android.os.Bundle;
//import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.ejmc.android.simplechat.net.JSONparser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
    private int seqNumber;
    private String msg;

    private SharedPreferences prefs;



    //URL to get JSON Array
    //private static String url = "http://10.0.2.2:8080/chat-kata/api/chat?seq=0";

    //private static String url ="http://172.16.100.73:8080/chat-kata/api/chat?seq=";


    private static String url ="http://10.0.2.2:8080/chat-kata/api/chat?seq=";

    //JSON Node Names
    private static final String TAG_USER = "user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        prefs = getSharedPreferences("Chat", Context.MODE_PRIVATE);


        seqNumber = prefs.getInt("SEQ", 0);

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
                        new getChat().execute();

                    }
                });
            }
        }, 1000, 1000);  //Timer execute GET per second










        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = editor.getText().toString();
                if (hayCaracter(msg)) {//lo envio si no no
                    Message m = listado.get(listado.size() - 1);
                    /*if (m.getNombre().equals(nick)) {
                        String lastmessage = listado.get(listado.size() - 1).getMensaje();
                        //listado.get(listado.size()).setMensaje(listado.get(listado.size()).getMensaje()+"/n"+msg);
                        listado.remove(listado.size() - 1);
                        listado.add(new Message(nick, lastmessage + "\n" + msg));

                    } else {
                        listado.add(new Message(nick, msg));
                    }   */

                    new postChat().execute();


                    recargar();
                   // lv.setSelection(lv.getAdapter().getCount() - 1);
                }
                editor.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().putInt("SEQ", seqNumber).commit();
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
        lv=(ListView)this.getListView();
        setListAdapter((ListAdapter) adaptador);
        lv.setSelection(lv.getAdapter().getCount() - 1);
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
                                if(json==null)
                                    return false;
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

    private class postChat extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute(){
            Toast.makeText(getApplicationContext(), "Sending", Toast.LENGTH_SHORT).show();

        }


        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result=false;

            String status=JSONparser.sendJSON("http://10.0.2.2:8080/chat-kata/api/chat",new Message(nick, msg));

            if(status.equals("OK"))
                result=true;

            return result;
        }

        @Override
        	    protected void onPostExecute(Boolean result) {
            //Show status to the user

            if(result)
            Toast.makeText(getApplicationContext(), "Message has been sent", Toast.LENGTH_SHORT).show();
            else
            Toast.makeText(getApplicationContext(), "Couldn't been sent. Try again", Toast.LENGTH_SHORT).show();

        }
    }











}
