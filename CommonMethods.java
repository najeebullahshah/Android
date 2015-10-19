package com.examble.Activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommonMethods {

	Context context;

	ProgressDialog progressDialog;

	public CommonMethods(Context context) {
		this.context = context;
	}

	@SuppressLint("NewApi")
	public boolean validateEditTextFields(ArrayList<View> fields) {
		boolean notEmpty = true;

		addTextWatch(fields);

		for (View field : fields) {

			if (field instanceof EditText) {
				EditText et = (EditText) field;
				if (et.getText().toString().length() == 0) {
					if (notEmpty)
						et.requestFocus();
					notEmpty = false;
					et.setError("Field required");
					// et.setBackgroundDrawable(contxt.getResources().getDrawable(R.drawable.listing_background_red_alert));
				} else {
					et.setError(null);
					// et.setBackgroundDrawable(contxt.getResources().getDrawable(R.drawable.listing_background_white));
				}
			} else if (field instanceof AutoCompleteTextView) {
				AutoCompleteTextView atv = (AutoCompleteTextView) field;
				if (atv.getText().toString().length() == 0) {
					if (notEmpty)
						atv.requestFocus();
					notEmpty = false;
					atv.setError("Veuillez remplir les champs requis");
					// atv.setBackgroundDrawable(contxt.getResources().getDrawable(R.drawable.listing_background_red_alert));
				} else {
					atv.setError(null);
					// atv.setBackgroundDrawable(contxt.getResources().getDrawable(R.drawable.listing_background_dark));
				}
			}

		}

		return notEmpty;
	}

	@SuppressLint("SimpleDateFormat")
	public boolean isEarlierDate(String firstDate, String secondDate)
			throws java.text.ParseException {
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			Date dateFirst = formatter.parse(firstDate);

			Date dateSecond = formatter.parse(secondDate);

			if ((dateFirst.compareTo(dateSecond) < 0)) {
				return true;
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		return false;
	}

	/* Method for checking Network Availability */
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/* Method for showing the Progress Dialog with a specific message */
	public void showProgressDialog(String message) {
		progressDialog = ProgressDialog.show(context, "", message, true);
	}

	/* Method for hiding the Progress Dialog */
	public void hideProgressDialog() {
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.hide();
			}
		}
	}

	public boolean isProgressDialog() {
		boolean isProgress = false;
		if (progressDialog.isShowing())
			isProgress = true;
		return isProgress;
	}

	/* Method for showing Toast with a specific message */
	public void showToast(String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	@SuppressLint("SimpleDateFormat")
	public long dateToMillis(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date mDate = sdf.parse(date);
			long timeInMilliseconds = mDate.getTime();
			System.out.println("Date in milli :: " + timeInMilliseconds);
			return timeInMilliseconds;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/* Method for custom font on TextView */
	public TextView getTypeFacedTextView(TextView targetTextView,
			String FONTS_LOCATION_PATH) {
		// this is a static for testing!
		Typeface face = Typeface.createFromAsset(context.getAssets(),
				FONTS_LOCATION_PATH);

		targetTextView.setTypeface(face);
		return targetTextView;
	}

	public void ActivityIntentForResult(Class<?> cls, int value) {
		Intent i = new Intent(context, cls);
		((Activity) context).startActivityForResult(i, value);
		((Activity) context).overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void ActivityIntentResultOK() {
		Intent resultIntent = new Intent();
		((Activity) context).setResult(Activity.RESULT_OK, resultIntent);
	}

	/* Method for Calling Activity Intent */
	public void ActivityIntent(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);

		context.startActivity(intent);
		((Activity) context).overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	/*
	 * Method for calling the Parent Activity by removing the state of all
	 * others
	 */
	public void ActivityIntentTop(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	void addTextWatch(ArrayList<View> views) {

		for (View view : views) {
			if (view instanceof EditText)
				((EditText) view).addTextChangedListener(new CustomTextWatcher(
						(EditText) view));
			else if (view instanceof EditText)
				((AutoCompleteTextView) view)
						.addTextChangedListener(new CustomTextWatcher(
								(AutoCompleteTextView) view));
		}

	}

	private class CustomTextWatcher implements TextWatcher {
		private EditText mEditText = null;
		private AutoCompleteTextView mATV = null;

		public CustomTextWatcher(EditText e) {
			mEditText = e;
			mEditText.setError(null);
		}

		public CustomTextWatcher(AutoCompleteTextView e) {
			mATV = e;
			mATV.setError(null);
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (s.length() > 0) {
				if (mEditText != null) {
					mEditText.setError(null);
				} else if (mATV != null) {
					mEditText.setError(null);
				}
			}
		}

	}

	/* Method for setting the User Interface of Spinner via Adapter */
	public CustomArrayAdapter<CharSequence> spinnerAdapter(String[] spinnerArray) {
		CustomArrayAdapter<CharSequence> spinnerAdapter = new CustomArrayAdapter<CharSequence>(
				context, spinnerArray);
		return spinnerAdapter;
	}

	/* Custom Adapter Class for User Interface of Spinner */
	public class CustomArrayAdapter<T> extends ArrayAdapter<T> {
		public CustomArrayAdapter(Context ctx, T[] objects) {
			super(ctx, android.R.layout.simple_spinner_item, objects);
		}

		// other constructors

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			
			TextView text = (TextView) view.findViewById(android.R.id.text1);
			text.setSingleLine(false);
			LayoutParams params = text.getLayoutParams();
			text.setPadding(10, 10, 10, 10);
			text.setGravity(Gravity.CENTER_VERTICAL);
			text.setLayoutParams(params);
			text.setTextSize(16);
			text.setMaxLines(3);
			text.setTextColor(Color.parseColor("#666e7c"));
			text.setBackgroundColor(Color.parseColor("#ECECEC"));

			return view;

		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			View view = super.getView(pos, convertView, parent);

			TextView text = (TextView) view.findViewById(android.R.id.text1);
			text.setTextColor(Color.parseColor("#000000"));
			text.setGravity(Gravity.LEFT);
			text.setTextSize(16);
			text.setSingleLine(false);

			return view;

		}

	}
	
}
