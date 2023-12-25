package es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * ES 高级查询
 * @author booty
 * @date 2021/7/22 16:09
 */
public class HighLevelSearch {

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
     *
     * @throws IOException ...
     */
    @After
    public void after() throws IOException {
        //关闭连接
        client.close();
    }


    /**
     * 查询所有索引数据
     */
    @Test
    public void test1() throws IOException {
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //将请求体添加到请求中
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
        //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * 关键字精确查询
     */
    @Test
    public void test2() throws IOException {
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //关键字精确查询指定字段和值
        sourceBuilder.query(QueryBuilders.termQuery("age", "30"));

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //将请求体添加到请求中
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
        //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * 关键字精确查询
     */
    @Test
    public void test3() throws IOException {
        // 构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        // 分页查询
        // 当前页其实索引(第一条数据的顺序号)，from = (pageNum - 1) * size)
        sourceBuilder.from(0);
        // 每页显示多少条 size
        sourceBuilder.size(10);

        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("student");
        //将请求体添加到请求中
        sourceBuilder.size(2);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
        //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 排序查询
     */
    @Test
    public void test4() throws IOException {
        //构建查询请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        //指定排序字段和方式
        sourceBuilder.sort("age", SortOrder.ASC);

        //构建查询请求
        SearchRequest request=new SearchRequest();
        request.indices("student");
        //将请求体添加到请求中
        request.source(sourceBuilder);
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);
        // 查询
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
        //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * 过滤字段查询
     */
    @Test
    public void test5() throws IOException {
        //构建查询请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());

        //查询字段过滤
        String[] excludes={};
        String[] includes={"name","age"};

        sourceBuilder.fetchSource(includes,excludes);
        //构建查询请求
        SearchRequest request=new SearchRequest();
        request.indices("student");
        //将请求体添加到请求中
        request.source(sourceBuilder);
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);
        // 查询
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * bool多条件查询(must.should,must_not)
     */
    @Test
    public void test6() throws IOException {
        //创建请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();

        //创建多条件复合查询的构造器
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        // 必须包含
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", "30"));
        // 一定不含
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name", "zhangsan"));
        // 可能包含
        boolQueryBuilder.should(QueryBuilders.matchQuery("sex", "男"));
        //将多条件构造后放入请求体
        sourceBuilder.query(boolQueryBuilder);

        //创建查询请求,将请求体添加到请求中
        SearchRequest request=new SearchRequest();
        request.indices("student");
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
        //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * 范围查询
     */
    @Test
    public void test7() throws IOException {
        //创建请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();

        //范围查询构造器
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        // 大于等于
        rangeQuery.gte("30");
        // 小于等于
        rangeQuery.lte("40");
        //将构造器放入查询体中
        sourceBuilder.query(rangeQuery);

        //创建查询请求,将请求体添加到请求中
        SearchRequest request=new SearchRequest();
        request.indices("student");
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * 模糊查询
     */
    @Test
    public void test8() throws IOException {
        //创建请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();

        //指定查询字段\值\模糊偏差字符量(补全,顺序交换,去除多余)
        sourceBuilder.query(QueryBuilders.fuzzyQuery("name","zhangsan").fuzziness(Fuzziness.ONE));

        //创建查询请求,将请求体添加到请求中
        SearchRequest request=new SearchRequest();
        request.indices("student");
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * 高亮查询
     */
    @Test
    public void test9() throws IOException {
        //创建请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();

        //构建查询方式：高亮查询
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name","zhangsan");
        //构建高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置标签前缀
        highlightBuilder.preTags("<font color='red'>");
        //设置标签后缀
        highlightBuilder.postTags("</font>");
        //设置高亮字段
        highlightBuilder.field("name");
        //设置高亮构建对象
        sourceBuilder.highlighter(highlightBuilder);

        //创建查询请求,将请求体添加到请求中
        SearchRequest request=new SearchRequest();
        request.indices("student");
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }



    /**
     * 聚合查询(最大)
     */
    @Test
    public void test10() throws IOException {
        //创建请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();

        //设置聚合查询的字段和年龄
        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("age"));

        //创建查询请求,将请求体添加到请求中
        SearchRequest request=new SearchRequest();
        request.indices("student");
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }


    /**
     * 分组统计
     */
    @Test
    public void test11() throws IOException {
        //创建请求体
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();

        //设置分组查询的字段
        sourceBuilder.aggregation(AggregationBuilders.terms("age_groupby").field("age"));

        //创建查询请求,将请求体添加到请求中
        SearchRequest request=new SearchRequest();
        request.indices("student");
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 查询匹配(此处显示的是匹配中的字段,该响应中还有分组统计后的数据)
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
        System.out.println(response);
    }









}
