package client.demo2;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author booty
 * @date 2021/6/30 15:07
 */
@Data
@ToString
@XmlRootElement(name="car")
@Accessors(chain = true)
public class Car {
    String name;
    Double price;
}
