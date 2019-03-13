package ar.com.sustentate.com.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.ParseException;

import ar.com.sustentate.com.Controller.MessageController;
import ar.com.sustentate.com.R;
import ar.com.sustentate.com.adapter.MessageAdapter;
import ar.com.sustentate.com.api.ResultListener;
import ar.com.sustentate.com.models.AssistanceRequest;
import ar.com.sustentate.com.models.AssistanceResponse;

public class ChatBotActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    MessageAdapter messageAdapter;
    String lsessionid = "";
    MessageController messageController = new MessageController();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.editText);
        messageAdapter = new MessageAdapter(this);
        listView = (ListView) findViewById(R.id.messages_view);
        listView.setAdapter(messageAdapter);

        try {
            messageAdm("hola", false);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                try {
                    messageAdm(message, true);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void messageAdm(String message, final boolean user) throws ParseException {
        editText.getText().clear();
        final AssistanceRequest assistanceRequest = new AssistanceRequest(message, "www.google.com", lsessionid );
        if (user){messageAdapter.add(assistanceRequest);}
        messageController.obtenerResponse(this, assistanceRequest, new ResultListener<AssistanceResponse>() {
            @Override
            public void loading() {
                listView.setSelection(listView.getCount() - 1);

            }

            @Override
            public void finish(AssistanceResponse result) {
                messageAdapter.add(result);
                lsessionid = result.getSessionId();
                listView.setSelection(listView.getCount() - 1);
            }

            @Override
            public void error(Throwable error) {

            }
        });

    }


}
