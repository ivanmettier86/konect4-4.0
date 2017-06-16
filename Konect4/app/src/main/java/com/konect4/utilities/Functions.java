package com.konect4.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.konect4.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by Muhammad Zeeshan on 5/31/2017.
 */

public class Functions {


    private static ProgressDialog progress;

    public static void LogsFunction(String msg) {
        Log.d("******SF_Logs", msg);
    }

    public static void Logout(Context contxt) {
        showDialog("Logging out...", contxt);
    }

    /**
     * Show loading dialog
     */
    public static void showDialog(final String msg, final Context contxt) {
        ((Activity) contxt).runOnUiThread(new Runnable() {
            public void run() {
                if (progress != null && progress.isShowing()) {
                    progress.hide();
                    progress = null;
                }
                progress = new ProgressDialog(contxt);
                progress.setMessage(msg);
                progress.setCanceledOnTouchOutside(false);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
            }
        });
    }


    /**
     * Hide loading dialog
     */
    public static void hideDialog() {
        if (progress != null)
            progress.hide();
    }

    public static void hideDialog(Context contxt) {
        ((Activity) contxt).runOnUiThread(new Runnable() {
            public void run() {
                if (progress != null)
                    progress.hide();
            }
        });
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager check = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (check != null) {
            NetworkInfo[] info = check.getAllNetworkInfo();
            Log.d("", "");
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        } else {
            return false;
        }
        return false;
    }

    /*
    *Show Toast Function
     */
    public static void showToast(final Activity act, final String msg) {
        act.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(act.getApplicationContext(),
                        msg, Toast.LENGTH_LONG).show();
            }
        });
    }

//    public static void resultDialog(Activity activity, String message, boolean is_success){
//        // custom dialog
//
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View layout = inflater.inflate(R.layout.result_dialog, (ViewGroup) activity.findViewById(R.id.custom_toast));
//
//        RelativeLayout layout_dialog=(RelativeLayout)layout.findViewById(R.id.custom_toast);
//
//        ImageView image = (ImageView) layout.findViewById(R.id.image);
//        if (is_success) {
//            image.setImageResource(R.drawable.popup_tick);
//        }else{
//            image.setImageResource(R.drawable.popup_cross);
//        }
//
//        TextView text = (TextView) layout.findViewById(R.id.text);
//        text.setText(message);
//
//        final Toast toast = new Toast(activity.getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//
//        layout_dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("CANCELED ", "YES ITS CANCELED");
//                toast.cancel();
//            }
//        });
//
//        toast.setView(layout);
//        toast.show();
//        final Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.result_dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        Window window = dialog.getWindow();
//        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);
//
//        // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText(message);
//
////        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
////        // if button is clicked, close the custom dialog
////        dialogButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                dialog.dismiss();
////            }
////        });
//        dialog.show();
//    }

//
//
//
//    public static void showRateDialog(final Activity act) {
//        // custom dialog
//        try {
//
//            final Dialog dialog = new Dialog(act);
//
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.setContentView(R.layout.rate_smartcooks_dialog);
//
//            TextView rate_now = (TextView) dialog.findViewById(R.id.rate_now);
//            TextView remind_me = (TextView) dialog.findViewById(R.id.remind_me);
//            TextView no_thanks = (TextView) dialog.findViewById(R.id.no_thanks);
//
//            dialog.setCancelable(false);
//            dialog.show();
//
//            rate_now.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                    RateUs(act);
//                    SessionManager s = new SessionManager(act.getApplicationContext());
//                    s.setKey("rate_counter", "-10");
//                }
//            });
//
//            remind_me.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//
//                    SessionManager s = new SessionManager(act.getApplicationContext());
//                    s.setKey("rate_counter", "0");
//                }
//            });
//
//            no_thanks.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                    SessionManager s = new SessionManager(act.getApplicationContext());
//                    s.setKey("rate_counter", "-5");
//
////                    Intent intent = new Intent(getActivity().getApplicationContext(), UserPreferences.class);
////                    SmartSuggestions.selected_screen="YourKitchen";
////                    startActivity(intent);
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.white)
            .showImageForEmptyUri(R.color.white).showImageOnFail(R.color.white)
            .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    /* Checking Internet Connection */
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        return true;

                    }
        }
        return false;
    }


    public static int convertDpToPixel(double dp, Activity activity) {
        if (activity==null){
            return 0;
        }
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        double px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public static int convertDpToPixel(float dp, Activity activity) {
        if (activity==null){
            return 0;
        }
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public static void requestToServer(String url,
                                       final Map<String, String> params, final VolleyCallback callback,
                                       Activity activity) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("ERROR", "error => " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//				Map<String, String> params = new HashMap<String, String>();
//				params.put(ControllerConstants.ACCESS_KEY_NAME,
//						ControllerConstants.ACCESS_KEY);
                return params;
            }

            protected Map<String, String> getParams()
                    throws AuthFailureError {
                return params;
            };
        };
        queue.add(postRequest);
    }

    public interface VolleyCallback {
        void onSuccess(String result);
    }

    public static void requestToServerJson(String url, final JSONObject params, final VolleyCallback callback, Activity activity) {
        try {
            RequestQueue queue = Volley.newRequestQueue(activity);

            JsonObjectRequest req = new JsonObjectRequest(url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            callback.onSuccess(response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    error.printStackTrace();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
//				params.put(ControllerConstants.ACCESS_KEY_NAME,
//						ControllerConstants.ACCESS_KEY);
                    return params;
                }
            };
            req.setShouldCache(false);
            queue.add(req);

            req.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        catch (OutOfMemoryError oome){
            oome.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface VolleyCallbackDetail {
        void onSuccess(String result);
        void onError(String result);
    }


    public static void requestToServerJsonDetail(String url, final JSONObject params, final VolleyCallbackDetail callback, Activity activity) {
        try {
            RequestQueue queue = Volley.newRequestQueue(activity);

            JsonObjectRequest req = new JsonObjectRequest(url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            callback.onSuccess(response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    error.printStackTrace();
                    callback.onError(error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
//				params.put(ControllerConstants.ACCESS_KEY_NAME,
//						ControllerConstants.ACCESS_KEY);
                    return params;
                }
            };
            queue.add(req);

            req.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        catch (OutOfMemoryError oome){
            oome.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void showImage(String path, ImageView imageView, final CircularProgressBar cp) {

        try {
            ImageLoader.getInstance().displayImage(path, imageView,
                    Functions.options, new ImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String arg0, View arg1) {
                            if (cp != null) {
                                cp.setIndeterminate(true);
                            }
                        }

                        @Override
                        public void onLoadingFailed(String arg0, View arg1,
                                                    FailReason arg2) {
                            if (cp != null) {
                                cp.setIndeterminate(false);
                            }
                        }

                        @Override
                        public void onLoadingComplete(String arg0, View arg1,
                                                      Bitmap arg2) {
                            if (cp != null) {
                                cp.setIndeterminate(false);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String arg0, View arg1) {
                            if (cp != null) {
                                cp.setIndeterminate(false);
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String changeFormat(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date testDate = null;
        try {
            testDate = sdf.parse(dateTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
        String newFormat = formatter.format(testDate);
//		String split[] = newFormat.split(" ");
//		String dateInUrdu = split[0]+" "+months[0]+" "+split[1];

        return newFormat;
    }



    public static void RateUs(final Activity act) {
        String appName = "com.smartcooks";
        Uri uri = Uri.parse("market://details?id=" + appName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        //   goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
        //            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
        //             Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            act.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            act.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }//RateUs()
}