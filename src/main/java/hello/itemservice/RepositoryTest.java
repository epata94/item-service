package hello.itemservice;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class RepositoryTest {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RepositoryTest.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 웹 애플리케이션 모드를 변경
        ApplicationContext context = app.run(args);

        System.out.println("Repository Tester start..");
        ItemRepository itemRepository = new ItemRepository();
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();
        System.out.println("result = " + result);
        System.out.println("result.size() = " + result.size());

        SpringApplication.exit(context); // 애플리케이션 종료
    }
}



