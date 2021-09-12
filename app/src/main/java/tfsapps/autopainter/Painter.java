package tfsapps.autopainter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import androidx.annotation.NonNull;

public class Painter extends SurfaceView implements SurfaceHolder.Callback {
    Paint paint;
    float dp = 2;
    boolean drawing = true;
    private static final long FPS = 60;
    private final Random rand = new Random(System.currentTimeMillis());
    private final List<PaintData> paintList = new ArrayList<PaintData>();

    public Painter(Context context) {
        super(context);
        getHolder().addCallback(this);
    }
    public void StartDrawing(){
        drawing = true;
    }
    public void StopDrawing(){
        drawing = false;
    }

    public void createObject(Canvas canvas){
        float xc = getWidth();
        float yc = getHeight();
        int type;
        type = rand.nextInt(1000);
        type = type % 4;

        paint = new Paint();

        float x = rand.nextInt((int) xc);
        float y = rand.nextInt((int) yc);

        int color_1 = rand.nextInt(255);
        int color_2 = rand.nextInt(255);
        int color_3 = rand.nextInt(255);
        int color_4 = rand.nextInt(255);
        int dp = rand.nextInt(3);
        int stroke = rand.nextInt(8);
        int scale = rand.nextInt(10);
//        int scale = rand.nextInt((int)xc/3);

        PaintData paintData = new PaintData(paint,xc,yc,x,y,dp);
        paintData.PaintDataSetParam(color_1,color_2,color_3,color_4,scale,stroke,type);
        paintList.add(paintData);
    }

    protected void drawObject(Canvas canvas) {
        // 背景
        canvas.drawColor(Color.argb(255, 0, 0, 0));

        if (drawing == false){
            return;
        }
        createObject(canvas);
        for (int i = 0; i < paintList.size(); i++) {
            PaintData object = paintList.get(i);
            int s_x = rand.nextInt(100);
            int m_x = rand.nextInt(3);
            int s_y = rand.nextInt(100);
            int m_y = rand.nextInt(3);
            object.move(s_x,m_x,s_y,m_y);
//            invalidate();
            object.draw(canvas);
        }
    }

    /* ゲーム全体の描画間隔 */
    private class DrawThread extends Thread {
        private boolean isFinished;

        @Override
        public void run() {
            super.run();

            SurfaceHolder holder = getHolder();
            while (!isFinished) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawObject(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                try {
                    sleep(1000 / FPS);
//                    sleep(10000 / FPS);
//                    sleep(10000 / FPS);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private DrawThread drawThread;

    public interface Callback {
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void startDrawThread() {
        stopDrawThread();
        drawThread = new DrawThread();
        drawThread.start();
    }

    public boolean stopDrawThread() {
        if (drawThread == null) {
            return false;
        }
        drawThread.isFinished = true;
        drawThread = null;
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        startDrawThread();  //描画スレッド起動
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
