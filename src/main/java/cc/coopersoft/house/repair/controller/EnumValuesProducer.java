package cc.coopersoft.house.repair.controller;


import cc.coopersoft.house.repair.data.PaymentType;
import cc.coopersoft.house.repair.data.model.RepairBusinessEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class EnumValuesProducer {

    @Produces
    @Named
    @ApplicationScoped
    private PaymentType[] paymentTypes(){
        return PaymentType.values();
    }

    @Produces
    @Named
    @ApplicationScoped
    private RepairBusinessEntity.SplitType[] splitTypes(){
        return RepairBusinessEntity.SplitType.values();
    }

    @Produces
    @Named
    @ApplicationScoped
    private List<RoundingMode> splitCalcType(){
        List<RoundingMode> result = new ArrayList<>(4);
        result.add(RoundingMode.FLOOR);
        result.add(RoundingMode.CEILING);
        result.add(RoundingMode.HALF_UP);
        result.add(RoundingMode.HALF_EVEN);
       return result;
    }
}
