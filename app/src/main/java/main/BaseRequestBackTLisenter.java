package main;

/**
 * Created by dell on 2017/4/20.
 */

public interface BaseRequestBackTLisenter<T> {
    void success(T t);
    void fail(String message);
    void failF(String message);


}


