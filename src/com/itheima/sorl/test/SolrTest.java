package com.itheima.sorl.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrTest {

    @Test
    public void testCreate() throws IOException, SolrServerException {
        //1. 与solr服务器建立联系，创建httpsolrserver，传入接口地址
        HttpSolrServer SolrServer = new HttpSolrServer("http://localhost:8081/solr/");

        //2. 创建solrinputdocument对象，调用add方法构建内容
        SolrInputDocument document = new SolrInputDocument();

        document.addField("id", "c001");
        document.addField("product_name", "美女与野兽的恋爱");

        SolrServer.add(document);

        SolrServer.commit();
    }


    @Test
    public void testDelete() throws IOException, SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8081/solr/");

        SolrInputDocument document = new SolrInputDocument();

        solrServer.deleteById("c001");

        solrServer.commit();

    }

    @Test
    public void testQuery() throws SolrServerException {
//        1. 	与Solr服务器建立连接，创建HttpSolrServer，传入接口地址
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8081/solr/");
//        2. 	创建搜索对象SolrQuery
        SolrQuery query = new SolrQuery();
//        3. 	设置查询条件setQ...
        query.setQuery("product_name:'灯'");
//        4. 	查询数据(solrServer.query)
        QueryResponse queryResponse = solrServer.query(query);
//        5. 	获取文档列表(response.getResults)
        SolrDocumentList results = queryResponse.getResults();
//        6. 	输出总条数(results.getNumFound)
        System.out.println(results.getNumFound());
//        7. 	解析查询结果，遍历results，直接通过get("属性取值")
        for (SolrDocument result : results) {
            System.out.println(result.get("id") + ":" + result.get("product_name"));
        }
    }

    @Test
    public void testQuery2() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8081/solr/");

        SolrQuery query = new SolrQuery();

        query.setQuery("灯");
        query.setFilterQueries("花");
        query.set("df", "product_name");

        QueryResponse response = solrServer.query(query);

        SolrDocumentList results = response.getResults();

        for (SolrDocument result : results) {
            System.out.println(result.get("id") + ":" + result.get("product_name"));
        }
    }
}
