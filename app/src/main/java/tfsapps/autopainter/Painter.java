package tfsapps.autopainter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import androidx.annotation.NonNull;

public class Painter extends SurfaceView implements SurfaceHolder.Callback {
    Paint paint;
    Paint title;
    Paint score;
    private long time_count;
    private int game_stage = 1;
    private int draw_num = 0;
    private int star_num = 0;
    boolean drawing = true;
    private static final long FPS = 60;
    private final Random rand = new Random(System.currentTimeMillis());
    private final List<PaintData> paintList = new ArrayList<PaintData>();

    private final int GAME_OPEING = 1;      //ステージの表示　中央に大きく
    private final int GAME_SETTING = 2;     //ゲーム準備中
    private final int GAME_PLAYING = 3;     //ゲームプレイ中
    private final int GAME_ENDING = 4;      //ゲーム終了中

    private int game_status = 0;

    public Painter(Context context) {
        super(context);
        getHolder().addCallback(this);

        /* スコア表示（図形の数）の生成 */
        title = new Paint();
        /* スコア表示（図形の数）の生成 */
        score = new Paint();
    }

    public void StartDrawing(){
        drawing = true;
    }
    public void StopDrawing(){
        drawing = false;
    }

    /********************************************************************************
     図形生成の処理
     *********************************************************************************/
    public void createObject(Canvas canvas, long timer){
        float xc = getWidth();
        float yc = getHeight();
        int type;
        int i;

        //test  出現率調整　ゲームバランス
/*        int rate = 10;
        rate = rate - game_stage;
        if ((timer%rate) != 0){
            return;
        }
*/
        /* 図形の出現率 */
        while (true) {
            type = rand.nextInt(1000);
            type = type % 7;
            if (type == 6){ //星型だけ確率を下げる
                if ((timer%5) == 0){
                    break;
                }
            }
            else{
                break;
            }
        }
        paint = new Paint();

        float x = rand.nextInt((int) xc);
        float y = rand.nextInt((int) yc);

        int color_1 = rand.nextInt(255);
        int color_2 = rand.nextInt(255);
        int color_3 = rand.nextInt(255);
        int color_4 = rand.nextInt(255);
        int dp = rand.nextInt(3);
//        int stroke = rand.nextInt(8);
        int stroke = rand.nextInt(15);
        if (stroke <=5) stroke=5;
        int scale = rand.nextInt((int)xc/30);
//        int scale = rand.nextInt((int)xc/3);

        PaintData paintData = new PaintData(paint,xc,yc,x,y,dp);
        paintData.PaintDataSetParam(color_1,color_2,color_3,color_4,scale,stroke,type);
        paintList.add(paintData);
    }

    /********************************************************************************
        描画処理
    *********************************************************************************/
    protected void drawObject(Canvas canvas,long timer) {

        /* 背景 */
        canvas.drawColor(Color.argb(220, 255, 255, 255));   //白で影あり
//        canvas.drawColor(Color.argb(255, 255, 255, 255)); //白で影なし

        /* アプリ起動直後 */
        if (game_status == 0)   game_status = GAME_OPEING;

        /* ゲームエンディング */
        if (game_status == GAME_ENDING){
            title.setColor(Color.BLACK);
            title.setTextSize(70);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setAntiAlias(true);
            canvas.drawText("☆★ ステージ:" + game_stage + " クリア ★☆", 50, 150, title);

            /* オブジェクト全て消去 */
            for (int i = 0; i < paintList.size(); i++) {
                PaintData object = paintList.get(i);
                paintList.remove(object);
            }

            if ((timer % 100) == 0){
                game_status = GAME_OPEING;
                game_stage++;
                time_count = 0;
            }
            return;
        }


        /* ゲームオープニング */
        if (game_status == GAME_OPEING){
            title.setColor(Color.BLACK);
            title.setTextSize(70);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setAntiAlias(true);
            canvas.drawText("ステージ: " + game_stage + " 準備中...", 50, 150, title);

            if (timer > 70){
                game_status = GAME_SETTING;
            }
            return;
        }
        /* ゲーム準備中 */
        if (game_status == GAME_SETTING){
            title.setColor(Color.BLACK);
            title.setTextSize(70);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setAntiAlias(true);
            canvas.drawText("ステージ: " + game_stage + " スタート！", 50, 150, title);

            if (timer > 120){
                game_status = GAME_PLAYING;
            }
        }

        star_num = 0;

        /* 図形の生成 */
        createObject(canvas, timer);

        /* 図形の表示 */
        for (int i = 0; i < paintList.size(); i++) {
            PaintData object = paintList.get(i);
            int s_x = rand.nextInt(100) + 1;    //  X軸の移動ベクトル（ +方向<50 -方向>50)
            int m_x = rand.nextInt(3);          //  X軸の移動量
            int s_y = rand.nextInt(100) + 1;    //  y軸の移動ベクトル（ +方向<50 -方向>50)
            int m_y = rand.nextInt(4);          //  X軸の移動量
            int s_s = rand.nextInt(100) + 1;    //  図形の大きさベクトル（ +方向<50 -方向>50)
            boolean scale_flag;                        //  大きさ変更フラグ（時間間隔によって決まる）

            /* 大きさを変更する間隔 */
            if ((timer%60) == 0){
                scale_flag = true;
            }
            else{
                scale_flag = false;
            }
            /* 図形の移動 */
            object.move(s_x,m_x,s_y,m_y,s_s,scale_flag);
//            invalidate();
            /* 図形の表示 */
            object.draw(canvas);
        }

        /* 不要図形の消去 */
        for (int i = 0; i < paintList.size(); i++) {
            PaintData object = paintList.get(i);
            if (object.isObjAlive() == false){
                paintList.remove(object);
            }
            else {
                if (object.isStar() == true){
                    star_num++;
                }
            }
        }


        /* スコアの表示 */
        if (game_status == GAME_PLAYING) {
            title.setColor(Color.BLUE);
            title.setTextSize(45);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setAntiAlias(true);
            canvas.drawText("ほし★をタッチしてゼロ個にしよう！！", 50, 50, title);

            score.setColor(Color.RED);
            score.setTextSize(60);
            score.setTypeface(Typeface.DEFAULT_BOLD);
            score.setAntiAlias(true);
            canvas.drawText("ステージ:" + game_stage + "　★ 残り:" + star_num + "個", 50, 120, score);

            /* 星がゼロ個になった場合 */
            if (star_num <= 0){
                game_status = GAME_ENDING;
            }
        }
    }

    /********************************************************************************
        タッチイベント（タップ処理）
     *********************************************************************************/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.v("TAP>>>","xPos=" + event.getX() + ", yPos="+ event.getY());

                /* ゲームプレイ中のみ */
                if (game_status == GAME_PLAYING) {
                    for (int i = 0; i < paintList.size(); i++) {
                        PaintData object = paintList.get(i);
                        if (object.isObjHit(event.getX(), event.getY()) == true) {
                        }
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    /********************************************************************************
     ゲーム全体の描画間隔
     *********************************************************************************/
    private class DrawThread extends Thread {
        private boolean isFinished;
        @Override
        public void run() {
            super.run();
            time_count = 0;
            SurfaceHolder holder = getHolder();
            while (!isFinished) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawObject(canvas,time_count);
                    holder.unlockCanvasAndPost(canvas);
                }

                try {
                    time_count++;
                    //オーバーフロー対策　最大値超えの初期化処理
                    if(time_count > 99999999){
                        time_count = 1;
                    }
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
