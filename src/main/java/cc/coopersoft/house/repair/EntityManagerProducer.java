package cc.coopersoft.house.repair;

import cc.coopersoft.framework.BizDefine;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by cooper on 23/02/2017.
 */
public class EntityManagerProducer implements java.io.Serializable{


    @Inject
    private Logger logger;

    @PersistenceUnit(unitName = "entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @PersistenceUnit(unitName = "frameworkEntityManagerFactory")
    private EntityManagerFactory frameworkEntityManagerFactory;

    @Produces
    @BizDefine
    @ConversationScoped
    public EntityManager createFrameworkEM(){
        logger.log(Level.CONFIG," framework EntityManager: open");
        return this.frameworkEntityManagerFactory.createEntityManager();
    }

    public void disposeFrameworkEM(@Disposes @BizDefine EntityManager entityManager){
        logger.log(Level.CONFIG," framework EntityManager: close");
        if (entityManager.isOpen()){
            entityManager.close();
        }
    }


    @Produces
    @Default
    @ConversationScoped
    public EntityManager create(){
        logger.log(Level.CONFIG," EntityManager: open");
        return this.entityManagerFactory.createEntityManager();
    }


    public void dispose(@Disposes @Default EntityManager entityManager){
        logger.log(Level.CONFIG," EntityManager: close");
        if (entityManager.isOpen()){
            entityManager.close();
        }
    }
}
