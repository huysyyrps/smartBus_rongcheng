package main.smart.bus.util;



import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ״̬��������
 * ���ߣ� JairusTse
 * ���ڣ� 17/12/19
 */
public class StatusBarUtil {

    /**
     * ����״̬��Ϊ͸��
     * @param activity
     */
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * �޸�״̬����ɫ��֧��4.4���ϰ汾
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {

        //Android6.0��API 23�����ϣ�ϵͳ����
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //ʹ��SystemBarTint��ʹ4.4�汾״̬����ɫ����Ҫ�Ƚ�״̬������Ϊ͸��
            setTranslucentStatus(activity);
            //����״̬����ɫ
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    /**
     * ����״̬��ģʽ
     * @param activity
     * @param isTextDark ���֡�ͼ���Ƿ�Ϊ��ɫ ��falseΪĬ�ϵİ�ɫ��
     * @param colorId ״̬����ɫ
     * @return
     */
    public static void setStatusBarMode(Activity activity, boolean isTextDark, int colorId) {

        if(!isTextDark) {
            //���֡�ͼ����ɫ���䣬ֻ�޸�״̬����ɫ
            setStatusBarColor(activity, colorId);
        } else {
            //�޸�״̬����ɫ������ͼ����ɫ
            setStatusBarColor(activity, colorId);
            //4.4���ϲſ��Ը�����ͼ����ɫ
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(OSUtil.isMIUI()) {
                    //С��MIUIϵͳ
                    setMIUIStatusBarTextMode(activity, isTextDark);
                } else if(OSUtil.isFlyme()) {
                    //����flymeϵͳ
                    setFlymeStatusBarTextMode(activity, isTextDark);
                } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //6.0���ϣ�����ϵͳ����
                    Window window = activity.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    //4.4����6.0���µ�����ϵͳ����ʱû���޸�״̬��������ͼ����ɫ�ķ������п��Լ���
                }
            }
        }
    }

    /**
     * ����Flymeϵͳ״̬��������ͼ����ɫ
     * @param activity
     * @param isDark ״̬�����ּ�ͼ���Ƿ�Ϊ��ɫ
     * @return
     */
    public static boolean setFlymeStatusBarTextMode(Activity activity, boolean isDark) {
        Window window = activity.getWindow();
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isDark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * ����MIUIϵͳ״̬��������ͼ����ɫ��MIUIV6���ϣ�
     * @param activity
     * @param isDark ״̬�����ּ�ͼ���Ƿ�Ϊ��ɫ
     * @return
     */
    public static boolean setMIUIStatusBarTextMode(Activity activity, boolean isDark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (isDark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//״̬��͸���Һ�ɫ����
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//�����ɫ����
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //������ 7.7.13 ���Ժ�汾������ϵͳAPI���ɷ�����Ч�����ᱨ������������ʽ��Ҫ����
                    if (isDark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View
                                .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                                .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View
                                .SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
}