package com.github.superrelax102.reservationsystem.service;

import java.util.List;

import com.github.superrelax102.reservationsystem.dto.MenuResponseDto;
import com.github.superrelax102.reservationsystem.entity.Menu;
import com.github.superrelax102.reservationsystem.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    private MenuResponseDto convertToDto(Menu entity) {
        return MenuResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .detail(entity.getDetail())
                .formattedPrice(String.format("%,d円", entity.getCurrentfee()))
                .formattedDuration(formatDuration(entity.getDuration()))
                .build();
    }

    private String formatDuration(Integer minutes) {
        if (minutes == null) return "0分";
        int h = minutes / 60;
        int m = minutes % 60;
        String dispDuretion;
        String dispDuretionH = "";
        String dispDuretionM = "";

        if (h > 0) {
            dispDuretionH = h + "時間";
        }
        if (m > 0) {
            dispDuretionM = m + "分";
        }
        dispDuretion = dispDuretionH + dispDuretionM;
        return dispDuretion;
    }
}
