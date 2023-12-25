package server.demo2;

/**
 * @author booty
 * @date 2021/6/30 15:23
 */
public class UserServiceImpl implements UserService {
    @Override
    public void save(User user) {
        System.out.println("保存");
        System.out.println(user);
    }

    @Override
    public User get(int id) {
        User user1 = new User().setAge(20).setId(1).setName("1号")
                .setCar(new Car().setName("奔驰").setPrice(100.0))
                ;
        User user2 = new User().setAge(20).setId(2).setName("2号")
//                .setCar(new Car().setName("宝马").setPrice(100.0))
                ;
        return id == 1 ? user1 : user2;
    }

    @Override
    public void update(User user) {
        System.out.println("修改");
        System.out.println(user);
    }

    @Override
    public void delete(User user) {
        System.out.println("删除");
        System.out.println(user);
    }
}
