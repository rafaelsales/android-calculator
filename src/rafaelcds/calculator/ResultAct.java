package rafaelcds.calculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultAct extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		TextView tvResultado = (TextView) findViewById(R.id.resultado_tvResultado);
		tvResultado.setText(getIntent().getExtras().getString("resultado"));
	}

}
