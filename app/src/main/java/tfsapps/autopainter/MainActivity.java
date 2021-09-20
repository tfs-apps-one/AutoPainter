package tfsapps.autopainter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
/*
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
*/
public class MainActivity extends AppCompatActivity implements Painter.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Painter painter = new Painter(this);
        painter.setCallback(this);
        setContentView(painter);
/*
        String AdUnitID = "ca-app-pub-3940256099942544/6300978111";
        // Relative layout インスタンス生成
        RelativeLayout layout = new RelativeLayout(this);
        setContentView(layout);

        // 背景

        // AdMob用のLayout parameterを設定
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        // 画面の底に位置させるようにALIGN_PARENT_BOTTOMを設定
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        // AdMob インスタンス生成
        AdView adMobView = new AdView(this);
        adMobView.setAdUnitId(AdUnitID);
        adMobView.setAdSize(AdSize.BANNER);
        // AdMobをレイアウトに追加
        layout.addView(adMobView);

        // AdMobのレイアウト属性を設定
        adMobView.setLayoutParams(params);

        // AdMobをロードして表示
        AdRequest adRequest = new AdRequest.Builder().build();
        adMobView.loadAd(adRequest);

 */
    }
}