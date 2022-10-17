package com.pwc.capstone.core.models;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.pwc.capstone.core.constants.ConstantClass;

@Model (adaptables =  {Resource.class,SlingHttpServletRequest.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BannerHomeModel {
    
    @ValueMapValue
    private String[] articleLinks;
    
    @ScriptVariable
    PageManager pageManager;
    
    @SlingObject
    ResourceResolver resolver;
    
    List<BannerArticleModel> bannerArticles;
    
    @PostConstruct
    public void init() {
        
        if(articleLinks !=null && articleLinks.length > 0) {
            
            bannerArticles= new ArrayList<>();
            
            
            for(String path : articleLinks) {
                
                Page  articlePage=pageManager.getPage(path);
                Date createdDate=articlePage.getProperties().get("jcr:created", Date.class);
                SimpleDateFormat formatter=new SimpleDateFormat("EEE,dd MMMM yyyy");
                String createdDateStr=formatter.format(createdDate);
                String articleNodePath = path + ConstantClass.BANNER_ARTICLE_NODE_PATH;
                Resource resource=resolver.getResource(articleNodePath);
                
                if(resource!=null) {
                    
                    
                    BannerArticleModel bannerArticleModel=resource.adaptTo(BannerArticleModel.class);
                    
                    bannerArticleModel.setPageCreatedDate(createdDateStr);
                    
                    bannerArticles.add(bannerArticleModel);
                    
                    if(bannerArticles.size() >= 5) {
                        break;
                    }
                }           
            }
        }
    }

    public String[] getArticleLinks() {
        return articleLinks;
    }


    public List<BannerArticleModel> getBannerArticles() {
        return bannerArticles;
    }
      
}