package es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * 数据模型类
 *
 * @author booty
 * @date 2021/7/22 15:45
 */
public class Document {

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
        client = new RestHighLevelClient(builder);
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
     * 创建文档数据
     */
    @Test
    public void test1() throws IOException {
        //新增文档请求对象
        IndexRequest request = new IndexRequest();
        //设置索引及唯一标识
        request.index("user").id("1001");
        //创建数据对象
        User user = new User("张三", 30, "男");
        //将对象转化为json格式的mapper
        ObjectMapper objectMapper = new ObjectMapper();
        //转化为json对象
        String jsonUser = objectMapper.writeValueAsString(user);
        //添加传输对象,数据格式为json
        request.source(jsonUser, XContentType.JSON);
        //发送请求,获取响应
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println("_index:" + response.getIndex());
        System.out.println("_id:" + response.getId());
        System.out.println("_result:" + response.getResult());
    }


    /**
     * 修改文档
     */
    @Test
    public void test2() throws IOException {
        // 修改文档 - 请求对象
        UpdateRequest request = new UpdateRequest();
        // 配置修改参数
        request.index("user").id("1001");
        // 设置请求体，对数据进行修改
        request.doc(XContentType.JSON, "sex", "女");
        // 客户端发送请求，获取响应对象
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println("_index:" + response.getIndex());
        System.out.println("_id:" + response.getId());
        System.out.println("_result:" + response.getResult());
    }

    /**
     * 查询文档
     */
    @Test
    public void test3() throws IOException {
        //1.创建请求对象
        GetRequest request = new GetRequest().index("user").id("1001");
        //2.客户端发送请求，获取响应对象
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        //3.打印结果信息
        System.out.println("_index:" + response.getIndex());
        System.out.println("_type:" + response.getType());
        System.out.println("_id:" + response.getId());
        System.out.println("source:" + response.getSourceAsString());
    }


    /**
     * 删除文档
     */
    @Test
    public void test4() throws IOException {
        //创建请求对象
        DeleteRequest request = new DeleteRequest().index("user").id("1");
        //客户端发送请求，获取响应对象
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        //打印信息
        System.out.println(response.toString());
    }

    /**
     * 批量创建
     */
    @Test
    public void test5() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new
                IndexRequest().index("user").id("1001").source(XContentType.JSON, "name",
                "zhangsan"));
        request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name",
                "lisi"));
        request.add(new
                IndexRequest().index("user").id("1003").source(XContentType.JSON, "name",
                "wangwu"));
        //客户端发送请求，获取响应对象
        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println("took:" + responses.getTook());
        System.out.println("items:" + responses.getItems());
    }

    /**
     * 批量删除
     */
    @Test
    public void test6() throws IOException {
        //创建批量删除请求对象
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        //客户端发送请求，获取响应对象
        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println("took:" + responses.getTook());
        System.out.println("items:" + responses.getItems());
    }




    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    private static class User {
        private String name;
        private Integer age;
        private String sex;
    }

}
