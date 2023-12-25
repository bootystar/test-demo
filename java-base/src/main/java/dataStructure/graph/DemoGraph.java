package dataStructure.graph;

import java.util.*;

/**
 * 图结构
 * 通过邻接表的结构创建图
 *
 * 连通图：在无向图中，若任意两个顶点vivi与vjvj都有路径相通，则称该无向图为连通图。
 * 强连通图：在有向图中，若任意两个顶点vivi与vjvj都有路径相通，则称该有向图为强连通图。
 * 连通网：在连通图中，若图的边具有一定的意义，每一条边都对应着一个数，称为权；权代表着连接连个顶点的代价，称这种连通图叫做连通网。
 * 生成树：一个连通图的生成树是指一个连通子图，它含有图中全部n个顶点，但只有足以构成一棵树的n-1条边。一颗有n个顶点的生成树有且仅有n-1条边，如果生成树中再添加一条边，则必定成环。
 * 最小生成树：在连通网的所有生成树中，所有边的代价和最小的生成树，称为最小生成树。
 *
 * @author booty
 * @date 2021/7/8 12:00
 */
public class DemoGraph<T> {


    public static void main(String[] args) {
        List<String> strings = Arrays.asList("A", "B", "C", "D", "E", "F");
        DemoGraph<String> graph = new DemoGraph<>(strings.size());
        strings.forEach(graph::addVertex);
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 1);
        graph.addEdge("A", "D", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("E", "F", 3);

        graph.showGraph();

        graph.deepFirstSearch();
        graph.boardFirstSearch();


    }

    //顶点的集合
    private List<T> vertexList;
    //储存图的邻接矩阵
    private int[][] edges;
    //边的数目
    private int numOfEdges;

    /**
     * 构造器
     *
     * @param capacity 容量
     */
    public DemoGraph(int capacity) {
        edges = new int[capacity][capacity];
        vertexList = new ArrayList<>(capacity);
        numOfEdges = 0;
    }

    /**
     * 插入结点
     *
     * @param vertex 结点
     */
    public void addVertex(T vertex) {
        vertexList.add(vertex);
    }

    /**
     * 建立结点之间的连接关系
     * (边)
     *
     * @param v1     顶点1
     * @param v2     顶点2
     * @param weight 该连接权值
     */
    public void addEdge(T v1, T v2, int weight) {
        Integer index1 = null, index2 = null;
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).equals(v1)) index1 = i;
            if (vertexList.get(i).equals(v2)) index2 = i;
        }
        if (index1 == null || index2 == null) return;
        //无向连接,互相指向
        edges[index1][index2] = weight;
        edges[index2][index1] = weight;
        numOfEdges++;
    }


    /**
     * 获取连接关系的数量
     *
     * @return 边的数量
     */
    public int getNumOfEdges() {
        return numOfEdges;
    }

    /**
     * 获取结点个数
     *
     * @return 结点个数
     */
    public int getNumOfVertex() {
        return vertexList.size();
    }

    /**
     * 获取结点之间的权值
     *
     * @param v1 结点1
     * @param v2 结点2
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
     * 打印矩阵
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

    /**
     * 深度优先遍历
     *
     * 如图下所示:
     *    A  B  C  D  E  F
     * A [0, 1, 1, 0, 0, 1]
     * B [1, 0, 2, 0, 0, 0]
     * C [1, 2, 0, 0, 0, 0]
     * D [0, 0, 0, 0, 0, 0]
     * E [0, 0, 0, 0, 0, 3]
     * F [1, 0, 0, 0, 3, 0]
     *
     *  访问A,输出A
     *      A的第一个邻接节点为B,输出B
     *          B的B之后的邻接节点为C,输出C
     *          B无其他邻接节点,回上一步
     *      A的第二个邻接节点为C,C已经输出,跳过
     *      A的第三个邻接节点为F,输出F
     *      A无其他邻接节点,回上一步
     *  访问B,B已经输出,跳过
     *  访问C,C已经输出,跳过
     *  访问D,输出D
     *      D无邻接节点,回上一步
     *  访问E,输出E
     *      E的第一个邻接节点为F,F已经输出,跳过
     *      E无其他邻接节点,回上一步
     *  访问F,F已经输出,跳过
     *  (因每次访问都会输入与其相关的邻接点,所以访问邻接点时,只需要访问自己之后的邻接点即可)
     */
    public void deepFirstSearch() {
        boolean[] isVisited = new boolean[getNumOfVertex()];
        //循环
        for (int i = 0; i < getNumOfVertex(); i++) {
            //若当前节点未被访问,访问其并输出邻接节点
            if (!isVisited[i]) {
                deepFirstSearch(isVisited, i);
            }
       }System.out.println();
    }

    /**
     * 深度优先遍历
     * @param isVisited 访问过的节点存放的数组
     * @param currentIndex 当前节点的下标
     */
    public void deepFirstSearch(boolean[] isVisited, int currentIndex) {
        //输出当前结点
        System.out.print(vertexList.get(currentIndex) + " => ");
        //将当前节点标记为已访问
        isVisited[currentIndex] = true;
        //获取下一个邻接节点
        Integer nextNeighborIndex = getNextNeighborIndex(currentIndex, currentIndex);
        //若有下一个邻接节点
        while (nextNeighborIndex != null) {
            //若下一个邻接节点未被访问过
            if (!isVisited[nextNeighborIndex]) {
                //递归,输出邻接节点及其相关的邻接节点
                deepFirstSearch(isVisited, nextNeighborIndex);
            }
            //获取当前节点的下一个邻接节点
            nextNeighborIndex = getNextNeighborIndex(currentIndex, nextNeighborIndex);
        }

    }



    /**
     * 广度优先遍历
     *
     * 如图下所示:
     *    A  B  C  D  E  F
     * A [0, 1, 1, 0, 0, 1]
     * B [1, 0, 2, 0, 0, 0]
     * C [1, 2, 0, 0, 0, 0]
     * D [0, 0, 0, 0, 0, 0]
     * E [0, 0, 0, 0, 0, 3]
     * F [1, 0, 0, 0, 3, 0]
     *
     *  访问A,入栈
     *      A的第一个邻接节点为B,入栈
     *      A的第二个邻接节点为C,入栈
     *      A的第三个邻接节点为F,入栈
     *      A无其他邻接节点,回上一步
     *  访问B,B已经入栈,跳过
     *  访问C,C已经入栈,跳过
     *  访问D,入栈
     *      D无邻接节点,回上一步
     *  访问E,入栈
     *      E的第一个邻接节点为F,F已经入栈,跳过
     *      E无其他邻接节点,回上一步
     *  访问F,F已经入栈,跳过
     *  依次弹栈输出
     *  (因每次访问都会输入与其相关的邻接点,所以访问邻接点时,只需要访问自己之后的邻接点即可)
     */
    public void boardFirstSearch() {
        boolean[] booleans = new boolean[getNumOfVertex()];
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!booleans[i]){
                //若当前节点未被访问过,访问其并输出邻接节点
                boardFirstSearch(booleans, i);
            }

        }
        System.out.println();
    }

    /**
     * 广度优先遍历
     * @param isVisited 存放访问过的节点
     * @param currentIndex 当前节点的下标
     */
    public void boardFirstSearch(boolean[]  isVisited, int currentIndex) {
        //下一个邻接节点下标
        Integer nextNeighborIndex;
        //当前节点的邻接节点队列
        LinkedList<Integer> queue = new LinkedList<>();
        //输出当前节点
        System.out.print(vertexList.get(currentIndex) + " => ");
        //标记已访问
        isVisited[currentIndex] = true;
        //入队列
        queue.offer(currentIndex);
        //循环弹出队列中的元素(外层循环时,队列中元素均为当前节点的邻接节点,)
        while (!queue.isEmpty()) {
            //当前节点下标设置为队列中取出的节点
            currentIndex = queue.poll();
            //获取当前节点的第一个邻接节点
            nextNeighborIndex = getNextNeighborIndex(currentIndex, currentIndex);
            //当前节点的邻接节点不为空,依次取出,遍历
            while (nextNeighborIndex != null) {
                //未访问过的邻接节点,输出
                if(!isVisited[nextNeighborIndex]){
                    //输出
                    System.out.print(vertexList.get(nextNeighborIndex) + " => ");
                    //标记已访问
                    isVisited[nextNeighborIndex] = true;
                    //入队列(当前节点的临接节点)
                    queue.offer(nextNeighborIndex);
                }
                //将邻接节点设置为当前节点的下一个邻接节点
                nextNeighborIndex = getNextNeighborIndex(currentIndex, nextNeighborIndex);
            }
        }



    }



    /**
     * 获取指定节点的第一个邻接节点的下标
     *
     * @param index 指定节点下标
     * @return 邻接结点下标
     */
    public Integer getFirstNeighborIndex(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] != 0) return i;
        }
        return null;
    }

    /**
     * 根据前一个邻接结点下标获取下一个邻接结点
     *
     * @param vertexIndex       结点
     * @param lastNeighborIndex 前一个邻接结点下标
     * @return 下一个邻接结点下标
     */
    public Integer getNextNeighborIndex(int vertexIndex, int lastNeighborIndex) {
        for (int i = lastNeighborIndex + 1; i < vertexList.size(); i++) {
            if (edges[vertexIndex][i] != 0) return i;
        }
        return null;
    }


}
