package cc.coopersoft.framework.services;

import cc.coopersoft.framework.data.TaskAction;
import cc.coopersoft.framework.data.TaskSubscribe;
import cc.coopersoft.framework.data.TaskSubscribeGroup;
import cc.coopersoft.framework.data.model.SubscribeEntity;
import cc.coopersoft.framework.data.model.SubscribeGroupEntity;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Startup
@Singleton
@ApplicationScoped
public class BusinessSubscribeConfigService  implements java.io.Serializable{

    @Inject
    private Logger logger;


    private Map<String,TaskSubscribe> editors;

    private Map<String,TaskSubscribe> views;

    private Map<String,TaskAction> actions;

    private Map<String,TaskAction> validations;

    @PostConstruct
    public void load(){

        editors = new HashMap<>();
        views = new HashMap<>();
        actions = new HashMap<>();
        validations = new HashMap<>();

        Reflections reflections = new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage("cc.coopersoft")).addScanners(new ResourcesScanner()));
        Set<String> confings = reflections.getResources(Pattern.compile(".*\\.tasksubscribe\\.xml"));

        logger.config("find tasksubscribe config count:" + confings.size());

        for (String conf : confings) {
            logger.config("load tasksubscribe config:" + conf);
            try {
                DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = domBuilder.parse(getClass().getClassLoader().getResourceAsStream(conf));
                Element root = doc.getDocumentElement();
                NodeList typeNodes = root.getChildNodes();
                for (int i = 0, size = typeNodes.getLength(); i < size; i++){
                    Node typeNode = typeNodes.item(i);
                    if(typeNode.getNodeType() == Node.ELEMENT_NODE){
                        String typeName = typeNode.getNodeName().trim().toUpperCase();
                        NodeList regNodes = typeNode.getChildNodes();
                        for(int j=0, jsize = regNodes.getLength(); j < jsize; j++){
                            Node regNode = regNodes.item(j);
                            if((regNode.getNodeType() == Node.ELEMENT_NODE) &&
                                    ("subscribe".equals(regNode.getNodeName().trim().toLowerCase()))){
                                if ("VIEW".equals(typeName) ||
                                        "EDITOR".equals(typeName)){

                                    TaskSubscribe subscribe = new TaskSubscribe();
                                    subscribe.setId(regNode.getAttributes().getNamedItem("id").getNodeValue());

                                    subscribe.setPage(regNode.getAttributes().getNamedItem("page").getNodeValue());
                                    subscribe.setName(regNode.getAttributes().getNamedItem("name").getNodeValue());

                                    for(int k = 0, subSize = regNode.getChildNodes().getLength(); k < subSize; k++){
                                        Node subNode = regNode.getChildNodes().item(k);
                                        if (subNode.getNodeType() == Node.ELEMENT_NODE){
                                            if ("param".equals(subNode.getNodeName())){
                                                subscribe.getParams().put(subNode.getAttributes().getNamedItem("name").getNodeValue(),
                                                        subNode.getAttributes().getNamedItem("value").getNodeValue());
                                            }
                                            if ("dependency".equals(subNode.getNodeName())){
                                                subscribe.getDependencies().add(subNode.getFirstChild().getNodeValue());
                                            }

                                        }
                                    }



                                    if ("VIEW".equals(typeName)){
                                        views.put(subscribe.getId(),subscribe);
                                        logger.config("load view subscribe:" + subscribe);
                                    }else if ("EDITOR".equals(typeName)){
                                        subscribe.setClassName(regNode.getAttributes().getNamedItem("class").getNodeValue());
                                        editors.put(subscribe.getId(),subscribe);
                                        logger.config("load editor subscribe:" + subscribe);
                                    }


                                }else if ("ACTION".equals(typeName) || "VALID".equals(typeName)){
                                    TaskAction subscribe = new TaskAction();
                                    subscribe.setId(regNode.getAttributes().getNamedItem("id").getNodeValue());
                                    subscribe.setName(regNode.getAttributes().getNamedItem("name").getNodeValue());
                                    subscribe.setClassName(regNode.getAttributes().getNamedItem("class").getNodeValue());
                                    if ("ACTION".equals(typeName)) {
                                        actions.put(subscribe.getId(), subscribe);
                                    }else{
                                        validations.put(subscribe.getId(), subscribe);
                                    }

                                    logger.config("load action subscribe:" + subscribe);
                                }

                            }
                        }

                    }
                }

            } catch (FileNotFoundException e) {
                logger.log(Level.CONFIG,e.getMessage(),e);
                throw new IllegalArgumentException(e);
            } catch (ParserConfigurationException e) {
                logger.log(Level.CONFIG,e.getMessage(),e);
                throw new IllegalArgumentException(e);
            } catch (SAXException e) {
                logger.log(Level.CONFIG,e.getMessage(),e);
                throw new IllegalArgumentException(e);
            } catch (IOException e) {
                logger.log(Level.CONFIG,e.getMessage(),e);
                throw new IllegalArgumentException(e);
            }

        }


    }

    public TaskSubscribeGroup factoryGroup(SubscribeGroupEntity subscribeGroupEntity){
        TaskSubscribeGroup result = new TaskSubscribeGroup();
        result.setName(subscribeGroupEntity.getName());
        result.setIcon(subscribeGroupEntity.getIconCss());
        result.setIndex(subscribeGroupEntity.getPriority());
        for(SubscribeEntity subscribeEntity: subscribeGroupEntity.getSubscribeList()){
            if (SubscribeGroupEntity.Type.EDITOR.equals(subscribeGroupEntity.getType())){
                result.getSubscribes().add(getEditor(subscribeEntity.getRegName()));
            }else{
                result.getSubscribes().add(getView(subscribeEntity.getRegName()));
            }

        }
        return result;
    }

    private TaskSubscribe getEditor(String id){
        return editors.get(id);
    }

    private TaskSubscribe getView(String id){
        return views.get(id);
    }

    public TaskAction getAction(String id){
        return actions.get(id);
    }

    public TaskAction getValid(String id) {return validations.get(id);}


}
