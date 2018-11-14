package cc.coopersoft;

import cc.coopersoft.framework.tools.SetLinkList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestKnow {


    public static void main(String[] args){

        Set<String> s = new HashSet<>();
        s.add("111");
        List<String> list = SetLinkList.instance(s);
        System.out.println(list.get(0));
        s.add("22222");
        System.out.println(list.get(1));
        list.add("333");
        System.out.println(s.size());
    }

}
