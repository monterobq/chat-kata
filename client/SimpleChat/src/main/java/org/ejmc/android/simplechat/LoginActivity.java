package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity {

    private String user, pwd;

    private EditText usuario, pass;
    private Button logearse;
    private Intent paso;
    private Context este;
    private TextView error;
    private String info;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        este = this;

        usuario = (EditText) findViewById(R.id.editUsuario);
        pass = (EditText) findViewById(R.id.editPwd);
        logearse = (Button) findViewById(R.id.login);
        error = (TextView) findViewById(R.id.textView3);




        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario.setText("");
            }
        });


        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass.setText("");            }
        });





        //info="Error: ";

        logearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = usuario.getText().toString();
                pwd = pass.getText().toString();

                //if( checkPass(user, pwd) ){
                paso = new Intent(este, ChatActivity.class);
                paso.putExtra("nick", user);
                startActivity(paso);
                finish();
                /*}
                else{
                    info+="\nCredenciales incorrectas.";
                    error.setText(info);
                    error.setTextColor(Color.RED);
                } */
                info="Error: ";
            }
        });

    }

    public boolean hayCaracter( String cadena ){
        for(int i=0; i<cadena.length()-1; i++){
            if(cadena.charAt(i)!=' ' )//tiene algun caracter distinto de ' '
                return true;
        }
        return false;
    }

    public boolean checkPass(String user, String pwd ){  //ejemplo de prueba
        if( user.equals("") || !hayCaracter(user)) info+="User vacío. ";
        if( pass.equals("") || !hayCaracter(pwd)) info+="Pass vacía. ";
        if( user.equals("bq") && pwd.equals("bq")) return true;
        else return false;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
