package cc.coopersoft.framework;

import cc.coopersoft.framework.services.EntityService;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/10/16.
 */
public abstract class EntityController<E, PK extends Serializable> extends MutableController {

    @Inject
    protected Logger logger;

    //@Inject
    //private JsfMessage<BaseMessages> messages;

    private static final long serialVersionUID = -5462396456614090423L;

    private PK id;
    protected E instance;


    /**
     * Get the managed entity, using the id from {@link #getId()} to load it from
     * the Persistence Context or creating a new instance if the id is not
     * defined.
     *
     * @see #getId()
     */
    @Transactional
    public E getInstance()
    {
        if (instance==null)
        {
            initInstance();
        }
        return instance;
    }

    /**
     * Clear the managed entity (and id), allowing the {@link EntityController} to be
     * reused.
     */
    public void clearInstance()
    {
        setInstance(null);
        setId(null);
    }

    /**
     * Load the instance if the id is defined otherwise create a new instance
     * <br />
     * Utility method called by {@link #getInstance()} to load the instance from
     * the Persistence Context if the id is defined. Otherwise a new instance is
     * created.
     *
     * @see #find()
     * @see #createInstance()
     */
    protected void initInstance()
    {
        if ( isIdDefined() )
        {

            setInstance( find() );

        }
        else
        {
            setInstance( createInstance() );
        }
    }


    /**
     * Create a new instance of the entity.
     * <br />
     * Utility method called by {@link #initInstance()} to create a new instance
     * of the entity.
     */
    protected E createInstance(){
        return getEntityService().createNew();
    }



    /**
     * Get the id of the object being managed.
     */
    public PK getId()
    {
        return id;
    }

    /**
     * Set/change the entity being managed by id.
     *
     *
     */
    public void setId(PK id)
    {
        if ( setDirty(this.id, id) )clearDirtyInstance();
        this.id = id;
    }

    protected void clearDirtyInstance(){
        setInstance(null);
    }

    /**
     * Returns true if the id of the object managed is known.
     */
    public boolean isIdDefined()
    {
        return getId()!=null && !"".equals( getId() );
    }

    /**
     * Set/change the entity being managed.
     */
    public void setInstance(E instance)
    {
        setDirty(this.instance, instance);
        this.instance = instance;
    }


    /**
     * Flush any changes made to the managed entity instance to the underlying
     * database.
     * <br />
     * If the update is successful, a log message is printed, a
     * {@link javax.faces.application.FacesMessage} is added and a transaction
     * success event raised.
     *
     * @return "updated" if the update is successful
     */

    @Transactional
    public void save()
    {

        setInstance(getEntityService().saveEntity(getInstance()));

        logger.config("save entity");
    }

    @Transactional
    public void saveOrUpdate(){
        if (isIdDefined()){
            save();
        }else if(getEntityService().getEntity(getInstanceId()) == null){
            save();
            id = getInstanceId();
        }else{
            primaryKeyConflict();
           // messages.addError().primaryKeyConflict();
        }

    }

    protected void primaryKeyConflict(){

    }

    protected PK getInstanceId(){
        return (PK) EntityHelper.getEntityId(getInstance());
    }


    /**
     * Remove managed entity instance from the Persistence Context and the
     * underlying database.
     * If the remove is successful, a log message is printed, a
     * {@link javax.faces.application.FacesMessage} is added and a transaction
     * success event raised.
     *
     *
     * @return "removed" if the remove is successful
     */
    @Transactional
    public void remove()
    {
        getEntityService().deleteEntity(getInstance());

    }


    @Transactional
    public E find()
    {

            E result = loadInstance();
            if (result==null)
            {
                throw new EntityNotFoundException( getId() );
            }
            return result;

    }

//    @Transactional
//    public boolean isManaged()
//    {
//        return getInstance()!=null &&
//                getEntityManager().contains( getInstance() );
//    }



    /**
     * Called by {@link #find()}.
     * <br />
     * Can be overridden to support eager fetching of associations.
     *
     */
    protected E loadInstance()
    {
        return getEntityService().getEntity(getId());
    }



    protected abstract EntityService<E,PK> getEntityService();

    //protected abstract EntityManager getEntityManager();



}
