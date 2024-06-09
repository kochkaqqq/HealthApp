package com.example.health;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.health.adapters.UserAdapter;
import com.example.health.domain.User;
import com.example.health.webclient.client.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RatingFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<User> users = new ArrayList<>();

    public RatingFragment() {
        users = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        users = new ArrayList<>();
        Client client = new Client();
        try {
            client.GetUserList(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        users = new ArrayList<>();
        Client client = new Client();
        try {
            client.GetUserList(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        recyclerView = view.findViewById(R.id.recycleView);

        sortUsersByScore();

        return view;
    }

    private void sortUsersByScore() {
        Observable.fromIterable(users)
                .sorted((user1, user2) -> Integer.compare(user2.getScore(), user1.getScore()))
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sortedUsers -> {
                    UserAdapter adapter = new UserAdapter(sortedUsers);
                    recyclerView.setAdapter(adapter);
                }, Throwable::printStackTrace);
    }
}