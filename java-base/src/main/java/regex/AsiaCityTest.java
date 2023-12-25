package regex;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author booty
 * @date 2021/5/24 14:18
 */
public class AsiaCityTest {
    private final static Map<String, Pro> MAP = new ConcurrentHashMap<>();
    private final static List<Pro> PROS = new CopyOnWriteArrayList<>();

    static {
        //遍历省列表，新建省信息
        Pro pro = new Pro("chengdu", "1", new HashMap<>());

        //遍历当前省的城市列表，放入省信息
        City chengdu = new City("chengdu", "1");
        City chongqing = new City("chongqing", "2");

        Map<String, City> cityMap = pro.getCityMap();
        cityMap.put(chengdu.getSerial(), chengdu);
        cityMap.put(chongqing.getSerial(), chongqing);
        MAP.put(pro.getSerial(), pro);

        //map封装完毕后,封装list
        Set<Map.Entry<String, Pro>> entries = MAP.entrySet();
        for (Map.Entry<String, Pro> entry : entries) {
            PROS.add(entry.getValue());
        }
    }


    public static void main(String[] args) {
        System.out.println(MAP.toString());
        City city1 = MAP.get("1").getCityMap().get("1");
        City city2 = MAP.get("1").getCityMap().get("2");
        System.out.println(city1);
        System.out.println(city2);
        System.out.println(PROS);
        System.out.println(getCityList("1"));
    }

    //获取指定省
    static Pro getPro(String serial) {
        return MAP.get(serial);
    }

    //获取指定市
    static City getCity(String proSerial, String citySerial) {
        return MAP.get(proSerial).getCityMap().get(citySerial);
    }

    //获取省列表
    static List<Pro> getProList() {
        return PROS;
    }

    //获取某省的市列表
    static List<City> getCityList(String proSerial) {
        Map<String, City> cityMap = MAP.get(proSerial).getCityMap();
        Set<Map.Entry<String, City>> entries = cityMap.entrySet();
        List<City> cityList = new ArrayList<>();
        for (Map.Entry<String, City> entry : entries) {
            cityList.add(entry.getValue());
        }
        return cityList;
    }

}

@Data
@AllArgsConstructor
class Pro {
    String name;
    String serial;
    Map<String, City> cityMap;
}

@Data
@AllArgsConstructor
class City {
    String name;
    String serial;
}