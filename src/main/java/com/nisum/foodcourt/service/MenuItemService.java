package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.MenuItem;
import com.nisum.foodcourt.model.MenuItemDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MenuItemService {

    /**
     * Getting list of available menu item
     *
     * @return
     */

    ResponseEntity<?> getAllMenuItems();

    /**
     * Getting menu item by id
     *
     * @param id
     * @return
     */
    ResponseEntity<?> getMenuById(Integer id);

    /**
     * Creating new Menu item
     *
     * @param menuItemDtoList
     * @return
     */
    ResponseEntity<?> persistMenuItem(List<MenuItemDto> menuItemDtoList);

    /**
     * Updating full or partial menu object
     *
     * @param menuItemDTO
     * @return
     */
    ResponseEntity<?> updateMenuItem(MenuItemDto menuItemDTO);

    /**
     * updating the availability of menu items
     *
     * @param menuItemDtoList
     * @return
     */
    ResponseEntity<?> updateAvailability(List<MenuItemDto> menuItemDtoList);

    /**
     * soft delete menu item
     *
     * @param id
     * @return
     */
    ResponseEntity<?> deleteMenuItem(Integer id);

    /**
     * Getting cached menu item list
     *
     * @return
     */
    Map<Integer, MenuItem> getMenuItemListCache();

}
