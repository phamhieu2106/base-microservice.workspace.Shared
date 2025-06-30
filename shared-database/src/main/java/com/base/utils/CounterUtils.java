package com.base.utils;

import com.base.constant.DigitType;
import com.base.domain.entity.CounterEntity;
import com.base.repository.CounterRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CounterUtils {
    private final CounterRepository counterRepository;

    /**
     * @param clazz:     Aggregate.class
     * @param digitType: @{@link DigitType}
     * @param prefix:    prefixCode
     * @return String as a code of Entity like "M000001" base on current Year reset counter each Year
     */
    @Transactional(rollbackFor = Exception.class)
    public String generateCode(Class<?> clazz, String digitType, String prefix) {
        CounterEntity counter = counterRepository.findByCode(clazz.getSimpleName()).orElse(null);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (ObjectUtils.isEmpty(counter)) {
            counter = CounterEntity.builder()
                    .code(clazz.getSimpleName() + calendar.get(Calendar.YEAR))
                    .value(1)
                    .build();
        } else {
            counter.setValue(counter.getValue() + 1);
        }
        counterRepository.save(counter);
        int value = counter.getValue();
        String code = String.format(digitType, prefix, counter.getValue());
        do {
            if (counterRepository.existsByCode(code)) {
                code = String.format(digitType, prefix, ++value);
            }
        } while (counterRepository.existsByCode(code));
        return code;
    }
}
