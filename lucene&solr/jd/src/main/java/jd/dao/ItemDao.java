package jd.dao;

import jd.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;

public interface ItemDao {

    /**
     * 需求: 查询索引库的方法
     * 参数: SolrQuery
     * 返回值: ResultModel
     */

    ResultModel queryProducts(SolrQuery solrQuery);
}
