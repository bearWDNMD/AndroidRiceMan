package com.example.riceMan.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.riceMan.R;
import com.example.riceMan.utils.DatabaseUtil;


public class FeedbackFragment extends Fragment {

    private EditText mFeedBackEditText;
    private Button mSendFeedBackButton;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        mFeedBackEditText = (EditText) root.findViewById(R.id.feedback_content);
        mSendFeedBackButton = (Button) root.findViewById(R.id.feedback_submit);
        mSendFeedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = mFeedBackEditText.getText().toString();
                //Util.insert_feedback(content,getContext());
                if (!"".equals(content)){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                HttpUrl httpUrl = HttpUrl.parse("http://10.0.2.2:8080/feedBack/").newBuilder()
//                                        .addQueryParameter("content", content).build();
//                                Request request = new Request.Builder().url(httpUrl.toString()).build();
//                                OkHttpClient client=new OkHttpClient();
//                                client.newCall(request).enqueue(new Callback() {
//                                    @Override
//                                    public void onFailure(Call call, IOException e) {
//                                        Log.i("error", "网络请求失败");
//                                    }
//                                    @Override
//                                    public void onResponse(Call call, Response response) throws IOException {
//                                        if (response.isSuccessful()) {
//                                            String jsonString = response.body().string();
//                                        }
//                                    }
//                                });
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
                    Toast.makeText(getContext().getApplicationContext(), "感谢您的反馈！",
                            Toast.LENGTH_SHORT).show();
                    DatabaseUtil.insert_feedback(content,getContext());
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "内容不能为空！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}