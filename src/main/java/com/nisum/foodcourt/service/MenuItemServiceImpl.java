package com.nisum.foodcourt.service;

import com.nisum.foodcourt.entity.MenuItem;
import com.nisum.foodcourt.model.MenuItemDto;
import com.nisum.foodcourt.repository.MenuRepository;
import com.nisum.foodcourt.resource.ResponseExceptionMessage;
import com.nisum.foodcourt.resource.util.ConversionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private static final Map<Integer, MenuItem> menuItemListCache = new HashMap<>();

    private final MenuRepository menuRepository;

    private final ConversionUtil conversionUtil;

    @PostConstruct
    public void init() {
        log.info("initializing menu cache on startup: ");
        menuRepository.findAll()
                .forEach(this::insertIntoCache);
    }

    @Override
    public ResponseEntity<?> getAllMenuItems() {
        List<MenuItem> menuItemList = new ArrayList<>(menuItemListCache.values());
        return menuItemList.isEmpty() ?
                ResponseEntity.badRequest().body(ResponseExceptionMessage.MENU_NOT_FOUND.getMessage()) :
                ResponseEntity.ok(conversionUtil.mapEntityListToModelList(menuItemList, MenuItemDto.class));
    }

    @Override
    public ResponseEntity<?> getMenuById(Integer id) {
        MenuItem menuItem = menuItemListCache.get(id);
        return Objects.isNull(menuItem) ?
                ResponseEntity.badRequest().body(ResponseExceptionMessage.MENU_NOT_FOUND) :
                ResponseEntity.ok(conversionUtil.mapEntityToModel(menuItem, MenuItemDto.class));
    }

    @Override
    @Transactional
    public ResponseEntity<?> persistMenuItem(List<MenuItemDto> menuItemDtoList) {
        List<MenuItem> menuItemList = conversionUtil.mapModelListToEntityList(menuItemDtoList, MenuItem.class);
        menuItemList = menuRepository.saveAll(menuItemList);
        menuItemList.forEach(this::insertIntoCache);
        return ResponseEntity.ok(menuItemList);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateMenuItem(MenuItemDto menuItemDTO) {
        ResponseEntity<?> responseEntity;
        MenuItem menuItem;

        if (menuRepository.existsById(menuItemDTO.getId())) {
            menuItem = conversionUtil.mapModelToEntity(menuItemDTO, MenuItem.class);
            responseEntity = ResponseEntity.ok(menuRepository.save(menuItem));
            insertIntoCache(menuItem);
        } else {
            responseEntity = ResponseEntity.badRequest().body(ResponseExceptionMessage.MENU_NOT_FOUND.getMessage());
        }
        return responseEntity;
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateAvailability(List<MenuItemDto> menuItemDtoList) {
        ResponseEntity<?> responseEntity;
        try {
            List<MenuItem> menuItemList = menuRepository.findAllById(conversionUtil.getIdList(menuItemDtoList));
            responseEntity = menuItemList.size() == menuItemDtoList.size() ?
                    ResponseEntity.badRequest().body(ResponseExceptionMessage.MENU_NOT_FOUND.getMessage()) :
                    ResponseEntity.ok(conversionUtil.mapEntityListToModelList(saveItemAvailability(menuItemDtoList, menuItemList), MenuItemDto.class));
        } catch (Exception ec) {
            responseEntity = ResponseEntity.badRequest().body(ec.getMessage());
        }

        return responseEntity;

    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteMenuItem(Integer id) {
        ResponseEntity responseEntity;
        MenuItem menuItem = menuItemListCache.get(id);
        if (Objects.isNull(menuItem)) {
            responseEntity = ResponseEntity.badRequest().body(ResponseExceptionMessage.MENU_NOT_FOUND.getMessage());
        } else {
            menuRepository.softDeleteMenu(id);
            menuItemListCache.remove(id);
            responseEntity = ResponseEntity.ok(ResponseExceptionMessage.MENU_DELETED_SUCCESSFULLY.getMessage());
        }

        return responseEntity;
    }

    @Override
    public Map<Integer, MenuItem> getMenuItemListCache() {
        return menuItemListCache;
    }

    private List<MenuItem> saveItemAvailability(List<MenuItemDto> itemDTOList, List<MenuItem> itemList) {
        itemList.forEach(item -> {
            item.setAvailability(itemDTOList.stream()
                    .filter(itemDTO -> Objects.equals(item.getId(), itemDTO.getId()))
                    .findFirst().get()
                    .getAvailability());
        });
        List<MenuItem> menuItemList = menuRepository.saveAll(itemList);
        menuItemList.forEach(this::insertIntoCache);
        return menuItemList;
    }

    private void insertIntoCache(MenuItem menuItem) {
        menuItemListCache.put(menuItem.getId(), menuItem);
    }
}
