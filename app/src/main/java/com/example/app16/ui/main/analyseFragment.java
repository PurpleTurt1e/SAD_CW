package com.example.app16.ui.main;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import java.io.InputStream;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app16.R;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import android.view.View.OnClickListener;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.webkit.WebView;
import android.widget.TextView;



public class analyseFragment extends Fragment implements OnClickListener
{ View root;
  Context myContext;
  analyseBean analysebean;

  ImageView analyseResult;
  Button analyseOkButton;
  Button analysecancelButton;


 public analyseFragment() {}

  public static analyseFragment newInstance(Context c)
  { analyseFragment fragment = new analyseFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    fragment.myContext = c;
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  { super.onCreate(savedInstanceState); }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  { root = inflater.inflate(R.layout.analyse_layout, container, false);
    Bundle data = getArguments();
    analyseResult = (ImageView) root.findViewById(R.id.analyseResult);
    analysebean = new analyseBean(myContext);
    analyseOkButton = root.findViewById(R.id.analyseOK);
    analyseOkButton.setOnClickListener(this);
    analysecancelButton = root.findViewById(R.id.analyseCancel);
    analysecancelButton.setOnClickListener(this);
    return root;
  }



  public void onClick(View _v)
  { InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
    try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }
    if (_v.getId() == R.id.analyseOK)
    { analyseOK(_v); }
    else if (_v.getId() == R.id.analyseCancel)
    { analyseCancel(_v); }
  }

  public void analyseOK(View _v) 
  { 
    if (analysebean.isanalyseerror())
    { Log.w(getClass().getName(), analysebean.errors());
      Toast.makeText(myContext, "Errors: " + analysebean.errors(), Toast.LENGTH_LONG).show();
    }
    else
    { GraphDisplay _result = analysebean.analyse();
      analyseResult.invalidate();
      analyseResult.refreshDrawableState();
      analyseResult.setImageDrawable(_result);
    }
  }



  public void analyseCancel(View _v)
  { analysebean.resetData();
  }
}
