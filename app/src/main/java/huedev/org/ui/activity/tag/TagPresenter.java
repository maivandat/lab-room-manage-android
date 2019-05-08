package huedev.org.ui.activity.tag;

import android.content.Context;
import huedev.org.data.repository.TagRepository;
import huedev.org.data.source.remote.response.tag.CreateTagReponse;
import huedev.org.utils.rx.BaseSchedulerProvider;

public class TagPresenter implements TagContact.Presenter {
    private Context context;
    private TagContact.View mView;
    private TagRepository mTagRepository;
    private BaseSchedulerProvider baseSchedulerProvider;

    public TagPresenter(Context context, TagRepository tagRepository, BaseSchedulerProvider baseSchedulerProvider) {
        this.context = context;
        this.mTagRepository = tagRepository;
        this.baseSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void createTag(String value, int id_device) {
        if (value.isEmpty()){
            mView.logicCreateFaild();
        }else {
            mTagRepository.createTag(value, id_device)
                    .subscribeOn(baseSchedulerProvider.io())
                    .observeOn(baseSchedulerProvider.ui())
                    .subscribe(
                            createTagReponse -> handleCreateTagSuccess(createTagReponse),
                            err -> handleCreateTagFaild(err));
        }
    }

    private void handleCreateTagFaild(Throwable err) {
        mView.showLoginError(err);
    }

    private void handleCreateTagSuccess(CreateTagReponse createTagReponse) {
        mView.createSuccess(createTagReponse.tagItem);
    }

    @Override
    public void setView(TagContact.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
