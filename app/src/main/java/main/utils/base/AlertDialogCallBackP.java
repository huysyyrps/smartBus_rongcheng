package main.utils.base;

import java.util.List;

/**
 * Created by dell on 2017/4/25.
 */

public interface AlertDialogCallBackP {
    void oneselect(String s);
    void select(List<String> list);
    void confirm();
    void cancel();
}
