package com.brenohff.medonline.Socket;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.WebSocket;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;

public class Socket {

    private static final String TAG = "SOCKET";
    private static StompClient mStompClient;
    private Gson mGson = new GsonBuilder().create();

    public static void conectaSocket(Context context) {
        mStompClient = Stomp.over(WebSocket.class, "ws:/medonline-backend.herokuapp.com/consulta/websocket");
//        mStompClient = Stomp.over(WebSocket.class, "ws://192.168.0.22:8080/event/websocket");

        mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            Toast.makeText(context, "Erro ao conectar com o chat", Toast.LENGTH_SHORT).show();
                            break;
                        case CLOSED:
                            Toast.makeText(context, "Chat encerrado", Toast.LENGTH_SHORT).show();
                    }
                });

        mStompClient.connect();
    }

    public static void desconectaSocket() {
        if (mStompClient != null && mStompClient.isConnected()) {
            mStompClient.disconnect();
        }
    }
}
