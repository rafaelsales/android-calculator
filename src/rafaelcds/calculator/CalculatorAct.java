package rafaelcds.calculator;

import java.text.DecimalFormat;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class CalculatorAct extends Activity implements OnClickListener {
	final String REGEX_OPERATORS = "(\\+)|(-)|(\\*)|(/)";
	
	private EditText etOperation;
	private Stack<String> expression = new Stack<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		etOperation = (EditText) findViewById(R.id.main_etOperation);

		TableLayout tbButtons = (TableLayout) findViewById(R.id.main_tbButtons);
		for (int i = 0; i < tbButtons.getChildCount(); i++) {
			TableRow row = (TableRow) tbButtons.getChildAt(i);
			for (int j = 0; j < row.getChildCount(); j++) {
				Button button = (Button) row.getChildAt(j);
				button.setOnClickListener(this);
			}
		}
	}

	@Override
	public void onClick(View view) {
		Button button = (Button) view;
		if (button.getId() == R.id.main_btClear) {
			expression.clear();
			etOperation.setText("");
			return;
		} else if (button.getId() == R.id.main_btAddition) {
			addOperation("+");
		} else if (button.getId() == R.id.main_btDivision) {
			addOperation("/");
		} else if (button.getId() == R.id.main_btMultiplication) {
			addOperation("*");
		} else if (button.getId() == R.id.main_btSubtraction) {
			addOperation("-");
		} else if (button.getId() == R.id.main_btResult) {
			calculate(view);
		} else {
			addNumber(button.getText().toString());
		}
		renderExpression();
	}

	private void renderExpression() {
		etOperation.getText().clear();
		for (String element : expression) {
			etOperation.getText().append(element);
		}
	}

	private void addNumber(String number) {
		if (!expression.isEmpty() && expression.peek().matches("[0-9]")) {
			expression.push(number);
		} else if (!"0".equals(number)) {
			expression.push(number);
		}
	}

	private void addOperation(String operator) {
		if (expression.isEmpty()) {
			return;
		} else {
			for (int i = 0; i < expression.size() - 1; i++) {
				if (expression.get(i).matches(REGEX_OPERATORS)) {
					return;
				}
			}
		}
		if (expression.peek().matches(REGEX_OPERATORS)) {
			expression.pop();
		}
		expression.push(operator);
	}

	private void calculate(View view) {
		if (!etOperation.getText().toString().matches("([0-9]+)(" + REGEX_OPERATORS + ")([0-9]+)")) {
			return;
		}
		String strNumber1 = "";
		String strNumber2 = "";
		String operator = null;
		for (String el : expression) {
			if (el.matches(REGEX_OPERATORS)) {
				operator = el;
			} else if (operator == null) {
				strNumber1 += el;
			} else {
				strNumber2 += el;
			}
		}
		Double result;
		Double number1 = Double.parseDouble(strNumber1);
		Double number2 = Double.parseDouble(strNumber2);
		if (operator.equals("+")) {
			result = number1 + number2;
		} else if (operator.equals("-")) {
			result = number1 - number2;
		} else if (operator.equals("*")) {
			result = number1 * number2;
		} else {
			result = number1 / number2;
		}
		
		DecimalFormat decimalFormat = new DecimalFormat("#0.000");
		Intent intent = new Intent(view.getContext(), ResultAct.class);
		intent.putExtra("resultado", decimalFormat.format(result));
		startActivity(intent);
	}

}