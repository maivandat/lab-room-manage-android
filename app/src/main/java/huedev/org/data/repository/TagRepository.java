package huedev.org.data.repository;

import android.support.annotation.NonNull;

import huedev.org.data.source.TagDataSource;
import huedev.org.data.source.local.TagLocalDataSource;
import huedev.org.data.source.remote.TagRemoteDataSource;
import huedev.org.data.source.remote.response.tag.CreateTagReponse;
import io.reactivex.Single;

public class TagRepository implements TagDataSource.LocalDataSource, TagDataSource.RemoteDataSource {

    private static TagRepository instance;

    @NonNull
    private TagLocalDataSource mTagLocalDataSource;

    @NonNull
    private TagRemoteDataSource mTagRemoteDataSource;

    public TagRepository (@NonNull TagLocalDataSource tagLocalDataSource, @NonNull TagRemoteDataSource tagRemoteDataSource) {
        this.mTagLocalDataSource = tagLocalDataSource;
        this.mTagRemoteDataSource = tagRemoteDataSource;
    }

    public static synchronized TagRepository getInstance (@NonNull TagLocalDataSource tagLocalDataSource, @NonNull TagRemoteDataSource tagRemoteDataSource) {
        if (instance == null) {
            instance = new TagRepository(tagLocalDataSource, tagRemoteDataSource);
        }
        return instance;
    }
    @Override
    public Single<CreateTagReponse> createTag(String value, int id_device) {
        return mTagRemoteDataSource.createTag(value, id_device);
    }
}
