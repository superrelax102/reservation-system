package com.github.superrelax102.reservationsystem.service;

import java.util.List;

import com.github.superrelax102.reservationsystem.entity.Menu;
import com.github.superrelax102.reservationsystem.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }
}
