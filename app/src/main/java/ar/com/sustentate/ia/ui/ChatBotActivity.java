package ar.com.sustentate.ia.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.ParseException;

import ar.com.sustentate.ia.Controller.MessageController;
import ar.com.sustentate.ia.R;
import ar.com.sustentate.ia.adapter.MessageAdapter;
import ar.com.sustentate.ia.api.ResultListener;
import ar.com.sustentate.ia.models.AssistanceRequest;
import ar.com.sustentate.ia.models.AssistanceResponse;
import ar.com.sustentate.ia.utils.HTTPConnectionManager;

public class ChatBotActivity extends AppCompatActivity {

    EditText editText;
    ImageButton sendButton;
    ListView listView;
    LinearLayout editChat;
    MessageAdapter messageAdapter;
    String lsessionid = "";
    MessageController messageController = new MessageController();

    @SuppressLint({"RestrictedApi", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.editText);
        messageAdapter = new MessageAdapter(this);
        sendButton = findViewById(R.id.sendButton);
        listView =  findViewById(R.id.messages_view);
        editChat = findViewById(R.id.editchat);
        listView.setAdapter(messageAdapter);

        if(HTTPConnectionManager.isNetworkingOnline(this)){
            try {
                messageAdm("hola", false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else{
            editText.setEnabled(false);
            editText.setInputType(InputType.TYPE_NULL);
            editText.setText("");
            sendButton.setClickable(false);
            sendButton.setEnabled(false);
            editChat.setBackground(getResources().getDrawable(R.drawable.editchatdis));
            AssistanceResponse assistanceResponse = new AssistanceResponse("No hay conexion","www.google.com", 3, "0");
            messageAdapter.add(assistanceResponse);
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
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
