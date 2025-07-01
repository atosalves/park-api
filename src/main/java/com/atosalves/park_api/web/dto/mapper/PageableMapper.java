package com.atosalves.park_api.web.dto.mapper;

import org.springframework.data.domain.Page;

import com.atosalves.park_api.web.dto.PageableDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

        public static <T> PageableDto<T> toDto(Page<T> page) {
                return new PageableDto<T>(page.getContent(), page.isFirst(), page.isLast(), page.getNumber(),
                                page.getSize(), page.getNumberOfElements(), page.getTotalPages(),
                                page.getTotalElements());
        }

}
