package io.github.pleuvoir;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.github.pleuvoir.chapter04.Config4;

public class ChapterTest04 {

	@Test
	public void context() {
		
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Config4.class);
		
		System.out.println("容器初始化完成。。。");
		app.close();
	
	}

}
