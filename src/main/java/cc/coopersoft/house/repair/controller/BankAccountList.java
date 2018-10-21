package cc.coopersoft.house.repair.controller;

import cc.coopersoft.house.repair.data.model.BankAccountEntity;
import cc.coopersoft.house.repair.services.BankAccountService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class BankAccountList {

    @Inject
    private BankAccountService bankAccountService;

    @Produces
    @RequestScoped
    @Named
    public List<BankAccountEntity> bankAccounts(){
        return bankAccountService.getBankAccounts();
    }
}
