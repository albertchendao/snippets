package org.example.common.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 比较器工具
 */
@Slf4j
public class ComparatorHelper {

    /**
     * 支持 null 的比较器
     *
     * @param keyExtractor     从原始值获取被比较的字段
     * @param originNullsFirst 原始值为 null 时是否排最前
     * @param valueNullsFirst  被比较的字段为 null 时是否排最前
     */
    public static <T, U extends Comparable<? super U>> Comparator<T> nullsComparing(
            Function<? super T, ? extends U> keyExtractor,
            boolean originNullsFirst,
            boolean valueNullsFirst) {
        Comparator<U> valueComparator = valueNullsFirst ?
                Comparator.nullsFirst(Comparator.naturalOrder()) :
                Comparator.nullsLast(Comparator.naturalOrder());
        Comparator<? super T> innerComparator = Comparator.comparing(keyExtractor, valueComparator);
        return originNullsFirst ? Comparator.nullsFirst(innerComparator) : Comparator.nullsLast(innerComparator);
    }

    @Data
    @AllArgsConstructor
    private static class InnerDto {
        private Integer order;
    }

    public static void main(String[] args) {
        List<InnerDto> l = new ArrayList<>();
        l.add(null);
        l.add(new InnerDto(null));
        l.add(new InnerDto(1));
        l.add(new InnerDto(2));

        l.sort(ComparatorHelper.nullsComparing(InnerDto::getOrder, false, false));
        log.debug("{}", l);
    }
}

