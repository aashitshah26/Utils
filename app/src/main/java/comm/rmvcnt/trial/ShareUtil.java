package comm.rmvcnt.trial;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ShareUtil {

    private static Context context;
    private static ShareUtil instance;

    public enum APP_NAME{
        WHATSAPP,FB,WECHAT,TELEGRAM,SNAPCHAT,HIKE,LINE,INSTA,MESSENGER;
    }


    public static void init(Context context){
        instance = new ShareUtil();
        ShareUtil.context = context;
    }

    public ShareUtil getInstance(){
        if (instance==null){
            throw new RuntimeException("Please initialize first by calling init()");
        }
        return instance;
    }

    /**
     * This method is used to share text with different application.
     * @param text Text to share.
     * @param decider The app name decider.
     * @throws  ActivityNotFoundException if application is not installed.
     *
     */
    public void shareText(String text,APP_NAME decider) throws ActivityNotFoundException{

        switch (decider){
            case WHATSAPP:
                shareToWhatsapp(text);
                break;
            case FB:
                shareToFb(text);
                break;
            case WECHAT:
                shareToWeChat(text);
                break;
            case TELEGRAM:
                shareToTelegram(text);
                break;
            case SNAPCHAT:
                shareToSnapchat(text);
                break;
            case HIKE:
                shareToHike(text);
                break;
            case LINE:
                shareToLine(text);
                break;
            case INSTA:
                shareToInsta(text);
                break;
            case MESSENGER:
                shareToMessenger(text);
                break;
        }
    }

    private void shareToMessenger( String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

    private  void shareToInsta( String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,text);
        shareIntent.setPackage("com.instagram.android");
        try {
            context.startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Insta have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareToLine( String text) {

        Intent lineIntent = new Intent(Intent.ACTION_SEND);
        lineIntent.setType("text/plain");
        lineIntent.putExtra(Intent.EXTRA_SUBJECT,"Share text");
        lineIntent.setPackage("jp.naver.line.android");
        lineIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            context.startActivity(lineIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Line have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private  void shareToHike(String text) {

        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.hike.chat.stickers");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Hike have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareToSnapchat( String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

    private void shareToTelegram( String text) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        myIntent.setPackage("org.telegram.messenger");
        myIntent.putExtra(Intent.EXTRA_TEXT, text);//
        try {
            context.startActivity(Intent.createChooser(myIntent, "Share with"));
        }catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Telegram have not been installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void shareToWeChat( String text) {

        Intent wechatIntent = new Intent(Intent.ACTION_SEND);

        wechatIntent.setType("text/plain");
        wechatIntent.putExtra(Intent.EXTRA_SUBJECT,"Share text");
        wechatIntent.setPackage("com.tencent.mm");
        wechatIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            context.startActivity(wechatIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Wechat have not been installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void shareToFb( String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

    private void shareToWhatsapp( String text) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }



}
