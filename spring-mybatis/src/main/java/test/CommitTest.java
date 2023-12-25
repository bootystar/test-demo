package test;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bean.Goods;
import service.GoodsService;

public class CommitTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		GoodsService bean = context.getBean(GoodsService.class);
		System.out.println(bean.getClass());
		Goods goods = new Goods();
		goods.setId(2);
		goods.setSalesprice(new BigDecimal(79));
		bean.updateGoods(goods);

	}
}
