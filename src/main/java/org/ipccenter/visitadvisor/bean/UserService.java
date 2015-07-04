package org.ipccenter.visitadvisor.bean;

import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author stad
 */
@ManagedBean
@ApplicationScoped
public class UserService {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;
}
