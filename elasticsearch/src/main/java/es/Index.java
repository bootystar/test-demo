package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

/**
 * ES 索引的创建查看删除
 * @author booty
 * @date 2021/7/22 15:02
 */
public class Index {

    private static RestHighLevelClient client;

    /**
     * 获取es客户端对象
     */
    @Before
    public void before() {
        //连接地址
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        //连接工厂
        RestClientBuilder builder = RestClient.builder(httpHost);
        //创建客户端连接对象
        client=new RestHighLevelClient(builder);
    }

    /**
     * 关闭es客户端对象
     */
    @After
    public void after() throws IOException {
        //关闭连接
        client.close();
    }


    /**
     * 创建索引
     */
    @Test
    public void test1() throws IOException {
        //创建索引为user的请求对象
        CreateIndexRequest request = new CreateIndexRequest("user");
        //发送请求 获取相应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println("是否创建成功:"+acknowledged);
    }


    /**
     * 查看索引
     */
    @Test
    public void test2() throws IOException {
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        System.out.println("aliases:"+ response.getAliases());
        System.out.println("mappings:"+response.getMappings());
        System.out.println( "settings"+ response.getSettings());
    }


    /**
     * 删除索引
     */
    @Test
    public void test3() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println("是否删除成功:"+acknowledged);
    }




}

