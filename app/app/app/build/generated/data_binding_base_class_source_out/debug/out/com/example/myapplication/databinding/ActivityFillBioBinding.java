// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityFillBioBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton buttonGoback;

  @NonNull
  public final Button buttonNext;

  @NonNull
  public final EditText edittextAddress;

  @NonNull
  public final EditText edittextCity;

  @NonNull
  public final EditText edittextDistrict;

  @NonNull
  public final EditText edittextName;

  @NonNull
  public final EditText edittextPhone;

  @NonNull
  public final LinearLayout fillBio;

  @NonNull
  public final Spinner spinnerGender;

  @NonNull
  public final TextView textAddress;

  @NonNull
  public final TextView textCity;

  @NonNull
  public final TextView textDistrict;

  @NonNull
  public final TextView textGender;

  @NonNull
  public final TextView textName;

  @NonNull
  public final TextView textPhone;

  @NonNull
  public final TextView textSignup;

  private ActivityFillBioBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageButton buttonGoback, @NonNull Button buttonNext,
      @NonNull EditText edittextAddress, @NonNull EditText edittextCity,
      @NonNull EditText edittextDistrict, @NonNull EditText edittextName,
      @NonNull EditText edittextPhone, @NonNull LinearLayout fillBio,
      @NonNull Spinner spinnerGender, @NonNull TextView textAddress, @NonNull TextView textCity,
      @NonNull TextView textDistrict, @NonNull TextView textGender, @NonNull TextView textName,
      @NonNull TextView textPhone, @NonNull TextView textSignup) {
    this.rootView = rootView;
    this.buttonGoback = buttonGoback;
    this.buttonNext = buttonNext;
    this.edittextAddress = edittextAddress;
    this.edittextCity = edittextCity;
    this.edittextDistrict = edittextDistrict;
    this.edittextName = edittextName;
    this.edittextPhone = edittextPhone;
    this.fillBio = fillBio;
    this.spinnerGender = spinnerGender;
    this.textAddress = textAddress;
    this.textCity = textCity;
    this.textDistrict = textDistrict;
    this.textGender = textGender;
    this.textName = textName;
    this.textPhone = textPhone;
    this.textSignup = textSignup;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFillBioBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFillBioBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_fill_bio, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFillBioBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_goback;
      ImageButton buttonGoback = ViewBindings.findChildViewById(rootView, id);
      if (buttonGoback == null) {
        break missingId;
      }

      id = R.id.button_next;
      Button buttonNext = ViewBindings.findChildViewById(rootView, id);
      if (buttonNext == null) {
        break missingId;
      }

      id = R.id.edittext_address;
      EditText edittextAddress = ViewBindings.findChildViewById(rootView, id);
      if (edittextAddress == null) {
        break missingId;
      }

      id = R.id.edittext_city;
      EditText edittextCity = ViewBindings.findChildViewById(rootView, id);
      if (edittextCity == null) {
        break missingId;
      }

      id = R.id.edittext_district;
      EditText edittextDistrict = ViewBindings.findChildViewById(rootView, id);
      if (edittextDistrict == null) {
        break missingId;
      }

      id = R.id.edittext_name;
      EditText edittextName = ViewBindings.findChildViewById(rootView, id);
      if (edittextName == null) {
        break missingId;
      }

      id = R.id.edittext_phone;
      EditText edittextPhone = ViewBindings.findChildViewById(rootView, id);
      if (edittextPhone == null) {
        break missingId;
      }

      id = R.id.fill_bio;
      LinearLayout fillBio = ViewBindings.findChildViewById(rootView, id);
      if (fillBio == null) {
        break missingId;
      }

      id = R.id.spinner_gender;
      Spinner spinnerGender = ViewBindings.findChildViewById(rootView, id);
      if (spinnerGender == null) {
        break missingId;
      }

      id = R.id.text_address;
      TextView textAddress = ViewBindings.findChildViewById(rootView, id);
      if (textAddress == null) {
        break missingId;
      }

      id = R.id.text_city;
      TextView textCity = ViewBindings.findChildViewById(rootView, id);
      if (textCity == null) {
        break missingId;
      }

      id = R.id.text_district;
      TextView textDistrict = ViewBindings.findChildViewById(rootView, id);
      if (textDistrict == null) {
        break missingId;
      }

      id = R.id.text_gender;
      TextView textGender = ViewBindings.findChildViewById(rootView, id);
      if (textGender == null) {
        break missingId;
      }

      id = R.id.text_name;
      TextView textName = ViewBindings.findChildViewById(rootView, id);
      if (textName == null) {
        break missingId;
      }

      id = R.id.text_phone;
      TextView textPhone = ViewBindings.findChildViewById(rootView, id);
      if (textPhone == null) {
        break missingId;
      }

      id = R.id.text_signup;
      TextView textSignup = ViewBindings.findChildViewById(rootView, id);
      if (textSignup == null) {
        break missingId;
      }

      return new ActivityFillBioBinding((RelativeLayout) rootView, buttonGoback, buttonNext,
          edittextAddress, edittextCity, edittextDistrict, edittextName, edittextPhone, fillBio,
          spinnerGender, textAddress, textCity, textDistrict, textGender, textName, textPhone,
          textSignup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}