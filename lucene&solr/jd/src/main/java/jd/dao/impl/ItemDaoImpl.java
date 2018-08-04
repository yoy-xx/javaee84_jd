package jd.dao.impl;

import jd.dao.ItemDao;
import jd.pojo.ResultModel;
import jd.pojo.item;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public ResultModel queryProducts(SolrQuery solrQuery){

        ResultModel resultModel = new ResultModel();

        List<item> itemList = new ArrayList<>();


        try {
            //使用solr服务
            QueryResponse response = solrServer.query(solrQuery);

            //获取查询的文档集合
            SolrDocumentList results = response.getResults();

            //命中的总记录数
            Long numFound = results.getNumFound();

            resultModel.setTotalCount(numFound.intValue());

            //循环文档集合
            for (SolrDocument doc : results) {

                item item = new item();

                //获取id
                String id = (String) doc.get("id");
                item.setPid(id);


                //商品标题
                String product_name = (String) doc.get("product_name");

                //获取高亮
                Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
                //第一个map的key就是文档id
                Map<String, List<String>> stringListMap = highlighting.get(id);
                //第二个map的key就是高亮字段
                List<String> list = stringListMap.get("product_name");
                if (list!=null&&list.size()>0){
                    product_name = list.get(0);
                }


                item.setName(product_name);


                //商品价格
                float product_price = (float) doc.get("product_price");
                item.setPrice(product_price);


                //商品图片地址
                String product_picture = (String) doc.get("product_picture");
                item.setPicture(product_picture);

                itemList.add(item);

            }

        resultModel.setProductList(itemList);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultModel;
    }
}
