package com.pwc.capstone.core.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.pwc.capstone.core.constants.ConstantClass;
import com.pwc.capstone.core.models.BannerArticleModel;
import com.pwc.capstone.core.services.CapstoneUtilsService;
import com.pwc.capstone.core.services.TrendingArticlesServices;
import com.pwc.capstone.core.utils.CapstoneClassUtils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.wcm.api.Page;

/**
 * @author Amit_Babil
 *
 */
@Component(immediate = true, service = TrendingArticlesServices.class)
public class TrendingArticlesServicesImpl implements TrendingArticlesServices{
    
    @Reference
    QueryBuilder queryBuilder;
    
    @Reference
    CapstoneUtilsService utilsService;
    
    private static final Logger LOG = LoggerFactory.getLogger(TrendingArticlesServicesImpl.class);    
    
    /**
     *this method helps to get article which are created on current date
     *
     */
    @Override
    public List<BannerArticleModel> getArticles() {
        try(ResourceResolver resolver = utilsService.getResourceResolver()) {
            Map<String, String> queryProps = new HashMap<>();
            queryProps.put("type", "cq:Page");
            queryProps.put("path", "/content/capstone/us/en/articles");
            queryProps.put("orderby", "@jcr:content/jcr:created");
            queryProps.put("orderby.sort","desc");
            Query query = queryBuilder.createQuery(PredicateGroup.create(queryProps), resolver.adaptTo(Session.class));
            List<Hit> result = query.getResult().getHits();
            List<BannerArticleModel> trendingArticles = new ArrayList<>();
            
            for (Hit hit : result) {
                try {
                    Resource resource = hit.getResource();
                    String articleNodePath = resource.getPath() + ConstantClass.BANNER_ARTICLE_NODE_PATH;
                    Resource bannerResource = resolver.getResource(articleNodePath);
                    Page articlePage = resource.adaptTo(Page.class);                
                    String createdDateStr = CapstoneClassUtils.getDate(articlePage.getProperties().get("jcr:created", Date.class));
                    BannerArticleModel bannerArticleModel = bannerResource.adaptTo(BannerArticleModel.class);
                    bannerArticleModel.setPageCreatedDate(createdDateStr);
                    trendingArticles.add(bannerArticleModel);
                } catch (RepositoryException e) {
                    LOG.error("while pages are read then exception occurs {}", e.getMessage());
                }
            }   
            return trendingArticles;
        }
    }
}
