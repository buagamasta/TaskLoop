package at.pinzker.taskloop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/** Compact, accessible permission status indicator for the app header. */
final class StatusDot extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final boolean ready;

    StatusDot(Context context, boolean ready) {
        super(context);
        this.ready = ready;
        setClickable(true);
        setFocusable(true);
        if (!ready) {
            AlphaAnimation pulse = new AlphaAnimation(0.35f, 1f);
            pulse.setDuration(850);
            pulse.setRepeatMode(Animation.REVERSE);
            pulse.setRepeatCount(Animation.INFINITE);
            startAnimation(pulse);
        }
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = getResources().getDisplayMetrics().density * 7f;
        paint.setColor(ready ? 0xFF55D68B : 0xFFFF555F);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, paint);
    }

    @Override protected void onDetachedFromWindow() {
        clearAnimation();
        super.onDetachedFromWindow();
    }
}
