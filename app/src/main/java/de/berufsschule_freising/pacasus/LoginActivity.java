package de.berufsschule_freising.pacasus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

import de.berufsschule_freising.pacasus.model.dataContext.DataContext;
import de.berufsschule_freising.pacasus.model.entity.User;

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

		// Fill Database with Inital User
		User user = new User();
		user.setID(1);
		user.setName("admin");
		user.setPassword("123");
		DataContext db = new DataContext(this);
		db.addUser(user);
	}

	public void onClick(View v){
		// TODO: Mit DB abfragen
		DataContext db = new DataContext(this);
		ArrayList<User> userList = db.fetchUser();
		// System.out.println(userList.size());
		// Toast.makeText(this, userList.get(0).getName(), Toast.LENGTH_LONG).show();

		for (User user : userList){
			if (this.txtUsername.getText().toString().equals(user.getName())){
				if (this.txtPassword.getText().toString().equals(user.getPassword())){
					Toast.makeText(this, "Super! Login erfolgreich :D", Toast.LENGTH_LONG).show();
					Intent mainIntent = new Intent(this, MainActivity.class);
					this.startActivity(mainIntent);
                    return;
				}else {
					Toast.makeText(this, "Falsche Zugangsdaten; Passwort ist falsch!", Toast.LENGTH_LONG).show();
				}
			} else {
				continue;
			}
		}

		Toast.makeText(this, "Falsche Zugangsdaten; Benutzername ist falsch oder unbekannt!", Toast.LENGTH_LONG).show();
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
