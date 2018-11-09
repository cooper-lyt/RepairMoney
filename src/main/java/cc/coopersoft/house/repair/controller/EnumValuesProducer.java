package cc.coopersoft.house.repair.controller;


import cc.coopersoft.house.repair.data.PaymentType;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class EnumValuesProducer {

    @Produces
    @Named
    @RequestScoped
    private PaymentType[] paymentTypes(){
        return PaymentType.values();
    }
}
