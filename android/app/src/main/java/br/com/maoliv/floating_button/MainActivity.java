package br.com.maoliv.floating_button;

import android.os.Bundle;
import android.widget.ImageView;

import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.Screen;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

  private static final String CHANNEL = "floating_button";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    MethodChannel channel = new MethodChannel(getFlutterView(), CHANNEL);
    channel.setMethodCallHandler((call, result) -> {
      switch(call.method){
        case "create":
          ImageView image = new ImageView(getApplicationContext());
          image.setImageResource(android.R.drawable.ic_menu_add);

          FloatWindow.with(getApplicationContext()).setView(image)
                  .setWidth(Screen.width, 0.15f)
                  .setHeight(Screen.width, 0.15f)
                  .setX(Screen.width, 0.8f)
                  .setY(Screen.height, 0.3f)
                  .setDesktopShow(true)
                  .build();

          image.setOnClickListener(v ->{
            channel.invokeMethod("touch", null);
          });
          break;
        case "show":
          FloatWindow.get().show();
          break;
        case "hide":
          FloatWindow.get().hide();
          break;
        case "isShowing":
          result.success(FloatWindow.get().isShowing());
          break;
        default:
          result.notImplemented();
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    FloatWindow.destroy();
  }
}
