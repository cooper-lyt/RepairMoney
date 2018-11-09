package cc.coopersoft.house.repair.services;

import cc.coopersoft.framework.services.SimpleEntityService;
import cc.coopersoft.house.repair.data.AccountStatus;
import cc.coopersoft.house.repair.data.model.BankAccountEntity;
import cc.coopersoft.house.repair.data.repository.BankAccountRepository;
import org.apache.deltaspike.data.api.EntityRepository;

import javax.inject.Inject;
import java.util.List;

public class BankAccountService extends SimpleEntityService<BankAccountEntity,String> {

    @Inject
    private BankAccountRepository bankAccountRepository;

    public List<BankAccountEntity> getBankAccounts(){
        return bankAccountRepository.findByStatusOrderByCreateTimeDescBankAccountId(AccountStatus.NORMAL);
    }

    @Override
    public EntityRepository<BankAccountEntity, String> getEntityRepository() {
        return bankAccountRepository;
    }

    @Override
    public BankAccountEntity createNew() {
        return new BankAccountEntity();
    }
}
