package cc.coopersoft.framework.tools;

import java.util.UUID;

public class UUIDGenerator {

    public static String getUUID(){
        String result = UUID.randomUUID().toString();
        return result.substring(0, 8) + result.substring(9, 13) + result.substring(14, 18) + result.substring(19, 23) + result.substring(24);
    }


}
