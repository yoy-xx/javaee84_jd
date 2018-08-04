package jd.pojo;

import java.util.List;

public class ResultModel {

    //总记录数
    private Integer totalCount;

    //总页码
    private Integer totalPages;

    //当前页
    private Integer curPage;

    //总记录
    private List<item> productList;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public List<item> getProductList() {
        return productList;
    }

    public void setProductList(List<item> productList) {
        this.productList = productList;
    }
}
