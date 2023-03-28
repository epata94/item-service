package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
// 현재 컨트롤러의 BASE PATH를 적용하고 향후 @GetMapping에 적용됨
@RequestMapping("/basic/items")
// 모든 final로 정의한 사용자 정의 클래스 필드의 생성자를 자동으로 생성하는 용도로 사용
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
//        model.addAllAttribute("items", items);
        model.addAttribute("items", items);

        return "basic/items";
    }

// @PathVariable사용하는 경우 itemId는 request parameter의 itemId의 값으로 치환
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
// input tage의 name 속성값과 일치하는 파라메터 매칭
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
//        레파지토리에 먼저 데이터를 저장한다.
        itemRepository.save(item);
// 저장과정에서 문제가 없는 경우에 화면에 값을 출력한다.
        model.addAttribute("item", item);
        return "basic/item";
    }



//    @PostMapping("/add")
/*
@ModelAttribute - 요청 파라미터 처리
@ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로
입력해준다.
 */
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
// model.addAttribute("item", item); //자동 추가, 생략 가능
// @ModelAttribute을 사용한 경우에만 가능
        return "basic/item";
    }
//    @PostMapping("/add")
//  @ModelAttribute을 사용하는 경우 Model 객체를 인자에서 생략 가능
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        // model.addAttribute("item", item); // 내부적으로 model.addAttribute 함수가 수행
        return "basic/item";
    }

//    @PostMapping("/add")
//    임의의 객체는 자동으로 @ModelAttribute 가 적용됨
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }


    /**
     * PRG - Post/Redirect/Get
     * 새로 고침 오작동을 방지하기 위한 연결 방식
     */
//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * RedirectAttributes
     * redirect되었을 때 속성을 addAttribute함수를 통해 저장
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
//        return "redirect:/basic/items/" + item.getId();
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

//    @PostConstruct : 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
//    여기서는 간단히 테스트용 데이터를 넣기 위해 사용
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
