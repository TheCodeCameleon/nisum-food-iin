package com.nisum.foodcourt.controller;

import com.nisum.foodcourt.model.MenuItemDto;
import com.nisum.foodcourt.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<?> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuItemById(@PathVariable("id") Integer id) {
        return menuItemService.getMenuById(id);
    }

    @PostMapping("/persist")
    public ResponseEntity<?> persistMenu(@RequestBody List<MenuItemDto> menuItemDtoList) {
        return menuItemService.persistMenuItem(menuItemDtoList);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMenuItem(@RequestBody MenuItemDto menuItemDTO) {
        return menuItemService.updateMenuItem(menuItemDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAvailability(@RequestBody List<MenuItemDto> menuItemDtoList) {
        return menuItemService.updateAvailability(menuItemDtoList);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable("id") Integer id) {
        return menuItemService.deleteMenuItem(id);
    }
}
