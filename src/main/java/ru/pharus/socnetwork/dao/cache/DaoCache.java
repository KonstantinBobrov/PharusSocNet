package ru.pharus.socnetwork.dao.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.dao.DaoFactory;
import ru.pharus.socnetwork.entity.Model;
import ru.pharus.socnetwork.entity.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DaoCache {
    private static final Logger log = LoggerFactory.getLogger(DaoCache.class);
    private DaoFactory factory = DaoFactory.getInstance();
    private static volatile DaoCache daoINSTANCE;
    private final int USER_CACHE_SIZE = 10;
    private LinkedHashMap<Integer, User> userCache = new LinkedHashMap<Integer, User>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, User> eldest) {
            if (this.size() > USER_CACHE_SIZE) {
                try {
                    //System.out.println("Size: deleting old entry");
                } catch (Exception e) {
                    log.error("Error in USER_CACHE ", e);
                }
                return true;
            }
            return false;
        }
    };

    private Map<Integer, Model> modelCache =  new ConcurrentHashMap<>();


    public static DaoCache getInstance() {
        DaoCache instance = daoINSTANCE;
        if (instance == null) {
            synchronized (DaoCache.class) {
                instance = daoINSTANCE;
                if (instance == null) {
                    log.info("Creating DaoCache instance");
                    daoINSTANCE = instance = new DaoCache();
                }
            }
        }
        return instance;
    }

    public void putUser(User user) {
        synchronized (this) {
            if (user != null) {
                userCache.put(user.getId(), user);
            }
        }
    }

    public User getUser(int id) {
        return userCache.get(id);
    }

    public void removeUser(int id) {
        userCache.remove(id);
    }

    public void putModel(Model model) {
        modelCache.put(model.getId(),model);
    }

    public Model getModel(int id){
        return modelCache.get(id);
    }
}
