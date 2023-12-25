package dataStructure.Tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据压缩的编码与解码
 * 利用赫(哈)夫曼树所有的数据都放在子叶节点上的特性(前缀码不会相同)
 * 将二叉树的左节点设置为0,右节点设置为1,
 * 子叶节点代码某个字符的编码,子叶节点的权重代表出现的次数
 * 将从根节点出发到子节点的路径作为该文字
 *
 * @author booty
 * @date 2021/7/5 15:30
 */
public class HuffmanCode {

    static String content = "在日常计算机的使用中，我们一定会出现下面这种情况：" +
            "假如给定a、b、c、d、e五个字符," +
            "我们现在要将文本编码成0/1序列从而使得计算机能够进行读取和计算。" +
            "为了保证每个字符的独一性，所以我们给予不同的的字符以不同的编码。" +
            "如果给每个字符赋予等长的编码的话，会使得平均的编码长度过长，影响计算时的性能，浪费计算机的资源(定长编码的缺点)。" +
            "这时我们就想到了变长编码，理所当然的，给出现概率较大的字符赋予较短的编码，概率较小的字符赋予较长的编码，" +
            "这样在计算的时候不就可以节省很多时间了吗？" +
            "可这样我们又面临到了一个巨大的问题:" +
            "假设现在文本中的字符是bcd，转换之后的0/1序列为00010，" +
            "可我们要在转换成文本的时候究竟是把第一位的0读作b还是把前两位的00读作c呢？" +
            "为了解决这个问题，就又有了前缀码的概念。" +
            "顾名思义，前缀码的含义就是任意字符的编码都不是其他字符编码的前缀" +
            "哈夫曼树的带权路径最小，所以有哈夫曼树构成的前缀码记作哈夫曼编码。哈夫曼作为已知的最佳无损压缩算法，满足前缀码的性质，可以即时解码" +
            "实现哈夫曼编码的主要思路为从指定的文件中读出文本，首先通过遍历获得各个字符出现的概率，根据出现概率的大小构造二叉树，在此基础上进行编码解码";
    static String string = "i like like like java do you like a java";

    public static void main(String[] args) {
        HuffMessage huffMessage = zipHuffmanCode(content);
        for (byte b : huffMessage.data) System.out.print(b + " ");
        System.out.println();
        //压缩前,数据长度1483
        System.out.println(content.getBytes(StandardCharsets.UTF_8).length);
        //压缩后1054
        System.out.println(huffMessage.data.length);

        huffMessage.dic.entrySet().forEach(System.out::println);
        String str = new String(decodeHuffmanMessage(huffMessage.data, huffMessage.dic));
        System.out.println(str);
    }


    /**
     * 赫夫曼编码
     * 将字符串通过赫夫曼编码缩短长度
     *
     * @param content 字符串
     * @return 赫夫曼编码后的数据, 字典, 赫夫曼树
     */
    public static HuffMessage zipHuffmanCode(String content) {

        //获取赫夫曼树
        CodeNode rootNode = getTreeRootNode(content.getBytes(StandardCharsets.UTF_8));

        //设置赫夫曼树的通路(节点路径)
        setCodeWay(rootNode);

        //使用路径作为key,byte编码作为value,生成字典
        HashMap<Byte, String> dic = new HashMap<>();
        getDicMap(rootNode, dic);

        //通过字典,进行编码
        byte[] codingBytes = coding(content.getBytes(StandardCharsets.UTF_8), dic);
        return new HuffMessage(codingBytes, dic);
    }


    /**
     * 根据传入字符串获取赫夫曼树
     *
     * @param sourceBytes 需要转化的源数据
     * @return 赫夫曼树root节点
     */
    private static CodeNode getTreeRootNode(byte[] sourceBytes) {
        Map<Byte, Integer> map = new HashMap<>();
        for (byte one : sourceBytes) {
            map.put(one, map.get(one) == null ? 1 : map.get(one) + 1);
        }
        List<CodeNode> list = map.entrySet().stream()
                .map(e -> new CodeNode(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        while (list.size() > 1) {
            list.sort(CodeNode::compareTo);
            CodeNode left = list.get(list.size() - 2);
            left.way = "0";
            CodeNode right = list.get(list.size() - 1);
            right.way = "1";
            CodeNode parent = new CodeNode(null, left.weight + right.weight);
            parent.left = left;

            parent.right = right;
            list.remove(left);
            list.remove(right);
            list.add(parent);
        }
        CodeNode root = list.get(0);
        //根路径设置为空
        root.way = "";
        return root;
    }

    /**
     * 依次将赫夫曼树的节点路径设置好
     * (左路为0,右路为1)
     *
     * @param root 根节点
     */
    private static void setCodeWay(CodeNode root) {
        //左路为0
        if (root.left != null) {
            root.left.way = root.way.concat("0");
            setCodeWay(root.left);
        }
        //右路为1
        if (root.right != null) {
            root.right.way = root.way.concat("1");
            setCodeWay(root.right);
        }
    }

    /**
     * 获取编码字典
     *
     * @param node 当前节点
     * @param map  存放数据的字典
     */
    private static void getDicMap(CodeNode node, Map<Byte, String> map) {
        if (node != null) {
            getDicMap(node.left, map);
            getDicMap(node.right, map);
            //当编码数据不为空时将数据放入字典map
            if (node.data != null) {
                map.put(node.data, node.way);
            }
        }
    }


    /**
     * 获取新的编码
     *
     * @param sourceBytes 原数据对应的byte数组
     * @param map         字典
     * @return 二进制编码
     */
    private static byte[] coding(byte[] sourceBytes, HashMap<Byte, String> map) {
        StringBuilder sb = new StringBuilder();
        //把需要压缩的byte数组处理成二进制字符串
        for (byte b : sourceBytes) {
            sb.append(map.get(b));
        }
        //定义byte数组的长度，如果被8整除，商为长度
        //不被8整除，len = 商 + 1
        int len = (sb.length() + 7) / 8;
        byte[] huffmanBytes = new byte[len];
        String code = "";
        int index = 0;
        //遍历拼串，每8位为1组，步长为8
        for (int i = 0; i < sb.length(); i += 8) {
            if (i + 8 > sb.length()) {
                //最后一次截取时,拼串中子串长度可能不足8
                code = sb.substring(i);
            } else {
                //subString 左开右闭
                code = sb.substring(i, i + 8);
            }
            //转化为二进制编码
            huffmanBytes[index++] = (byte) Integer.parseInt(code, 2);
        }
        return huffmanBytes;
    }

    /**
     * 赫夫曼解码
     *
     * @param data 编码后的数据
     * @param dic  数据字典
     * @return 源数据的byte字节
     */
    private static byte[] decodeHuffmanMessage(byte[] data, Map<Byte, String> dic) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            //byte最后一个元素不需要补齐八位
            boolean flag = (i != data.length - 1);
            String str = byteToString(flag, data[i]);
            sb.append(str);
        }
        //根据赫夫曼编码转换成对应的字符，huffmanCode中key为字符，value为编码
        //因为Map中，一个key对应一个value，而相同的value可能对应几个key，所以不能直接通过value找key
        //根据编码找字符，需要将原本的huffmanCode反转
        HashMap<String, Byte> reversCode = new HashMap<>();
        for (Map.Entry<Byte, String> entry : dic.entrySet()) {
            reversCode.put(entry.getValue(), entry.getKey());
        }
        //存放扫描的结果
        List<Byte> list = new ArrayList<>();
        //定义双指针，扫描stringBuilder中的编码字符，还原成原本的字符串
        for (int i = 0; i < sb.length(); ) {
            //i表示从第i位开始扫描,count表示每次扫描的位数,如果和reversCode中的字符匹配，加入到集合中，并下次i=i+count;
            int count = 1;
            //匹配到reversCode中对应的元素后，结束遍历
            boolean flag = true;
            Byte b = null;
            while (flag) {
                String key = sb.substring(i, i + count);
                b = reversCode.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }


    /**
     * 将byte对应的二进制编码转化为string
     *
     * @param flag 是否需要补0(字典寻路用)
     * @param b    待转换的byte
     * @return 转换后的str
     */
    private static String byteToString(boolean flag, byte b) {
        /*
        byte是负数时,最高位符号位为1,不用补,直接截取即可
            如  -1  对应的byte为: 1000 0001
                    对应的int为:  1000 0000 0000 0000 0000 0000 0000 0001
                最高符号位位1,所以显示的二进制值始终为32位,可以直接截取后8位

        byte是正数时,最高位为0,若byte的数值较小,转化为string是会省略0,此时需要补0
            如  1  对应的byte为 0000 0001
                   对应的int为 0000 0000 0000 0000 0000 0000 0000 0001
                最高位符号位为0,所以显示的二进制值就是1,此时截取字符串就会出现下标越界异常

        因 256对应的二进制值为 0000 0000 0000 0000 0000 0001 0000 0000
        byte的只有8位,所以其第9位的值始终为0,
        而256对应的第九位的值为1,剩下的低位全部为0,
        此时使用按位或,可以保证转换后第9位为1,让后面的0在转化时不会被省略

        按位或(|) : 对两个二进制的值进行运算(同为0时为0,其中一个为1就为1),
        */
        int temp = b;
        if (flag) {
            temp |= 256;
        }
        String str = Integer.toBinaryString(temp);
        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }


    /**
     * 节点类
     */
    private static class CodeNode implements Comparable<CodeNode> {
        // 字符的二进制编码
        private Byte data;
        // 字符出现的次数
        private Integer weight;
        // 赫夫曼树的路径(左路0,右路1)
        private String way;
        private CodeNode left;
        private CodeNode right;

        @Override
        public String toString() {
            return "CodeNode{" +
                    "code=" + data +
                    ", weight=" + weight +
                    ", way='" + way + '\'' +
                    '}';
        }

        public CodeNode(Byte code, Integer weight) {
            this.data = code;
            this.weight = weight;
        }

        public void preOrder() {
            System.out.println(this);
            if (left != null) {
                left.preOrder();
            }
            if (right != null) {
                right.preOrder();
            }
        }

        @Override
        public int compareTo(CodeNode o) {
            return o.weight.compareTo(weight);
        }
    }

    /**
     * 赫夫曼编码类
     */
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    private static class HuffMessage {
        private byte[] data;
        private Map<Byte, String> dic;
    }
}
