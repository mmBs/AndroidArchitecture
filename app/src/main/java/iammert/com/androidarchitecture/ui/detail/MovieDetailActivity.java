package iammert.com.androidarchitecture.ui.detail;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import iammert.com.androidarchitecture.R;
import iammert.com.androidarchitecture.databinding.ActivityMovieDetailBinding;

/**
 * Created by mertsimsek on 19/05/2017.
 */

public class MovieDetailActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private static final String KEY_MOVIE_ID = "key_movie_id";

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    ActivityMovieDetailBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    MovieDetailViewModel movieDetailViewModel;

    public static Intent newIntent(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(KEY_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int movieId = getIntent().getIntExtra(KEY_MOVIE_ID, 0);
        movieDetailViewModel.getMovie(movieId)
                .observe(this, movieEntity -> binding.setMovie(movieEntity));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

}
