package jd.service;

import jd.pojo.ResultModel;

public interface ItemService {

    /**
     * 根据关键词查询索引库商品数据
     * 参数: String queryString, String catalog_name, String price, String sort, Integer page, Integer rows
     */

    ResultModel queryProduct(String queryString, String catalog_name, String price, String sort, Integer page, Integer rows);
}
