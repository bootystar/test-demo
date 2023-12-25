package client.demo2;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 实体类
 * 注解@XmlRootElement(name="user")，指定该类转化为xml标签时的根元素名称
 *
 * @author booty
 * @date 2021/6/30 15:07
 */
@Data
@ToString
@XmlRootElement(name="user")
@Accessors(chain = true)
public class User {
    int id;
    String name ;
    int age;
    Car car;
}
