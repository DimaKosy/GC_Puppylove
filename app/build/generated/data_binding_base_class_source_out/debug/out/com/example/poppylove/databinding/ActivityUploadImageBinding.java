// Generated by view binder compiler. Do not edit!
package com.example.poppylove.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.poppylove.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityUploadImageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button retriveImg;

  @NonNull
  public final Button selectImg;

  @NonNull
  public final ImageView selectedImg;

  private ActivityUploadImageBinding(@NonNull ConstraintLayout rootView, @NonNull Button retriveImg,
      @NonNull Button selectImg, @NonNull ImageView selectedImg) {
    this.rootView = rootView;
    this.retriveImg = retriveImg;
    this.selectImg = selectImg;
    this.selectedImg = selectedImg;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityUploadImageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityUploadImageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_upload_image, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityUploadImageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.retriveImg;
      Button retriveImg = ViewBindings.findChildViewById(rootView, id);
      if (retriveImg == null) {
        break missingId;
      }

      id = R.id.selectImg;
      Button selectImg = ViewBindings.findChildViewById(rootView, id);
      if (selectImg == null) {
        break missingId;
      }

      id = R.id.selectedImg;
      ImageView selectedImg = ViewBindings.findChildViewById(rootView, id);
      if (selectedImg == null) {
        break missingId;
      }

      return new ActivityUploadImageBinding((ConstraintLayout) rootView, retriveImg, selectImg,
          selectedImg);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
