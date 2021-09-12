package tfsapps.autopainter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PaintData extends PaintParts {
    private float screen_x;
    private float screen_y;
    private float x;
    private float y;
    private int dp;

    private Paint paint;
    private int color1;
    private int color2;
    private int color3;
    private int color4;
    private int scale;
    private int stroke;
    private int type;

    private int sign_x = 0;
    private int sign_y = 0;
    private int move_x = 0;
    private int move_y = 0;


    public PaintData(Paint data,
                     float sc_x,
                     float sc_y,
                     float obj_x,
                     float obj_y,
                     int obj_dp) {
        super();
        paint = data;
        screen_x = sc_x;
        screen_y = sc_y;
        x = obj_x;
        y = obj_y;
        dp = obj_dp;
    }

    public void PaintDataSetParam(int c1,int c2,int c3,int c4, int sc, int st, int ty) {
        color1 = c1;
        color2 = c2;
        color3 = c3;
        color4 = c4;
        scale = sc;
        stroke = st;
        type = ty;
    }

    public void move(int s_x, int mv_x, int s_y, int mv_y){
        if (sign_x == 0)    sign_x = s_x;
        if (move_x == 0)    move_x = mv_x;
        if (sign_y == 0)    sign_y = s_y;
        if (move_y == 0)    move_y = mv_y;

        if (sign_x < 50)      x += mv_x;
        else                  x -= mv_x;

        if (sign_y < 50)      y += mv_y;
        else                  y -= mv_y;

    }

    @Override
    public void draw(Canvas canvas) {
        /* ここでタイプ表示？？ */
        switch (type) {
            // 円
            case 0:
                paint.setColor(Color.argb(color1, color2, color3, color4));
                paint.setStrokeWidth(stroke);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
                canvas.drawCircle(x - dp, y - dp, scale, paint);
//                canvas.drawCircle(x - 15 * dp, y - 55 * dp, scale, paint);
                break;
            // 円
            case 1:
                paint.setColor(Color.argb(color1, color2, color3, color4));
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
                canvas.drawCircle(x - dp, y - dp, scale, paint);
//                canvas.drawCircle(x - 15 * dp, y - 55 * dp, scale, paint);
                break;

            // 矩形
            case 2:
                // 矩形
                paint.setColor(Color.argb(color1, color2, color3, color4));
                paint.setStrokeWidth(stroke);
                paint.setStyle(Paint.Style.STROKE);
                // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
                canvas.drawRect(x - scale, y - scale,
                        x + scale * dp, y + scale * dp, paint);

/*                canvas.drawRect(x - 30 * dp, y - 50 * dp,
                        x + 120 * dp, y + 100 * dp, paint);*/
                break;

            // 矩形
            case 3:
                // 矩形
                paint.setColor(Color.argb(color1, color2, color3, color4));
                // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
                canvas.drawRect(x - scale * dp, y - scale * dp,
                        x + scale * dp, y + scale * dp, paint);
/*                canvas.drawRect(x - 30 * dp, y - 50 * dp,
                        x + 120 * dp, y + 100 * dp, paint);*/
                break;

            //  線
            case 4:
                paint.setStrokeWidth(stroke);
                paint.setColor(Color.argb(color1, color2, color3, color4));
                // (x1,y1,x2,y2,paint) 始点の座標(x1,y1), 終点の座標(x2,y2)
                canvas.drawLine(x + 20 * dp, y - 30 * dp, x - 70 * dp, y + 70 * dp, paint);
                break;
        }
    }
}
