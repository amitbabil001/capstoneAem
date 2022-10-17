package com.pwc.capstone.core.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.pwc.capstone.core.models.BannerArticleModel;

public interface TrendingArticlesServices {
    
    public List<BannerArticleModel> getArticles();

    
    
}
