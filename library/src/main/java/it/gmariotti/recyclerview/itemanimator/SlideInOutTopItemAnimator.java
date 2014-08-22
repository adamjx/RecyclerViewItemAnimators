package it.gmariotti.recyclerview.itemanimator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SlideInOutTopItemAnimator extends BaseItemAnimator {

    float mOriginalY;

    public SlideInOutTopItemAnimator(RecyclerView recyclerView) {
        super(recyclerView);
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        retrieveItemHeight(holder);

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                translationY(-mOriginalY).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setTranslationY(view, -mOriginalY);
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
        retrieveItemHeight(holder);
        ViewCompat.setTranslationY(holder.itemView, -mOriginalY);
    }

    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).translationY(0)
                .setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setTranslationY(view, 0);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
        mAddAnimations.add(holder);
    }


    private void retrieveItemHeight(final RecyclerView.ViewHolder holder){
       mOriginalY = mRecyclerView.getLayoutManager().getDecoratedBottom(holder.itemView);
    }


}
