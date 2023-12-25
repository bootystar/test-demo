package dataStructure.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 求最小生成树的 方式
 * <p>
 * 修路问题
 * 现有n个村庄,每个村庄之间的距离为nm
 * 先需要修路,求最短路径
 * 如下图,A\B\C\D\E\F\G 为村庄
 * 数字为每个村庄与其之间的距离,
 * 现修路,保证每个村庄都连通,求最短路径
 *         A - 5 -  B
 *       /  \      / \
 *      7    2    3   9
 *     /      \ /      \
 *    C        G        D
 *     \      / \      /
 *      8    4   6    4
 *       \  /     \  /
 *        E - 5 -  F
 *
 * @author booty
 * @date 2021/7/12 11:20
 */
public class MinimumSpanningTree {

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
        Graph<String> graph = new Graph<>(strings.size());
        strings.forEach(graph::addVertex);
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 7);
        graph.addEdge("A", "G", 2);
        graph.addEdge("B", "G", 3);
        graph.addEdge("B", "D", 9);
        graph.addEdge("G", "E", 4);
        graph.addEdge("G", "F", 6);
        graph.addEdge("C", "E", 8);
        graph.addEdge("E", "F", 5);
        graph.addEdge("D", "F", 4);
        prim(graph).forEach(System.out::println);
        System.out.println();
        kruskal(graph).forEach(System.out::println);
    }

    /**
     * 创建最小生成树
     * 普利姆算法,寻找最短路径
     * 基于节点
     *
     * @return 最小生成树方式
     */
    public static List<String> prim(Graph graph) {
        //装入结果
        List<String> wayList = new ArrayList<>();
        //标记节点是否已经访问过
        boolean[] visited=new boolean[graph.vertexList.size()];
        //开始点
        visited[0]=true;
        //节点1的下标
        int index1=-1;
        //节点2的下标
        int index2=-1;
        int minWeight = 0;
        //需要添加的最小路径数量=节点数量-1 ,这样才能保证连通(也可以用while判断标记已访问节点数组的值是否都为true)
        for (int k = 0; k < graph.vertexList.size()-1; k++) {
            //当前节点
            for (int i = 0; i <graph.vertexList.size(); i++) {
                //当前节点与其他节点的路径连接
                for (int j = 0; j < graph.vertexList.size(); j++) {
                    // 判断条件:点1走过,点2未走过
                    // 路径权值不为默认值0 ,
                    // 当最小权值为0时,当前路径大于最小权值
                    // 当路径权值不为0时,小于当前
                    if (    visited[i] &&
                            !visited[j] &&
                            graph.edges[i][j] != 0 &&
                            (graph.edges[i][j] < minWeight ||
                                    (minWeight==0 && graph.edges[i][j]>minWeight))) {
                        //记录最小权值(路径)和其对应的坐标
                        minWeight = graph.edges[i][j];
                        index1 = i;
                        index2 = j;
                    }
                }
            }
            //最小权值初始化
            minWeight=0;
            visited[index2]=true;
            wayList.add(graph.vertexList.get(index1)+"=>"+graph.vertexList.get(index2)+";weight= "+ graph.edges[index1][index2]);
        }
        return wayList;
    }


    /**
     * kruskal求最小树
     * 克鲁斯卡尔算法
     * 基于边,优先寻找最短的边,只要与之前的边不形成回路
     * 只需要找到最小数量的边即可(节点数-1)
     *
     * @return 放置方式集合
     */
    public static List<String> kruskal(Graph graph){
        //结果的合集
        List<String> result=new ArrayList<>();

        //所有边的合集
        List<Edge> edgeList=new ArrayList<>();

        //所有边的终点的合集
        int [] ends= new int[graph.edgeNum];

        //将二维数组中的边存放到边集合中
        for (int i = 0; i < graph.edges.length; i++) {
            for (int j = 0; j < graph.edges[i].length; j++) {
                if(graph.edges[i][j]!=0){
                    Edge edge = new Edge(i, j, graph.edges[i][j]);
                    edgeList.add(edge);
                }
            }
        }
        //从小到大排序并遍历
        edgeList.stream().sorted().forEach(e->{
            //该边的开始点
            Integer start = e.start;
            //该边的结束点
            Integer end = e.end;

            //获取两个节点对应的结束点是那个点
            int startFinalPoint = getEnd(ends, start);
            int endFinalPoint = getEnd(ends, end);
            //开始点指向的终点和结束点指向的终点不同
            if (startFinalPoint!=endFinalPoint){
                //将开始点的终点设置为该边的结束点(往后延伸)
                ends[startFinalPoint]=endFinalPoint;
                //将该边加入集合
                result.add(graph.vertexList.get(e.start)+"=>"+graph.vertexList.get(e.end)+";weight: "+e.weight);
            }
        });
        return result;
    }


    /**
     * while循环获取指定节点路径对应的终点
     * 克鲁斯卡尔算法使用
     * (也可递归)
     * @param ends 终点数组
     * @param vertex 指定节点下标
     * @return 终点下标
     */
    private static Integer getEnd(int[] ends,int vertex){
        //当该节点的终点节点的值不为0,代表其还有终点, 取其终点的终点(最后一个终点)
        while(ends[vertex]!=0){
            vertex=ends[vertex];
        }
        return vertex;
    }


//    /**
//     * 迪杰斯特拉算法
//     * 获取从某个节点到其他节点的最短路径
//     * @param graph     储存路径关系的图
//     * @param vertexIndex  指定节点
//     */
//    public static List<String> dijkstra(Graph graph,int vertexIndex){
//        ArrayList<String> result = new ArrayList<>();
//        VisitedVertex visitedVertex = new VisitedVertex(graph.vertexList.size(), vertexIndex);
//
//        return result;
//    }
//
//
//    /**
//     * 更新index下标指定节点到周围节点的距离和周围节点的前驱节点
//     * @param visitedVertex 已访问节点的集合
//     * @param index 指定节点
//     */
//    private static void update(Graph graph, VisitedVertex visitedVertex, int index){
//        int length=0;
//        for (int current = 0; current < graph.edges[index].length; current++) {
//            //若距离为0说明两个节点之间无连接,使用最大值表示,若联通 使当前节点距离加上指定节点已有距离
//            //当前节点至指定节点的距离length = 指定节点已有的距离(可能中途有其他节点)  +  当前节点 至 指定节点的距离
//            length = graph.edges[index][current] == 0 ? Integer.MAX_VALUE : visitedVertex.getDistance(index) + graph.edges[index][current];
//            //若当前节点未被访问过,或其距离小于当前已设置的距离 ,将其距离设置为当前距离
//            if (!visitedVertex.isVisited(current) || length < visitedVertex.getDistance(current)) {
//                visitedVertex.updateDistance(current, length);
//            }
//        }
//
//    }
//
//
//
//    /**
//     * 迪杰斯特拉算法使用
//     * 已访问顶点的集合
//     */
//    private static class VisitedVertex {
//        //记录各个顶点是否访问过,1表示已访问,0表示未访问
//        private boolean[] already;
//        //每个下标对应的值为前一个顶点对应的下标,会动态更新
//        private int[] preVisited;
//        //记录从该顶点到其他顶点的距离
//        private int[] distance;
//
//        /**
//         * 构造方法
//         * @param length 节点数量
//         * @param index  当前节点下标
//         */
//        public VisitedVertex(int length, int index) {
//            already=new boolean[length];
//            preVisited =new int[length];
//            distance=new int[length];
//            //默认距离最大(不连通)
//            Arrays.fill(distance,Integer.MAX_VALUE);
//            //出发点与出发点自身的距离为0
//            distance[0]=0;
//            //出发点已访问
//            already[index]=true;
//        }
//
//        /**
//         * 判断顶点是否已经被访问过
//         * @param index 下标
//         * @return 是否访问
//         */
//        public boolean isVisited(int index){
//            return already[index];
//        }
//
//        /**
//         * 更新到指定顶点的距离
//         * @param index 指定顶点
//         * @param newDistance 距离
//         */
//        public void updateDistance(int index,int newDistance){
//            distance[index]=newDistance;
//        }
//
//        /**
//         * 更新指定节点的前驱节点
//         * @param index   指定节点下标
//         * @param pre     新的前驱节点下标
//         */
//        public void updatePre(int index,int pre){
//            preVisited[index]=pre;
//        }
//
//        /**
//         * 返回出发节点到指定节点的距离
//         * @param index 指定节点
//         * @return 指定距离
//         */
//        public int getDistance(int index){
//            return distance[index];
//        }
//
//        /**
//         * 获取下一个需要访问的顶点(最短路径)
//         * @return 下一个顶点下标
//         */
//        public int next(){
//            int min =Integer.MAX_VALUE,index=0;
//            //找出未访问过的,距离最短的节点
//            for (int i = 0; i < already.length; i++) {
//                if(!already[i] && distance[i]<min){
//                    index=i;
//                    min=distance[i];
//                }
//            }
//            //将获取的节点标记为已访问
//            already[index]=true;
//            return index;
//        }
//    }





    /**
     * 边所对应的对象类
     * 克鲁斯卡尔算法使用
     */
    private static class Edge implements Comparable<Edge>{
        /**
         * 开始节点
         */
        private Integer start;
        /**
         * 结束节点
         */
        private Integer end;
        /**
         * 权值
         */
        private Integer weight;

        public Edge(Integer vertex1, Integer vertex2, Integer weight) {
            this.start = vertex1;
            this.end = vertex2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight-o.weight;
        }
    }






    /**
     * 图
     * 储存数据的图
     * @param <T> 泛型
     */
    private static class Graph<T> {
        private List<T> vertexList;
        private int[][] edges;
        private int edgeNum;

        /**
         * 构造器
         *
         * @param size 节点个数
         */
        public Graph(int size) {
            this.vertexList = new ArrayList<>(size);
            edges = new int[size][size];
            edgeNum = 0;
        }

        /**
         * 添加节点
         *
         * @param t 节点
         * @return 节点
         */
        public Graph<T> addVertex(T t) {
            vertexList.add(t);
            return this;
        }

        /**
         * 添加节点之间的关系和权值
         *
         * @param t1     节点1
         * @param t2     节点2
         * @param weight 权值
         * @return 自身
         */
        public Graph<T> addEdge(T t1, T t2, int weight) {
            Integer index1 = null, index2 = null;
            for (int i = 0; i < vertexList.size(); i++) {
                if (vertexList.get(i).equals(t1)) {
                    index1 = i;
                }
                if (vertexList.get(i).equals(t2)) {
                    index2 = i;
                }
            }
            assert index1 != null;
            assert index2 != null;
            edges[index1][index2] = weight;
            edges[index2][index1] = weight;
            edgeNum++;
            return this;
        }

        /**
         * 获取两个节点之间的权值
         *
         * @param v1 节点1
         * @param v2 节点2
         * @return 权值
         */
        public Integer getWeight(T v1, T v2) {
            Integer index1 = null, index2 = null;
            for (int i = 0; i < vertexList.size(); i++) {
                if (vertexList.get(i).equals(v1)) index1 = i;
                if (vertexList.get(i).equals(v2)) index2 = i;
            }
            if (index1 == null || index2 == null) return null;
            return edges[index1][index2];
        }

        /**
         * 遍历图矩阵
         */
        public void showGraph() {
            System.out.print("   ");
            vertexList.forEach(e -> System.out.print(e + "  "));
            System.out.println();
            for (int i = 0; i < edges.length; i++) {
                System.out.print(vertexList.get(i) + " ");
                System.out.println(Arrays.toString(edges[i]));
            }
        }
    }
}
