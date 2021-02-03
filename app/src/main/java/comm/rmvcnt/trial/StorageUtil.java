package comm.rmvcnt.trial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.storage.StorageManager;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StorageUtil {

    private SharedPreferences preferences;
    private static Context context;

    private static StorageUtil instance;

    private StorageUtil(){};

    public static void init(Context context){
        instance = new StorageUtil();
        StorageUtil.context = context;
        instance.preferences = context.getSharedPreferences("STORAGE",Context.MODE_PRIVATE);
    }

    public StorageUtil getInstance(){
        if (instance==null){
            throw new RuntimeException("Please initialize first by calling init()");
        }
        return instance;
    }



    public  < E > void storeObject (String name,E object) {
        preferences.edit().putString(name,deserialize(object)).apply();
    }

    public  < E > void storeList (String name,List<E> object) {
        preferences.edit().putString(name,deserialize(object)).apply();
    }

    public  < T > T getObject (String name,Class<T> type) {
        return serializeObject(preferences.getString(name,""),type);
    }

    public  < T > ArrayList<T> getList (String name,Class<T> type) {
        return serializeList(preferences.getString(name,""),type);
    }




    private < E > String deserialize (E object){
        return new Gson().toJson(object);
    }

    private < E > String deserialize (List<E> object){
        return new Gson().toJson(object);
    }

    private <T> T serializeObject(String json, Class<T> type){

        Type type1 = TypeToken.getParameterized(type,type).getType();
        if (json==null){
            return null;
        }else {
            try{
                return new Gson().fromJson(json, type1);
            }catch (JsonSyntaxException e){
                return null;
            }
        }
    }

    private <T> ArrayList<T> serializeList(String json, Class<T> type){
        Type type1 = TypeToken.getParameterized(ArrayList.class, type).getType();
        if (json==null){
            return null;
        }else {
            try{
                return new Gson().fromJson(json, type1);
            }catch (JsonSyntaxException e){
                return null;
            }
        }
    }


}
