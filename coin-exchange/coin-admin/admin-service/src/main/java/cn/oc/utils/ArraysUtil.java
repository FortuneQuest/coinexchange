package cn.oc.utils;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName : ArraysUtil
 * @Author: oc
 * @Date: 2022/11/28/15:53
 * @Description:
 **/
public class ArraysUtil {

    /**
     * 判断数组是否为空
     * @param ids
     * @return
     */
    public static boolean ArraysIsEmpty(Object[] ids) {
        if (ids == null || ids.length == 0) {
            return true;
        }
        return false;
    }
}
