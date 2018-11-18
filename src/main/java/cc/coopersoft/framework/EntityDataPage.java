package cc.coopersoft.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 7/24/16.
 */
public class EntityDataPage<E> {

    private static final int DEFAULT_PAGE_MAX_COUNT = 13;

    public static class DataPage{

        private String title;
        private int page;
        private int firstResult;

        public DataPage(String title, int page, int firstResult) {
            this.title = title;
            this.page = page;
            this.firstResult = firstResult;
        }

        public String getTitle() {
            return title;
        }

        private void setTitle(String title){
            this.title = title;
        }

        public int getPage() {
            return page;
        }

        public int getFirstResult() {
            return firstResult;
        }
    }

    private int displayPageCount = DEFAULT_PAGE_MAX_COUNT;

    private List<E> dataList;

    private int page;

    //private List<DataPage> pages;

    private int firstResult;

    private long pageCount;

    private long dataCount;

    private int pageSize;

    public EntityDataPage(List<E> dataList, int firstResult, long dataCount, int pageSize) {
        this.dataList = dataList;
        this.firstResult = firstResult;
        this.dataCount = dataCount;
        this.pageSize = pageSize;
        //pages = new ArrayList<>();
        pageCount = dataCount/pageSize;
        if (dataCount%pageSize > 0){
            pageCount++;
        }
        if (firstResult == 0){
            page = 1;
        }else
            page = (firstResult / pageSize) + 1;

//        for (int i = 1; i <= pageCount ; i++){
//            pages.add(new DataPage(String.valueOf(i),i, (i - 1) * pageSize));
//        }

    }

    public int getDisplayPageCount() {
        return displayPageCount;
    }

    public void setDisplayPageCount(int displayPageCount) {
        this.displayPageCount = displayPageCount;
    }

    public boolean isHaveNext(){
        return (page < pageCount);
    }

    public boolean isHavePrevious(){
        return (firstResult > 0);
    }

    public int getNextFirstResult(){
        return firstResult + pageSize;
    }

    public int getPreviousFirstResult(){
        return firstResult - pageSize;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public int getPage() {
        return page;
    }

    public List<DataPage> getPages() {
        List<DataPage> pages = new ArrayList<>();
        int p = getPage() - 1;
        int a = getPage() + 1;
        pages.add(new DataPage(String.valueOf(getPage()),getPage(), (getPage() - 1) * pageSize));
        while (pages.size()<= getDisplayPageCount()){
            if (p > 0){

                pages.add(0,new DataPage(String.valueOf(p),p, (p - 1) * pageSize));
                p--;
            }
            if (a <= pageCount){
                pages.add(new DataPage(String.valueOf(a),a, (a - 1) * pageSize));
                a++;
            }
        }

        DataPage first = pages.get(0);
        if ((first != null) &&  (first.getPage() != getPage()) && (first.getPage() > 1) ){
            first.setTitle("...");
        }
        DataPage last = pages.get(pages.size()-1);
        if ((last != null) && (last.getPage() != getPage()) && (last.getPage() < pageCount)){
            last.setTitle("...");
        }

        return pages;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public long getDataCount() {
        return dataCount;
    }

    public boolean isEmptyResult(){
        return dataCount == 0;
    }

    public long getPageCount() {
        return pageCount;
    }
}
