package com.pwc.capstone.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwc.capstone.core.services.CapstoneUtilsService;

@Component(immediate = true, service = CapstoneUtilsService.class)
public class CapstoneUtilsServiceImpl implements CapstoneUtilsService{

    @Reference
    ResourceResolverFactory factory;
    
    private static final String SUB_SERVICE_NAME = "capstoneservice";
    private static final Logger LOG = LoggerFactory.getLogger(TrendingArticlesServicesImpl.class);    

    
    /**
     * creating resourceResolver object from system user , for providing permissions we use admin 
     */
    @Override
    public ResourceResolver getResourceResolver() {     
        ResourceResolver resolver = null;
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put(ResourceResolverFactory.SUBSERVICE, SUB_SERVICE_NAME);   
            resolver = factory.getServiceResourceResolver(params);          
        } catch (LoginException e) {
            LOG.error("Error while creating resource resolver object from system user - {}",e.getMessage());
        }
        return resolver;        
    }


    

    

}
