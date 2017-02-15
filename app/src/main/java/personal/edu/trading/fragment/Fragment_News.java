package personal.edu.trading.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import personal.edu.trading.R;

/**
 * 消息
 * Created by Administrator on 2017/2/5 0005.
 */
public class Fragment_News extends Fragment {
    ImageView error_im;
    EditText editText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        error_im= (ImageView) view.findViewById(R.id.news_error_im);
        editText= (EditText) view.findViewById(R.id.news_top_et);
        error_im.setVisibility(View.GONE);
        inClick();
    }
    void inClick(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    error_im.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        error_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                error_im.setVisibility(View.GONE);
            }
        });
    }
}
