package com.pwc.capstone.core.models;
import com.pwc.capstone.core.services.TrendingArticlesServices;
import java.util.List;

import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;



/**
 * @author Amit_Babil
 *
 */
@Model(adaptables = Resource.class)
public class TrendingArticlesModel {
    
    @OSGiService
    TrendingArticlesServices trendingArticlesService;
    
    List<BannerArticleModel> trendingArticles;
    
    /**
     *this init() Method helps to get the article pages
     * by calling the method getArticles() of TrendingArticlesServiceImpl
     */
    @PostConstruct
    public void init() {
        trendingArticles = trendingArticlesService.getArticles();
    }
    
    public List<BannerArticleModel> getTrendingArticles() {
        return trendingArticles;
    }
}

   
