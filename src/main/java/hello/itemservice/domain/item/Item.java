package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// @Data: @Getter, @Setter등을 자동생성하기 위한 용도
@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price=0; // 값이 정해 지지 않은 상황, null에 대해서도 표현하기 위해 Wrapper 객체 사용
    private Integer quantity; // 값이 정해 지지 않은 상황, null에 대해서도 표현하기 위해 Wrapper 객체 사용

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
