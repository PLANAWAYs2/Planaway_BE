package backend.planawaypracticeV3.controller.users;

import backend.planawaypracticeV3.domain.TravelItem;
import backend.planawaypracticeV3.domain.User;
import backend.planawaypracticeV3.dto.response.MessageResponse;
import backend.planawaypracticeV3.repository.TravelItemRepository;
import backend.planawaypracticeV3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/travel-checklist")
public class TravelItemController {

    private final UserService userService;

    @GetMapping("/{planId}")
    public ResponseEntity<List<TravelItem>> getTravelItemsByPlanId(@PathVariable Long planId) {
        // planId에 해당하는 여행 계획의 준비물 리스트를 조회하여 반환
        List<TravelItem> itemList = userService.getTravelItemsByPlanId(planId);

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PostMapping("/{planId}")
    public ResponseEntity<TravelItem> addTravelItem(@PathVariable Long planId, @RequestParam String itemName) {
        TravelItem addedItem = userService.addTravelItem(planId, itemName);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<TravelItem> updateTravelItemName(@PathVariable Long itemId, @RequestParam String newName) {
        TravelItem updatedTravelItem = userService.updateTravelItemName(itemId, newName);
        return ResponseEntity.ok().body(updatedTravelItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteTravelItem(@PathVariable Long itemId) {
        userService.deleteTravelItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
