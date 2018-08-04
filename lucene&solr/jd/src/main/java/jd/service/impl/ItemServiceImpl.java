package jd.service.impl;

import jd.dao.ItemDao;
import jd.pojo.ResultModel;
import jd.service.ItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    /**
     * 需求: 根据关键词查询索引库商品数据
     * String queryString,
     * String catalog_name,
     * String price,
     * String sort,
     * Integer page,
     * Integer rows
     * @return ResultModel
     * 业务: 封装数据,页码计算
     */
    @Override
    public ResultModel queryProduct(String queryString,
                                    String catalog_name,
                                    String price,
                                    String sort,
                                    Integer page,
                                    Integer rows) {

        SolrQuery solrQuery = new SolrQuery();

        //封装主查询
        if (queryString!= null && !queryString.equals("")){
            solrQuery.setQuery(queryString);
        }
        else{
            solrQuery.setQuery("*:*");
        }

        //封装分类过滤查询
        if (catalog_name!= null && !catalog_name.equals("")){
            solrQuery.addFilterQuery("product_catalog_name:"+catalog_name);
        }

        //价格过滤
        if (price!= null && !price.equals("")){
            String[] split = price.split("-");
            solrQuery.addFilterQuery("product_price:[" + split[0] + " TO " + split[1] + "]");
        }

        //排序
        if (sort.equals("1")){
            solrQuery.setSort("product_price",SolrQuery.ORDER.asc);
        }
        else {
            solrQuery.setSort("product_price",SolrQuery.ORDER.desc);
        }

        //设置分页
        if (page == null){
            page = 1;
        }
        int startNo = (page-1)*rows;
        solrQuery.setStart(startNo);
        solrQuery.setRows(rows);

        //7.高亮查询
        //1) 开启高亮
        solrQuery.setHighlight(true);
        //2) 指定高亮字段
        solrQuery.addHighlightField("product_name");
        //3) 设置高亮前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //4) 设置高亮后缀
        solrQuery.setHighlightSimplePost("</font>");

        //默认查询字段
        solrQuery.set("df","product_keywords");

        //计算总页码
        ResultModel model = itemDao.queryProducts(solrQuery);
        Integer totalCount = model.getTotalCount();
        Integer pages = totalCount / rows;
        if (totalCount%rows>0){
            pages++;
        }

        //设置总页数
        model.setTotalPages(pages);
        //设置当前页
        model.setCurPage(page);



        return model;
    }
}
