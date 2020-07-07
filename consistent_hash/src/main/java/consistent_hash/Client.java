package consistent_hash;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        //虚拟节点数量
        int vnodeCount = 10;
        //物理节点数量
        int nodeCount = 10;
        //插入数据总量
        int dataCount = 1000000;
        List<Node> nodes = new ArrayList<>();
        HashCircle hashCircle = new SortedMapCircle();


        long begin1 = System.currentTimeMillis();
        for (int i = 0; i < nodeCount; i++) {
            Node node = new Node(String.format("10.0.45.%s", 190 + i), "12001");
            hashCircle.insertNode(node);
            insertVNode(node, vnodeCount, hashCircle);
            nodes.add(node);
        }
        long end1 = System.currentTimeMillis();


        long begin = System.currentTimeMillis();
        for (int i = 0; i < dataCount; i++) {
            //保证数据内容哈希值每次试验一致且尽量均衡。
            String key = MD5Utils.encode(i + "");
            hashCircle.getNode(key).add(key, key);
        }
        long end = System.currentTimeMillis();

        int count = 0;
        List<Integer> sizeList = new ArrayList<>();
        int offset = 1;
        for (Node node : nodes) {
            System.out.println(String.format("物理节点%s的缓存数据总数:%s", offset++, node.size()));
            sizeList.add(node.size());
            count += node.size();
        }

        System.out.println(String.format("%s个物理节点缓存数据总数:%s", nodeCount, count));
        System.out.println(String.format("初始化节点总耗时（物理节点：%s;虚拟节点：%s）:%sms", nodeCount, vnodeCount * nodeCount, end1 - begin1));
        System.out.println(String.format("插入数据规模为%s数据总耗时:%sms", dataCount, (end - begin)));
        System.out.println(String.format("数据规模%s下%s个物理节点（每个包含%s个节点虚拟节点）标准差:%s", dataCount, nodeCount, vnodeCount, standardDeviation(sizeList)));

    }


    private static void insertVNode(Node node, int vnodeCount, HashCircle hashCircle) {
        for (int i = 0; i < vnodeCount; i++) {
            Cache tmp = new VNode(node, node.getIp(), String.valueOf(Integer.valueOf(node.getPort()) + i + 1));
            hashCircle.insertNode(tmp);
        }
    }


    /**
     * 标准差
     * @param input
     * @return
     */
    private static double standardDeviation(List<Integer> input) {
        int sum = 0;
        for (Integer tmp : input) {
            sum += tmp;      //求出数组的总和
        }
        double average = sum / input.size();  //求出数组的平均数
        int total = 0;
        for (Integer tmp : input) {
            total += (tmp - average) * (tmp - average);   //求出方差，如果要计算方差的话这一步就可以了
        }
        return Math.sqrt(total / input.size());   //求出标准差

    }

}
