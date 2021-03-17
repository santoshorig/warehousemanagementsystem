package com.kamadhenu.warehousemanagementsystem.model.domain.graph;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Home Graph Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Home {

    @NotNull
    private Map<String, Integer> warehouseStatusMap;

    @NotNull
    private Map<String, Integer> clientStatusMap;

    @NotNull
    private Map<String, Integer> contractStatusMap;

    @NotNull
    private Map<String, Integer> inwardStatusMap;

    @NotNull
    private Map<String, Integer> srStatusMap;

    @NotNull
    private Map<String, Integer> lrStatusMap;

    @NotNull
    private Map<String, Double> inwardQuantityMap;

    @NotNull
    private Map<String, Double> outwardQuantityMap;

    @NotNull
    private Map<String, List<Double>> inwardOutwardQuantityMap;
}
