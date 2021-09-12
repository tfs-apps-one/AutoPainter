package tfsapps.autopainter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class PaintParts {
    private int obj_scale = 0;  //オブジェクトの大きさ
    private int obj_shape = 0;  //オブジェクトの形
    private int obj_x = 0;      //オブジェクトのX座標
    private int obj_y = 0;      //オブジェクトのy座標
    /*
    *   type:   形
    *   color:  色
    *   stroke:
    * */
        public abstract void draw(Canvas canvas);
    }
