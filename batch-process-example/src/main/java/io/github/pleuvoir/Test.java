package io.github.pleuvoir;

import io.github.pleuvoir.config.BatchConfiguration;
import io.github.pleuvoir.config.DatasourceConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext();
		app.register(DatasourceConfiguration.class);
		app.register(BatchConfiguration.class);
		app.refresh();

		SimpleFetch fetch = app.getBean(SimpleFetch.class);

		Object o = fetch.fetch();

		System.out.println(o);
	}
}
