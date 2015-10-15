package de.berufsschule_freising.pacasus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button btnLogin;
    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Elements from ActivityView
        // TODO: Gibt es eine bessere Lösung dafür??
        this.btnLogin = (Button)this.findViewById(R.id.btnLogin);
        this.txtUsername = (EditText)this.findViewById(R.id.txtUsername);
        this.txtPassword = (EditText)this.findViewById(R.id.txtPassword);
        // add Eventhandler
        this.btnLogin.setOnClickListener(this);
    }

    public void onClick(View v){
        // TODO: Mit DB abfragen
        if (this.txtUsername.getText().toString().equals("admin")){
            if (this.txtPassword.getText().toString().equals("123")){
                Toast.makeText(this, "Super! Login erfolgreich :D", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(this, MainActivity.class);
                this.startActivity(mainIntent);
            }else {
                Toast.makeText(this, "Falsche Zugangsdaten; Passwort ist falsch!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Falsche Zugangsdaten; Benutzername ist falsch!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
