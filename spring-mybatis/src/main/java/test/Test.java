package test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import bean.Goods;
import service.GoodsService;

public class Test {
	public static void main(String[] args) {
		Goods goods =new Goods();
	
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-context.xml");
		
		String[] names=context.getBeanDefinitionNames();
		System.out.println("===========================");
		for(String name:names) {
			System.out.println(name);
		}
		System.out.println("============================");
		GoodsService goodsService=context.getBean(GoodsService.class);
		List<Goods> selectGoods = goodsService.selectGoods(goods);
		System.out.println(selectGoods);
		
	}
	
}
