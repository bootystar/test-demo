package tcp;

import lombok.Data;

/**
 * 用于储存消息的实体类
 * tcp服务在传输时，会将多个小数据整合成一个包（沾包）以提高效率
 * 若服务端未对数据包进行拆分处理（拆包），数据便会出现问题
 * @author booty
 * @date 2021/6/17 15:01
 */
@Data
public class MessageProtocol {
    /**
     * 长度
     */
    private int length;
    /**
     * 内容
     */
    private byte[] content;
}
